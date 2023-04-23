package com.itshop.web.manager;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.constants.SnowConstants;
import com.itshop.web.dao.mysql.TBillDAO;
import com.itshop.web.dao.mysql.TInternetAccessOrderBeforeChangeDAO;
import com.itshop.web.dao.mysql.TOrderAmountDAO;
import com.itshop.web.dao.mysql.TStatementDAO;
import com.itshop.web.dao.po.*;
import com.itshop.web.dto.PageParam;
import com.itshop.web.dto.request.CompanyBillQueryParam;
import com.itshop.web.dto.request.CompanyStatementQueryParam;
import com.itshop.web.dto.request.CustomerStatementBillQuery;
import com.itshop.web.dto.request.StatementPaymentSaveParam;
import com.itshop.web.dto.response.*;
import com.itshop.web.enums.*;
import com.itshop.web.exception.BusinessException;
import com.itshop.web.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 结算单、账单
 */
@Service
@Slf4j
public class StatementBillManager {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    TBillDAO tBillDAO;

    @Autowired
    TStatementDAO tStatementDAO;

    @Autowired
    TInternetAccessOrderBeforeChangeDAO tInternetAccessOrderBeforeChangeDAO;

    @Autowired
    TOrderAmountDAO tOrderAmountDAO;

    @Autowired
    AuthorizationManager authorizationManager;

    /**
     * 插入互联网接入-订单-对应的账单
     *
     * @param lastTimeAuditPassInfo 上一次审批通过时，订单信息
     * @param nowAuditPassInfo      现在审批通过时，订单信息
     * @return
     */
    @Transactional(value = "mysqlTransactionManager", rollbackFor = Exception.class)
    public void SaveInternetAccessOrderBill(TInternetAccessOrderBeforeChange lastTimeAuditPassInfo,
                                            TInternetAccessOrderBeforeChange nowAuditPassInfo) {
        //当前月(季/年)第一天
        LocalDate curMQY = LocalDate.now();
        if (Objects.equals(PaymentCycleEnum.QUARTER.getCode(), nowAuditPassInfo.getPaymentCycle())) {
            curMQY = DateUtil.getStartOrEndDayOfQuarter(LocalDate.now(), true);
        } else if (Objects.equals(PaymentCycleEnum.YEAR.getCode(), nowAuditPassInfo.getPaymentCycle())) {
            curMQY = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
        } else {
            curMQY = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        }
        String currentYearMonthStr = curMQY.format(SnowConstants.YYYYMM);
        //
        LocalDate startDate = DateUtil.date2LocalDate(nowAuditPassInfo.getStartTime());
        LocalDate endDate = DateUtil.date2LocalDate(nowAuditPassInfo.getEndTime());
        //共多少天
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        //共多少个月(季/年)
        long totalMQYs = 0l;
        if (Objects.equals(PaymentCycleEnum.QUARTER.getCode(), nowAuditPassInfo.getPaymentCycle())) {
            totalMQYs = DateUtil.durationQuarters(startDate, endDate);
        } else if (Objects.equals(PaymentCycleEnum.YEAR.getCode(), nowAuditPassInfo.getPaymentCycle())) {
            totalMQYs = DateUtil.durationYears(startDate, endDate);
        } else {
            //合同期间(月)
            totalMQYs = nowAuditPassInfo.getContractPeriod();
        }
        log.info("[SaveInternetAccessOrderBill] orderTableName:{}," +
                        "orderId:{}," +
                        "changeId:{}," +
                        "currentYearMonth:{}," +
                        "paymentCycle:{}," +
                        "paymentMethod:{}," +
                        "startDate:{}," +
                        "endDate:{}," +
                        "totalDays[(endDate-startDate)+1]:{}," +
                        "totalMQYs:{}",
                OrderType.INTERNET_ACCESS_ORDER.getValue(),
                nowAuditPassInfo.getOrderId(),
                nowAuditPassInfo.getChangeId(),
                currentYearMonthStr,
                PaymentCycleEnum.getValueByCode(nowAuditPassInfo.getPaymentCycle()),
                PaymentMethodEnum.getValueByCode(nowAuditPassInfo.getPaymentMethod()),
                startDate,
                endDate,
                totalDays,
                totalMQYs
        );
        TOrderAmount orderAmountQuery = new TOrderAmount();
        orderAmountQuery.setOrderId(nowAuditPassInfo.getOrderId());
        orderAmountQuery.setChangeId(nowAuditPassInfo.getChangeId());
        orderAmountQuery.setOrderTableName(OrderType.INTERNET_ACCESS_ORDER.getValue());
        TOrderAmount orderAmount = tOrderAmountDAO.selectByOrderIdAndChangeId(orderAmountQuery);
        if (orderAmount == null) {
            return;
        }
        if (lastTimeAuditPassInfo != null) {
            TBill billQuery = new TBill();
            billQuery.setOrderId(lastTimeAuditPassInfo.getOrderId());
            billQuery.setChangeId(lastTimeAuditPassInfo.getChangeId());
            billQuery.setOrderTableName(OrderType.INTERNET_ACCESS_ORDER.getValue());
            billQuery.setBillYearMonth(currentYearMonthStr);
            TBill bill = tBillDAO.selectByOrderIdAndChangeId(billQuery);
            if (bill != null && Objects.equals(BillStatusEnum.UN_OUT_ACCOUNT.getValue(), bill.getBillStatus())) {
                //当前期需要重新计算
                LocalDate startDate1 = DateUtil.date2LocalDate(bill.getServiceStartTime());
                LocalDate endDate1 = DateUtil.date2LocalDate(bill.getServiceEndTime());
                long totalDays1 = ChronoUnit.DAYS.between(startDate1, endDate1) + 1;
                long days1 = ChronoUnit.DAYS.between(startDate1, LocalDate.now()) + 1;

                //总天数 = 总天数 - 已过去的天数
                long lostDays= ChronoUnit.DAYS.between(startDate, LocalDate.now()) + 1;
                totalDays = totalDays - lostDays;
                log.info("[SaveInternetAccessOrderBill] [currentYearMonth-Need-Recalculate]" +
                                "orderTableName:{}," +
                                "orderId:{}," +
                                "changeId:{}," +
                                "currentYearMonth:{}," +
                                "paymentCycle:{}," +
                                "paymentMethod:{}," +
                                "startDate1[bill.serviceStartTime]:{}," +
                                "endDate1[bill.serviceEndTime]:{}," +
                                "totalDays1[=(endDate1-startDate1)+1] :{}," +
                                "days1[=(now-startDate1)+1]:{}," +
                                "lostDays[(now-startDate)+1]:{},"+
                                "totalDays[totalDays-lostDays]:{}",
                        OrderType.INTERNET_ACCESS_ORDER.getValue(),
                        nowAuditPassInfo.getOrderId(),
                        nowAuditPassInfo.getChangeId(),
                        currentYearMonthStr,
                        PaymentCycleEnum.getValueByCode(nowAuditPassInfo.getPaymentCycle()),
                        PaymentMethodEnum.getValueByCode(nowAuditPassInfo.getPaymentMethod()),
                        startDate1,
                        endDate1,
                        totalDays1,
                        days1,
                        lostDays,
                        totalDays
                );

                bill.setBillStatus(BillStatusEnum.OUT_OF_ACCOUNT.getValue());
                bill.setModifiedTime(new Date());
                bill.setProviderPayAmount(bill.getProviderPayAmount()
                        .multiply(BigDecimal.valueOf(days1))
                        .divide(BigDecimal.valueOf(totalDays1), 2, RoundingMode.HALF_UP));
                bill.setAgentLevel1PayAmount(bill.getAgentLevel1PayAmount()
                        .multiply(BigDecimal.valueOf(days1))
                        .divide(BigDecimal.valueOf(totalDays1), 2, RoundingMode.HALF_UP));
                bill.setAgentLevel2PayAmount(bill.getAgentLevel2PayAmount()
                        .multiply(BigDecimal.valueOf(days1))
                        .divide(BigDecimal.valueOf(totalDays1), 2, RoundingMode.HALF_UP));
                bill.setAgentLevel3PayAmount(bill.getAgentLevel3PayAmount()
                        .multiply(BigDecimal.valueOf(days1))
                        .divide(BigDecimal.valueOf(totalDays1), 2, RoundingMode.HALF_UP));
                bill.setEndUserPayAmount(bill.getEndUserPayAmount()
                        .multiply(BigDecimal.valueOf(days1))
                        .divide(BigDecimal.valueOf(totalDays1), 2, RoundingMode.HALF_UP));
                tBillDAO.updateByPrimaryKeySelective(bill);
            }
            //当前期往后的需要删除
            tBillDAO.deleteByAfterBillYearMonth(billQuery);
        }
        /*服务商金额*/
        BigDecimal providerPayAmount = BigDecimal.ZERO;
        /*一级代理金额*/
        BigDecimal agentLevel1PayAmount = BigDecimal.ZERO;
        /*二级代理金额*/
        BigDecimal agentLevel2PayAmount = BigDecimal.ZERO;
        /*三级代理金额*/
        BigDecimal agentLevel3PayAmount = BigDecimal.ZERO;
        /*终端用户金额*/
        BigDecimal endUserPayAmount = BigDecimal.ZERO;
        int index = 0;
        List<TBill> billList = Lists.newArrayList();
        while (index <= totalMQYs) {
            //对账年月
            LocalDate billYearMonth = startDate;
            if (Objects.equals(PaymentCycleEnum.QUARTER.getCode(), nowAuditPassInfo.getPaymentCycle())) {
                billYearMonth = DateUtil.getStartOrEndDayOfQuarter(startDate, true);
            } else if (Objects.equals(PaymentCycleEnum.YEAR.getCode(), nowAuditPassInfo.getPaymentCycle())) {
                billYearMonth = startDate.with(TemporalAdjusters.firstDayOfYear());
            } else {
                billYearMonth = startDate.with(TemporalAdjusters.firstDayOfMonth());
            }
            String billYearMonthStr = billYearMonth.format(SnowConstants.YYYYMM);

            //服务开始时间
            LocalDate serviceStartTime = startDate;
            if (index != 0) {
                if (Objects.equals(PaymentCycleEnum.QUARTER.getCode(), nowAuditPassInfo.getPaymentCycle())) {
                    serviceStartTime = DateUtil.getStartOrEndDayOfQuarter(startDate, true);
                } else if (Objects.equals(PaymentCycleEnum.YEAR.getCode(), nowAuditPassInfo.getPaymentCycle())) {
                    serviceStartTime = startDate.with(TemporalAdjusters.firstDayOfYear());
                } else {
                    serviceStartTime = startDate.with(TemporalAdjusters.firstDayOfMonth());
                }
            }
            if (lastTimeAuditPassInfo != null && billYearMonthStr.equalsIgnoreCase(currentYearMonthStr)) {
                serviceStartTime = LocalDate.now();
            }
            //服务结束时间
            LocalDate serviceEndTime = endDate;
            if (index != totalMQYs) {
                if (Objects.equals(PaymentCycleEnum.QUARTER.getCode(), nowAuditPassInfo.getPaymentCycle())) {
                    serviceEndTime = DateUtil.getStartOrEndDayOfQuarter(startDate, false);
                } else if (Objects.equals(PaymentCycleEnum.YEAR.getCode(), nowAuditPassInfo.getPaymentCycle())) {
                    serviceEndTime = startDate.with(TemporalAdjusters.lastDayOfYear());
                } else {
                    serviceEndTime = startDate.with(TemporalAdjusters.lastDayOfMonth());
                }
            }

            if (serviceStartTime.isAfter(serviceEndTime)) {
                break;
            }
            TBill bill = new TBill();
            bill.setBillCode(String.format("%s_%s", nowAuditPassInfo.getBusinessId(), billYearMonthStr));
            bill.setBillYearMonth(billYearMonthStr);
            bill.setOrgId(nowAuditPassInfo.getOrgId());
            bill.setUserId(nowAuditPassInfo.getCreaterBy());
            bill.setOrderId(nowAuditPassInfo.getOrderId());
            bill.setChangeId(nowAuditPassInfo.getChangeId());
            bill.setOrderTableName(OrderType.INTERNET_ACCESS_ORDER.getValue());
            bill.setProductId(nowAuditPassInfo.getProductId());
            bill.setBillStatus(BillStatusEnum.UN_OUT_ACCOUNT.getValue());
            bill.setServiceStartTime(DateUtil.localDate2Date(serviceStartTime));
            bill.setServiceEndTime(DateUtil.localDate2Date(serviceEndTime));
            long days = ChronoUnit.DAYS.between(serviceStartTime, serviceEndTime) + 1;
            if (!serviceEndTime.isEqual(endDate)) {
                bill.setProviderPayAmount(orderAmount.getProviderTotalPrice()
                        .multiply(BigDecimal.valueOf(days))
                        .divide(BigDecimal.valueOf(totalDays), 2, RoundingMode.HALF_UP));
                bill.setAgentLevel1PayAmount(orderAmount.getAgentLevel1TotalPrice()
                        .multiply(BigDecimal.valueOf(days))
                        .divide(BigDecimal.valueOf(totalDays), 2, RoundingMode.HALF_UP));
                bill.setAgentLevel2PayAmount(orderAmount.getAgentLevel2TotalPrice()
                        .multiply(BigDecimal.valueOf(days))
                        .divide(BigDecimal.valueOf(totalDays), 2, RoundingMode.HALF_UP));
                bill.setAgentLevel3PayAmount(orderAmount.getAgentLevel3TotalPrice()
                        .multiply(BigDecimal.valueOf(days))
                        .divide(BigDecimal.valueOf(totalDays), 2, RoundingMode.HALF_UP));
                bill.setEndUserPayAmount(orderAmount.getSalesTotalPrice()
                        .multiply(BigDecimal.valueOf(days))
                        .divide(BigDecimal.valueOf(totalDays), 2, RoundingMode.HALF_UP));

                providerPayAmount = providerPayAmount.add(bill.getProviderPayAmount());
                /*一级代理金额*/
                agentLevel1PayAmount = agentLevel1PayAmount.add(bill.getAgentLevel1PayAmount());
                /*二级代理金额*/
                agentLevel2PayAmount = agentLevel2PayAmount.add(bill.getAgentLevel2PayAmount());
                /*三级代理金额*/
                agentLevel3PayAmount = agentLevel3PayAmount.add(bill.getAgentLevel3PayAmount());
                /*终端用户金额*/
                endUserPayAmount = endUserPayAmount.add(bill.getEndUserPayAmount());
            } else {
                //最后一期
                bill.setProviderPayAmount(orderAmount.getProviderTotalPrice()
                        .subtract(providerPayAmount));
                bill.setAgentLevel1PayAmount(orderAmount.getAgentLevel1TotalPrice()
                        .subtract(agentLevel1PayAmount));
                bill.setAgentLevel2PayAmount(orderAmount.getAgentLevel2TotalPrice()
                        .subtract(agentLevel2PayAmount));
                bill.setAgentLevel3PayAmount(orderAmount.getAgentLevel3TotalPrice()
                        .subtract(agentLevel3PayAmount));
                bill.setEndUserPayAmount(orderAmount.getSalesTotalPrice()
                        .subtract(endUserPayAmount));
            }
            bill.setCreateTime(new Date());
            bill.setModifiedTime(new Date());
            bill.setIsDeleted(false);
            bill.setOrgFullPath(orderAmount.getOrgFullPath());
            bill.setOutAccountDay(DateUtil.localDate2Date(billYearMonth));
            if (Objects.equals(PaymentCycleEnum.QUARTER.getCode(), nowAuditPassInfo.getPaymentCycle())) {
                startDate = startDate.plusMonths(3);
            } else if (Objects.equals(PaymentCycleEnum.YEAR.getCode(), nowAuditPassInfo.getPaymentCycle())) {
                startDate = startDate.plusYears(1);
            } else {
                startDate = startDate.plusMonths(1);
            }
            //后付
            if (Objects.equals(PaymentMethodEnum.AFTER_PAYING.getCode(), nowAuditPassInfo.getPaymentMethod())) {
                bill.setOutAccountDay(DateUtil.localDate2Date(startDate.with(TemporalAdjusters.firstDayOfMonth())));
            }
            log.info("[SaveInternetAccessOrderBill] orderTableName:{}," +
                            "orderId:{}," +
                            "changeId:{}," +
                            "currentYearMonth:{}," +
                            "paymentCycle:{}," +
                            "paymentMethod:{}," +
                            "startDate:{}," +
                            "endDate:{}," +
                            "totalDays[(endDate-startDate)+1]:{}," +
                            "totalMQYs:{},"+
                            "serviceStartTime:{},"+
                            "serviceEndTime:{},"+
                            "days[(serviceEndTime-serviceStartTime)+1]:{}",
                    OrderType.INTERNET_ACCESS_ORDER.getValue(),
                    nowAuditPassInfo.getOrderId(),
                    nowAuditPassInfo.getChangeId(),
                    currentYearMonthStr,
                    PaymentCycleEnum.getValueByCode(nowAuditPassInfo.getPaymentCycle()),
                    PaymentMethodEnum.getValueByCode(nowAuditPassInfo.getPaymentMethod()),
                    startDate,
                    endDate,
                    totalDays,
                    totalMQYs,
                    serviceStartTime,
                    serviceEndTime,
                    days
            );
            billList.add(bill);
            index++;
        }
        //会生成n或者n+1行数据 （n个月(季/年)）
        // 2022-01-01 至 2022-12-31 会生成12条
        // 2022-01-10 至 2023-01-09 会生成13条
        tBillDAO.insertBatch(billList);
    }


    /**
     * 保存月结算单
     */
    @Transactional(value = "mysqlTransactionManager", rollbackFor = Exception.class)
    public void SaveInternetAccessOrderStatement(String statementMonth) {
        Long payedCount = tStatementDAO.selectPayedCount(statementMonth);
        if (payedCount > 0) {
            throw new BusinessException(String.format("%s已存在“已结算”完毕的结算单，不支持再次结算！", statementMonth));
        }
        tBillDAO.updateBillStatus2UnOutAccount(statementMonth);
        tStatementDAO.deleteByStatementMonth(statementMonth);
        List<TOrganizational> organizations = authorizationManager.selectAllOrganizational();
        organizations.forEach(organizational -> {
            List<TBill> bills = tBillDAO.selectByOrgIdAndLteBillYearMonth(organizational.getOrgFullPath(), statementMonth);
            if (CollectionUtils.isNotEmpty(bills)) {
                List<TBill> currentOrgBills = bills.stream()
                        .filter(p -> Objects.equals(p.getOrgId(), organizational.getOrgId()))
                        .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(currentOrgBills)) {
                    boolean payeeEqualPayer = true;
                    if (Objects.equals(OrgTypeEnum.CUSTOMER_COMPANY.getCode(), organizational.getOrgType())) {
                        payeeEqualPayer = false;
                    }
                    TStatement statement = saveTStatement(statementMonth, organizational, currentOrgBills, payeeEqualPayer);
                    TBill bill = new TBill();
                    bill.setStatementId(statement.getStatementId());
                    bill.setBillStatus(BillStatusEnum.OUT_OF_ACCOUNT.getValue());
                    bill.setModifiedTime(new Date());
                    bill.setOrgId(organizational.getOrgId());
                    bill.setBillYearMonth(statementMonth);
                    tBillDAO.updateByOrgIdAndLteBillYearMonth(bill);
                }

                if(organizational.getParentOrgId() != -1){
                    List<TBill> descendantOrgBills = bills.stream().filter(p -> !Objects.equals(p.getOrgId(), organizational.getOrgId())).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(descendantOrgBills)) {
                        saveTStatement(statementMonth, organizational, descendantOrgBills, false);
                    }
                }
            }
        });
    }

    private TStatement saveTStatement(String statementMonth, TOrganizational organizational,
                                      List<TBill> bills,Boolean payeeEqualPayer) {
        TStatement statement = new TStatement();
        statement.setParentStatementId(-1);
        //收款组织ID
        statement.setPayeeOrgId(organizational.getParentOrgId());
        if(payeeEqualPayer){
            statement.setPayeeOrgId(organizational.getOrgId());
        }
        //付款方组织ID
        statement.setPayerOrgId(organizational.getOrgId());
        statement.setStatementMonth(statementMonth);
        statement.setIsLeaf(false);
        statement.setPaymentStatus(PaymentStatusEnum.NO_PAY.getCode());
        statement.setCreateTime(new Date());
        statement.setIsDeleted(false);
        statement.setPaymentConfirmUserId(-1);
        BigDecimal providerPayAmount = bills.stream().map(TBill::getProviderPayAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal agentLevel1PayAmount = bills.stream().map(TBill::getAgentLevel1PayAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal agentLevel2PayAmount = bills.stream().map(TBill::getAgentLevel2PayAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal agentLevel3PayAmount = bills.stream().map(TBill::getAgentLevel3PayAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal endUserPayAmount = bills.stream().map(TBill::getEndUserPayAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (OrgTypeEnum.SERVICE_PROVIDER.getCode().equals(organizational.getOrgType())) {
            statement.setPayAmount(providerPayAmount);
            statement.setCostAmount(providerPayAmount);
        } else if (OrgTypeEnum.CUSTOMER_COMPANY.getCode().equals(organizational.getOrgType())) {
            statement.setPayAmount(endUserPayAmount);
            if (organizational.getAgentLevel1OrgId() > 0) {
                statement.setCostAmount(agentLevel1PayAmount);
            }
            if (organizational.getAgentLevel2OrgId() > 0) {
                statement.setCostAmount(agentLevel2PayAmount);
            }
            if (organizational.getAgentLevel3OrgId() > 0) {
                statement.setCostAmount(agentLevel3PayAmount);
            }
        } else {
            if (organizational.getAgentLevel1OrgId() > 0) {
                statement.setPayAmount(agentLevel1PayAmount);
                statement.setCostAmount(providerPayAmount);
            }
            if (organizational.getAgentLevel2OrgId() > 0) {
                statement.setPayAmount(agentLevel2PayAmount);
                statement.setCostAmount(agentLevel1PayAmount);
            }
            if (organizational.getAgentLevel3OrgId() > 0) {
                statement.setPayAmount(agentLevel3PayAmount);
                statement.setCostAmount(agentLevel2PayAmount);
            }
        }
        tStatementDAO.insertSelective(statement);
        return statement;
    }

    /**
     * 账单管理
     *
     * @param pageParam
     * @return
     */
    public PageInfo<CompanyStatementResp> selectCompanyStatement(PageParam<CompanyStatementQueryParam> pageParam) {
        String orderBy;
        if (StringUtils.isEmpty(pageParam.getSortName())) {
            orderBy = "statementMonth asc ";
        } else {
            orderBy = pageParam.getSortName() + (pageParam.getReverse() ? " desc" : " asc");
        }
        PageInfo<CompanyStatementResp> pageInfo = PageHelper.offsetPage(pageParam.getOffset(), pageParam.getPageSize())
                .setOrderBy(orderBy)
                .doSelectPageInfo(
                        new ISelect() {
                            @Override
                            public void doSelect() {
                                tStatementDAO.selectCompanyStatement(pageParam.getSearchParam());
                            }
                        }
                );
        return pageInfo;
    }

    /**
     * 得到公司账单列表
     *
     * @param pageParam 分页参数
     * @return  PageInfo<CompanyStatementBillResp>
     */
    public PageInfo<CompanyStatementBillResp> selectCompanyBillList(PageParam<CompanyBillQueryParam> pageParam) {
        String orderBy;
        if (StringUtils.isEmpty(pageParam.getSortName())) {
            orderBy = "billYearMonth desc ";
        } else {
            orderBy = pageParam.getSortName() + (pageParam.getReverse() ? " desc" : " asc");
        }
        PageInfo<CompanyStatementBillResp> pageInfo = PageHelper.offsetPage(pageParam.getOffset(), pageParam.getPageSize())
                .setOrderBy(orderBy)
                .doSelectPageInfo(
                        new ISelect() {
                            @Override
                            public void doSelect() {
                                tBillDAO.selectCompanyStatementBill(pageParam.getSearchParam());
                            }
                        }
                );
        if (pageInfo != null) {
            settingPayAmount(pageInfo.getList());
        }
        return pageInfo;
    }

    /**
     * 根据结算单id得到账单信息
     *
     * @param statementId
     * @return
     */
    public List<CompanyStatementBillResp> selectCompanyStatementBill(Integer statementId) {
        CompanyBillQueryParam queryParam = new CompanyBillQueryParam();
        queryParam.setStatementId(statementId);
        List<CompanyStatementBillResp> result = tBillDAO.selectCompanyStatementBill(queryParam);
        settingPayAmount(result);
        return result;
    }

    private void settingPayAmount(List<CompanyStatementBillResp> result) {
        if (CollectionUtils.isNotEmpty(result)) {
            result.stream()
                    .sorted(Comparator.comparing(CompanyStatementBillResp::getBillYearMonth,
                            Comparator.naturalOrder()));
            result.forEach(item -> {
                LocalDate startDate = DateUtil.date2LocalDate(item.getServiceStartTime());
                LocalDate endDate = DateUtil.date2LocalDate(item.getServiceEndTime());
                item.setServiceTime(String.format("%s~%s", startDate.format(SnowConstants.DATE_WITH_HYPHEN_FORMATTER), endDate.format(SnowConstants.DATE_WITH_HYPHEN_FORMATTER)));
                if (Objects.equals(OrgTypeEnum.SERVICE_PROVIDER.getCode(), item.getOrgType())) {
                    item.setPayAmount(item.getProviderPayAmount());
                } else if (Objects.equals(OrgTypeEnum.CUSTOMER_COMPANY.getCode(), item.getOrgType())) {
                    item.setPayAmount(item.getEndUserPayAmount());
                } else {
                    if (BigDecimal.ZERO.compareTo(item.getAgentLevel1PayAmount()) < 0) {
                        item.setPayAmount(item.getAgentLevel1PayAmount());
                    }
                    if (BigDecimal.ZERO.compareTo(item.getAgentLevel2PayAmount()) < 0) {
                        item.setPayAmount(item.getAgentLevel2PayAmount());
                    }
                    if (BigDecimal.ZERO.compareTo(item.getAgentLevel3PayAmount()) < 0) {
                        item.setPayAmount(item.getAgentLevel3PayAmount());
                    }
                }
            });
        }
    }

    /**
     * 得到代理月账单列表
     *
     * @param payeeOrgId     收款组织ID
     * @param statementMonth 账期年月
     * @return
     */
    public AgentMonthlyBillOverviewPageResp selectAgentMonthlyBill(UserInfoVO userInfoVO,
                                                                   Integer payeeOrgId,
                                                                   String statementMonth) {
        AgentMonthlyBillOverviewPageResp resp = new AgentMonthlyBillOverviewPageResp();
        resp.setStatementMonth(statementMonth);
        resp.setOrgLinked(Lists.newLinkedList());
        List<TOrganizational> organizationals = authorizationManager.selectAllOrganizational();
        List<Integer> orgIds = Lists.newArrayList();
        organizationals.stream()
                .filter(p -> Objects.equals(p.getOrgId(), payeeOrgId))
                .findFirst()
                .ifPresent(p -> {
                    int index = Integer.MAX_VALUE;
                    for (int i = 0; i < p.getOrgTier().length; i++) {
                        if (Objects.equals(userInfoVO.getOrgId(), p.getOrgTier()[i])) {
                            index = i;
                            break;
                        }
                    }
                    while (index < p.getOrgTier().length) {
                        orgIds.add(p.getOrgTier()[index]);
                        index++;
                    }
                });
        orgIds.forEach(t -> {
            organizationals.stream()
                    .filter(p -> Objects.equals(p.getOrgId(), t))
                    .findFirst()
                    .ifPresent(p -> {
                        OrganizationalResp organizationalResp = new OrganizationalResp();
                        organizationalResp.setOrgId(p.getOrgId());
                        organizationalResp.setCompanyName(p.getCompanyName());
                        organizationalResp.setOrgType(p.getOrgType());
                        resp.getOrgLinked().add(organizationalResp);
                    });
        });
        if (statementMonth != null) {
            String[] arr = statementMonth.split("-");
            if(arr.length >1){
                statementMonth = arr[0] + arr[1];
            }
        }

        AgentMonthlyBillOverviewResp summaryRow = new AgentMonthlyBillOverviewResp();
        summaryRow.setStatementNo("汇总");
        List<AgentMonthlyBillOverviewResp> billResps = tStatementDAO.selectAgentMonthlyBill(payeeOrgId, statementMonth);
        if (CollectionUtils.isNotEmpty(billResps)) {
            billResps.forEach(p -> {
                p.setCanShowBillDetail(p.getBillCount() > 0);
                p.setCanDrillDown(true);
                if (Objects.equals(OrgTypeEnum.CUSTOMER_COMPANY.getCode(), p.getOrgType())) {
                    p.setCanDrillDown(false);
                }
                if (Objects.equals(p.getPayeeOrgId(), p.getPayerOrgId())) {
                    p.setCanDrillDown(false);
                }
                p.setCanConfirmPayment(Objects.equals(userInfoVO.getOrgId(), p.getPayeeOrgId()));
                organizationals.stream()
                        .filter(t -> Objects.equals(t.getOrgId(), payeeOrgId))
                        .findFirst()
                        .ifPresent(t -> {
//                            if (Objects.equals(OrgTypeEnum.SERVICE_PROVIDER.getCode(), t.getOrgType())) {
//                                p.setCostAmount(p.getProviderPayAmount());
//                            } else if (Objects.equals(OrgTypeEnum.CUSTOMER_COMPANY.getCode(), t.getOrgType())) {
//                                p.setCostAmount(p.getEndUserPayAmount());
//                            } else {
//                                if (BigDecimal.ZERO.compareTo(p.getAgentLevel1PayAmount()) < 0) {
//                                    p.setCostAmount(p.getAgentLevel1PayAmount());
//                                }
//                                if (BigDecimal.ZERO.compareTo(p.getAgentLevel2PayAmount()) < 0) {
//                                    p.setCostAmount(p.getAgentLevel2PayAmount());
//                                }
//                                if (BigDecimal.ZERO.compareTo(p.getAgentLevel3PayAmount()) < 0) {
//                                    p.setCostAmount(p.getAgentLevel3PayAmount());
//                                }
//                            }
                        });
            });

            BigDecimal payAmount = billResps.stream().map(AgentMonthlyBillOverviewResp::getPayAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            summaryRow.setPayAmount(payAmount);
            BigDecimal costAmount = billResps.stream().map(AgentMonthlyBillOverviewResp::getCostAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            summaryRow.setCostAmount(costAmount);
            billResps.add(0, summaryRow);
        }
        resp.setAgentMonthlyBills(billResps);
        BigDecimal payAmountForParentOrg = tStatementDAO.selectPayAmountForParentOrg(payeeOrgId, statementMonth);
        resp.setPayAmountForParentOrg(payAmountForParentOrg);
        return resp;
    }

    /**
     * 客户账目-月账单概览
     *
     * @param userInfoVO
     * @param pageParam
     * @return CustomerStatementBillOverviewResp
     */
    public CustomerStatementBillOverviewResp  selectCustomerStatementBill(
            UserInfoVO userInfoVO,
            PageParam<CustomerStatementBillQuery> pageParam) {

        String hashKey =String.format("",
                userInfoVO.getOrgId(),
                userInfoVO.getUserId());
        redisTemplate.opsForHash().put(hashKey,pageParam.getSearchParam().getPayeeOrgId().toString(),pageParam.getSearchParam());
        redisTemplate.expire(hashKey,2, TimeUnit.HOURS);

        CustomerStatementBillOverviewResp resp = new CustomerStatementBillOverviewResp();
        resp.setStatementMonth(pageParam.getSearchParam().getStatementMonth());
        resp.setOrgLinked(Lists.newLinkedList());
        List<TOrganizational> organizationals = authorizationManager.selectAllOrganizational();
        List<Integer> orgIds = Lists.newArrayList();

        organizationals.stream()
                .filter(p -> Objects.equals(p.getOrgId(), pageParam.getSearchParam().getPayeeOrgId()))
                .findFirst()
                .ifPresent(p -> {

                    int index = Integer.MAX_VALUE;
                    for (int i = 0; i < p.getOrgTier().length; i++) {
                        if (Objects.equals(userInfoVO.getOrgId(), p.getOrgTier()[i])) {
                            index = i;
                            break;
                        }
                    }
                    while (index < p.getOrgTier().length) {
                        orgIds.add(p.getOrgTier()[index]);
                        index++;
                    }
                });

        orgIds.forEach(t -> {
            organizationals.stream()
                    .filter(p -> Objects.equals(p.getOrgId(), t))
                    .findFirst()
                    .ifPresent(p -> {
                        OrganizationalResp organizationalResp = new OrganizationalResp();
                        organizationalResp.setOrgId(p.getOrgId());
                        organizationalResp.setCompanyName(p.getCompanyName());
                        organizationalResp.setOrgType(p.getOrgType());
                        CustomerStatementBillQuery query = (CustomerStatementBillQuery) redisTemplate.opsForHash().get(hashKey,p.getOrgId().toString());
                        organizationalResp.setQuery(query);
                        resp.getOrgLinked().add(organizationalResp);
                    });
        });

        String orderBy;
        if (StringUtils.isEmpty(pageParam.getSortName())) {
            orderBy = "statementMonth asc ";
        } else {
            orderBy = pageParam.getSortName() + (pageParam.getReverse() ? " desc" : " asc");
        }
        PageInfo<AgentMonthlyBillOverviewResp> pageInfo = PageHelper.offsetPage(pageParam.getOffset(), pageParam.getPageSize())
                .setOrderBy(orderBy)
                .doSelectPageInfo(
                        () -> tStatementDAO.selectCustomerStatementBill(pageParam.getSearchParam())
                );
        if (pageInfo != null && CollectionUtils.isNotEmpty(pageInfo.getList())) {
            pageInfo.getList().forEach(p -> {
                p.setCanShowBillDetail(p.getBillCount() > 0);
                p.setCanDrillDown(true);
                if (Objects.equals(OrgTypeEnum.CUSTOMER_COMPANY.getCode(), p.getOrgType())) {
                    p.setCanDrillDown(false);
                }
                if (Objects.equals(p.getPayeeOrgId(), p.getPayerOrgId())) {
                    p.setCanDrillDown(false);
                }
                p.setCanConfirmPayment(Objects.equals(userInfoVO.getOrgId(), p.getPayeeOrgId()));
                organizationals.stream()
                        .filter(t -> Objects.equals(t.getOrgId(), pageParam.getSearchParam().getPayeeOrgId()))
                        .findFirst()
                        .ifPresent(t -> {
//                            if (Objects.equals(OrgTypeEnum.SERVICE_PROVIDER.getCode(), t.getOrgType())) {
//                                p.setCostAmount(p.getProviderPayAmount());
//                            } else if (Objects.equals(OrgTypeEnum.CUSTOMER_COMPANY.getCode(), t.getOrgType())) {
//                                p.setCostAmount(p.getEndUserPayAmount());
//                            } else {
//                                if (BigDecimal.ZERO.compareTo(p.getAgentLevel1PayAmount()) < 0) {
//                                    p.setCostAmount(p.getAgentLevel1PayAmount());
//                                }
//                                if (BigDecimal.ZERO.compareTo(p.getAgentLevel2PayAmount()) < 0) {
//                                    p.setCostAmount(p.getAgentLevel2PayAmount());
//                                }
//                                if (BigDecimal.ZERO.compareTo(p.getAgentLevel3PayAmount()) < 0) {
//                                    p.setCostAmount(p.getAgentLevel3PayAmount());
//                                }
//                            }
                        });
            });
        }
        resp.setAgentMonthlyBills(pageInfo);
        BigDecimal payAmountForParentOrg = tStatementDAO.selectPayAmountForParentOrg(pageParam.getSearchParam().getPayeeOrgId(),
                pageParam.getSearchParam().getStatementMonth());
        resp.setPayAmountForParentOrg(payAmountForParentOrg);

        return resp;
    }

    public int SaveStatementPaymentInfo(StatementPaymentSaveParam saveParam, UserInfoVO userInfoVO) {
        TStatement oldInfo = tStatementDAO.selectByPrimaryKey(saveParam.getStatementId());
        if (oldInfo == null || PaymentStatusEnum.PAY.getCode().equalsIgnoreCase(oldInfo.getPaymentStatus())) {
            throw new BusinessException("该月账单已结清,无须重复结清!");
        }
        TStatement statement = new TStatement();
        statement.setStatementId(saveParam.getStatementId());
        statement.setBankFlow(saveParam.getBankFlow());
        statement.setPaymentTime(saveParam.getPaymentTime());
        if (statement.getPaymentTime() == null) {
            statement.setPaymentTime(new Date());
        }
        statement.setPaymentStatus(PaymentStatusEnum.PAY.getCode());
        statement.setPaymentConfirmUserId(userInfoVO.getAppUserInfoId());
        return tStatementDAO.updateByPrimaryKeySelective(statement);
    }

    public int ResetStatementPaymentInfo(Integer statementId, UserInfoVO userInfoVO) {
        log.warn("[ResetStatementPaymentInfo] statementId:{}, appUserInfoId:{},userId:{} orgId:{} orgTypeName:{},",
                statementId,
                userInfoVO.getAppUserInfoId(),
                userInfoVO.getUserId(),
                userInfoVO.getOrgId(),
                userInfoVO.getOrgTypeName());
        TStatement oldInfo = tStatementDAO.selectByPrimaryKey(statementId);
        if (oldInfo != null ) {
            log.info("[ResetStatementPaymentInfo],TStatement:{}", JSONObject.toJSONString(oldInfo));
        }
        return tStatementDAO.ResetStatementPaymentInfo(statementId);
    }


    /**
     * 代理月账单概览-客户公司账单详情
     *
     * @param statementId
     * @return
     */
    public AgentMonthlyBillDetailPageResp selectAgentMonthlyBill(UserInfoVO userInfoVO,Integer statementId) {
        AgentMonthlyBillDetailPageResp resp = new AgentMonthlyBillDetailPageResp();
        resp.setOrgLinked(Lists.newLinkedList());
        TStatement tStatement = tStatementDAO.selectByPrimaryKey(statementId);
        if (tStatement != null) {
            List<TOrganizational> organizationals = authorizationManager.selectAllOrganizational();
            List<Integer> orgIds = Lists.newArrayList();
            organizationals.stream()
                    //付款方组织ID
                    .filter(p -> Objects.equals(p.getOrgId(), tStatement.getPayerOrgId()))
                    .findFirst()
                    .ifPresent(p -> {
                        int index = Integer.MAX_VALUE;
                        for (int i = 0; i < p.getOrgTier().length; i++) {
                            if (Objects.equals(userInfoVO.getOrgId(), p.getOrgTier()[i])) {
                                index = i;
                                break;
                            }
                        }
                        while (index < p.getOrgTier().length) {
                            orgIds.add(p.getOrgTier()[index]);
                            index++;
                        }
                    });
            orgIds.forEach(t -> {
                organizationals.stream()
                        .filter(p -> Objects.equals(p.getOrgId(), t))
                        .findFirst()
                        .ifPresent(p -> {
                            OrganizationalResp organizationalResp = new OrganizationalResp();
                            organizationalResp.setOrgId(p.getOrgId());
                            organizationalResp.setCompanyName(p.getCompanyName());
                            organizationalResp.setOrgType(p.getOrgType());
                            resp.getOrgLinked().add(organizationalResp);
                        });
            });
        }
        List<AgentMonthlyBillDetailResp> result = tBillDAO.selectAgentMonthlyBill(statementId);
        if (CollectionUtils.isNotEmpty(result)) {
            result.forEach(item -> {
                LocalDate startDate = DateUtil.date2LocalDate(item.getServiceStartTime());
                LocalDate endDate = DateUtil.date2LocalDate(item.getServiceEndTime());
                item.setServiceTime(String.format("%s~%s", startDate.format(SnowConstants.DATE_WITH_HYPHEN_FORMATTER), endDate.format(SnowConstants.DATE_WITH_HYPHEN_FORMATTER)));
                if (Objects.equals(OrgTypeEnum.SERVICE_PROVIDER.getCode(), item.getOrgType())) {
                    item.setCostAmount(item.getProviderPayAmount());
                    item.setPayAmount(item.getProviderPayAmount());
                }
                if (Objects.equals(OrgTypeEnum.CUSTOMER_COMPANY.getCode(), item.getOrgType())) {
                    item.setCostAmount(item.getEndUserPayAmount());
                    item.setPayAmount(item.getEndUserPayAmount());
                }
                if (Objects.equals(OrgTypeEnum.AGENT_COMPANY.getCode(), item.getOrgType())) {
                    if (BigDecimal.ZERO.compareTo(item.getAgentLevel1PayAmount()) < 0) {
                        item.setCostAmount(item.getAgentLevel1PayAmount());
                        item.setPayAmount(item.getAgentLevel1PayAmount());
                    }
                    if (BigDecimal.ZERO.compareTo(item.getAgentLevel2PayAmount()) < 0) {
                        item.setCostAmount(item.getAgentLevel2PayAmount());
                        item.setPayAmount(item.getAgentLevel2PayAmount());
                    }
                    if (BigDecimal.ZERO.compareTo(item.getAgentLevel3PayAmount()) < 0) {
                        item.setCostAmount(item.getAgentLevel3PayAmount());
                        item.setPayAmount(item.getAgentLevel3PayAmount());
                    }
                }
            });
        }
        resp.setAgentMonthlyBillDetailList(result);
        return resp;
    }
}
