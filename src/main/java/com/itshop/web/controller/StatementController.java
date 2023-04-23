package com.itshop.web.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.pagehelper.PageInfo;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.dto.PageParam;
import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import com.itshop.web.dto.request.CompanyBillQueryParam;
import com.itshop.web.dto.request.CompanyStatementQueryParam;
import com.itshop.web.dto.request.CustomerStatementBillQuery;
import com.itshop.web.dto.request.StatementPaymentSaveParam;
import com.itshop.web.dto.response.*;
import com.itshop.web.manager.StatementBillManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 账单管理-控制器
 *
 * @description 账单管理-控制器
 * @author liufenglong
 * @date 2022/11/25
 */
@Slf4j
@RestController
@RequestMapping("/statement")
@Api(value = "StatementController", description = "账单管理-控制器")
public class StatementController extends BaseController {

    @Autowired
    StatementBillManager statementBillManager;

    /**
     * 月账单概览-列表
     *
     * @param pageParam
     * @return RetResult
     */
    @PostMapping("/company/list")
    @ApiOperation(value = "月账单概览-列表")
    public RetResult<PageInfo<CompanyStatementResp>> selectCompanyStatement(@RequestBody PageParam<CompanyStatementQueryParam> pageParam) {
        UserInfoVO userInfoVO = getUserInfoVO();
        if (pageParam.getSearchParam() == null) {
            CompanyStatementQueryParam orderQueryParam = new CompanyStatementQueryParam();
            orderQueryParam.setOrgId(userInfoVO.getOrgId());
            pageParam.setSearchParam(orderQueryParam);
        } else {
            pageParam.getSearchParam().setOrgId(userInfoVO.getOrgId());
        }
        PageInfo<CompanyStatementResp> result = statementBillManager.selectCompanyStatement(pageParam);
        return RetWrapper.success(result);
    }

    /**
     * 月账单概览-列表下载
     *
     * @param queryParam
     * @param response
     * @throws IOException
     */
    @PostMapping("/company/list/download")
    @ApiOperation(value = "月账单概览-列表下载")
    public void companyStatementDownload(@RequestBody CompanyStatementQueryParam queryParam, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("月账单", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        Set<String> excludeColumnFiledNames = new HashSet<String>();
        excludeColumnFiledNames.add("statementId");
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), CompanyStatementResp.class)
                .excludeColumnFiledNames(excludeColumnFiledNames).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("月账单").build();
        int index = 0, pageSize = 10000;
        boolean isLastPage = true;

        UserInfoVO userInfoVO = getUserInfoVO();
        queryParam.setOrgId(userInfoVO.getOrgId());
        do {
            PageParam<CompanyStatementQueryParam> pageParam = new PageParam<>();
            pageParam.setOffset(index * pageSize);
            pageParam.setPageSize(pageSize);
            pageParam.setSearchParam(queryParam);
            PageInfo<CompanyStatementResp> result = statementBillManager.selectCompanyStatement(pageParam);
            if (result != null) {
                if (CollectionUtils.isNotEmpty(result.getList())) {
                    excelWriter.write(result.getList(), writeSheet);
                }
                isLastPage = result.isIsLastPage();
            }
            index++;
        }
        while (!isLastPage);
        excelWriter.finish();
    }

    /**
     * 账单管理-账单详情
     * @param pageParam 分页参数
     * @return RetResult<PageInfo<CompanyStatementBillResp>>
     */
    @PostMapping("/company/bill/list")
    @ApiOperation(value = "公司账目-账单详情")
    public RetResult<PageInfo<CompanyStatementBillResp>> selectCompanyBillList(
            @RequestBody PageParam<CompanyBillQueryParam> pageParam) {
        UserInfoVO userInfoVO = getUserInfoVO();
        if (pageParam.getSearchParam() == null) {
            CompanyBillQueryParam orderQueryParam = new CompanyBillQueryParam();
            orderQueryParam.setOrgId(userInfoVO.getOrgId());
            pageParam.setSearchParam(orderQueryParam);
        } else {
            pageParam.getSearchParam().setOrgId(userInfoVO.getOrgId());
        }
        PageInfo<CompanyStatementBillResp> pageInfo = statementBillManager.selectCompanyBillList(pageParam);
        return RetWrapper.success(pageInfo);
    }

    /**
     * 公司账目-账单详情-下载
     *
     * @param queryParam 公司账单查询参数
     * @param response
     * @throws IOException
     */
    @PostMapping("/company/bill/list/download")
    @ApiOperation(value = "公司账目-账单详情-下载")
    public void CompanyBillListDownload(@RequestBody CompanyBillQueryParam queryParam,
                                        HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("账单详情", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        Set<String> excludeColumnFiledNames = new HashSet<String>();
        excludeColumnFiledNames.add("productCode");
        excludeColumnFiledNames.add("serviceStartTime");
        excludeColumnFiledNames.add("serviceEndTime");
        excludeColumnFiledNames.add("providerPayAmount");
        excludeColumnFiledNames.add("agentLevel1PayAmount");
        excludeColumnFiledNames.add("agentLevel2PayAmount");
        excludeColumnFiledNames.add("agentLevel3PayAmount");
        excludeColumnFiledNames.add("endUserPayAmount");
        excludeColumnFiledNames.add("orgId");
        excludeColumnFiledNames.add("orgType");
        excludeColumnFiledNames.add("orderId");
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), CompanyStatementBillResp.class)
                .excludeColumnFiledNames(excludeColumnFiledNames).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("账单详情").build();
        int index = 0, pageSize = 10000;
        boolean isLastPage = true;

        UserInfoVO userInfoVO = getUserInfoVO();
        queryParam.setOrgId(userInfoVO.getOrgId());
        do {
            PageParam<CompanyBillQueryParam> pageParam = new PageParam<>();
            pageParam.setOffset(index * pageSize);
            pageParam.setPageSize(pageSize);
            pageParam.setSearchParam(queryParam);
            PageInfo<CompanyStatementBillResp> result = statementBillManager.selectCompanyBillList(pageParam);
            if (result != null) {
                if (CollectionUtils.isNotEmpty(result.getList())) {
                    excelWriter.write(result.getList(), writeSheet);
                }
                isLastPage = result.isIsLastPage();
            }
            index++;
        }
        while (!isLastPage);
        excelWriter.finish();
    }

    /**
     * 账单管理-账单详情
     * @param statementId
     * @return
     */
    @GetMapping("/company/bill")
    @ApiOperation(value = "账单管理-账单详情")
    public RetResult<List<CompanyStatementBillResp>>  selectCompanyStatementBill(@RequestParam("statementId") Integer statementId ){
        List<CompanyStatementBillResp> result =   statementBillManager.selectCompanyStatementBill(statementId);
        return RetWrapper.success(result);
    }

    /**
     * 账单管理-账单详情
     *
     * @param statementId
     * @param response
     * @throws IOException
     */
    @GetMapping("/company/bill/download")
    @ApiOperation(value = "账单管理-账单详情下载")
    public void companyStatementBillDownload(@RequestParam("statementId") Integer statementId , HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("月账单详情", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        Set<String> excludeColumnFiledNames = new HashSet<String>();
        excludeColumnFiledNames.add("productCode");
        excludeColumnFiledNames.add("serviceStartTime");
        excludeColumnFiledNames.add("serviceEndTime");
        excludeColumnFiledNames.add("providerPayAmount");
        excludeColumnFiledNames.add("agentLevel1PayAmount");
        excludeColumnFiledNames.add("agentLevel2PayAmount");
        excludeColumnFiledNames.add("agentLevel3PayAmount");
        excludeColumnFiledNames.add("endUserPayAmount");
        excludeColumnFiledNames.add("orgId");
        excludeColumnFiledNames.add("orgType");
        excludeColumnFiledNames.add("orderId");
        EasyExcel.write(response.getOutputStream(), CompanyStatementBillResp.class)
                .excludeColumnFiledNames(excludeColumnFiledNames)
                .sheet("月账单详情")
                .doWrite(statementBillManager.selectCompanyStatementBill(statementId));
    }

    /**
     *
     * @param statementMonth
     * @return
     */
    @GetMapping("/statementMonth/save")
    @ApiOperation(value = "保存结算单")
    public RetResult SaveStatement(@RequestParam("statementMonth") String statementMonth){
        statementBillManager.SaveInternetAccessOrderStatement(statementMonth);
        return RetWrapper.success();
    }

    /**
     * 代理月账单概览
     *
     * @param payeeOrgId 收款方组织id
     * @param statementMonth 账单年月
     * @return
     */
    @GetMapping("/agent/month-bill/overview")
    @ApiOperation(value = "代理月账单概览")
    public RetResult<AgentMonthlyBillOverviewPageResp> overviewOfAgentMonthlyBill(@RequestParam(value = "payeeOrgId",required = false) Integer payeeOrgId,
                                                                                  @RequestParam(value = "statementMonth",required = false) String statementMonth) {
        UserInfoVO userInfoVO = getUserInfoVO();
        if (payeeOrgId == null) {
            payeeOrgId = userInfoVO.getOrgId();
        }
        return RetWrapper.success(statementBillManager.selectAgentMonthlyBill(userInfoVO, payeeOrgId, statementMonth));
    }

    /**
     * 客户账目-月账单概览(20230114)
     *
     * @param pageParam
     * @return RetResult<CustomerStatementBillOverviewResp>
     */
    @PostMapping("/agent/month-bill/overview")
    @ApiOperation(value = "客户账目-月账单概览")
    public RetResult<CustomerStatementBillOverviewResp> selectCustomerStatementBill(
            @RequestBody PageParam<CustomerStatementBillQuery> pageParam) {
        UserInfoVO userInfoVO = getUserInfoVO();
        if (pageParam.getSearchParam() == null) {
            CustomerStatementBillQuery orderQueryParam = new CustomerStatementBillQuery();
            orderQueryParam.setPayeeOrgId(userInfoVO.getOrgId());
            pageParam.setSearchParam(orderQueryParam);
        } else {
            if (pageParam.getSearchParam().getPayeeOrgId() == null) {
                pageParam.getSearchParam().setPayeeOrgId(userInfoVO.getOrgId());
            }
        }
        return RetWrapper.success(statementBillManager.selectCustomerStatementBill(userInfoVO, pageParam));
    }


    /**
     * 客户账目-月账单概览-下载(20230114)
     *
     * @param billQuery 客户账目查询
     * @param response
     * @throws IOException
     */
    @PostMapping("/agent/month-bill/overview/download")
    @ApiOperation(value = "客户账目-月账单概览-下载")
    public void customerStatementBillDownload(@RequestBody CustomerStatementBillQuery billQuery,
                                              HttpServletResponse response) throws IOException {
        UserInfoVO userInfoVO = getUserInfoVO();
        if (billQuery.getPayeeOrgId() == null) {
            billQuery.setPayeeOrgId(userInfoVO.getOrgId());
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("月账单概览", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        Set<String> excludeColumnFiledNames = new HashSet<String>();
        excludeColumnFiledNames.add("statementId");
        excludeColumnFiledNames.add("orgType");
        excludeColumnFiledNames.add("payeeOrgId");
        excludeColumnFiledNames.add("payerOrgId");
        excludeColumnFiledNames.add("billCount");
        excludeColumnFiledNames.add("canDrillDown");
        excludeColumnFiledNames.add("canShowBillDetail");
        excludeColumnFiledNames.add("canConfirmPayment");
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), AgentMonthlyBillOverviewResp.class)
                .excludeColumnFiledNames(excludeColumnFiledNames).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("月账单概览").build();
        int index = 0, pageSize = 10000;
        boolean isLastPage = true;

        do {
            PageParam<CustomerStatementBillQuery> pageParam = new PageParam<>();
            pageParam.setOffset(index * pageSize);
            pageParam.setPageSize(pageSize);
            pageParam.setSearchParam(billQuery);
            CustomerStatementBillOverviewResp result = statementBillManager.selectCustomerStatementBill(userInfoVO, pageParam);
            if (result != null && result.getAgentMonthlyBills() !=null) {
                if (CollectionUtils.isNotEmpty(result.getAgentMonthlyBills().getList())) {
                    excelWriter.write(result.getAgentMonthlyBills().getList(), writeSheet);
                }
                isLastPage = result.getAgentMonthlyBills().isIsLastPage();
            }
            index++;
        }
        while (!isLastPage);
        excelWriter.finish();

    }

    /**
     * 代理月账单概览-保存结算交易
     *
     * @param saveParam 结算单-交易银行流水信息
     * @return
     */
    @PostMapping("/agent/month-bill/savePayment")
    @ApiOperation(value = "代理月账单概览-保存结算交易")
    public RetResult<Integer> SaveStatementPaymentInfo(@RequestBody StatementPaymentSaveParam saveParam){
        return RetWrapper.success(statementBillManager.SaveStatementPaymentInfo(saveParam, getUserInfoVO()));
    }

    /**
     * 代理月账单概览-重置结算交易信息
     *
     * @param statementId 结算单ID
     * @return
     */
    @GetMapping("/agent/month-bill/reset-payment")
    @ApiOperation(value = "代理月账单概览-重置结算交易信息")
    public RetResult<Integer> ResetStatementPaymentInfo(@RequestParam(value = "statementId") Integer statementId){
        return RetWrapper.success(statementBillManager.ResetStatementPaymentInfo(statementId, getUserInfoVO()));
    }

    /**
     * 代理月账单概览-客户公司账单详情
     *
     * @param statementId 结算单ID
     * @return
     */
    @GetMapping("/agent/month-bill/detail")
    @ApiOperation(value = "代理月账单概览-客户公司账单详情")
    public RetResult<AgentMonthlyBillDetailPageResp> selectAgentMonthlyBill(@RequestParam(value = "statementId") Integer statementId) {
        return RetWrapper.success(statementBillManager.selectAgentMonthlyBill(getUserInfoVO(),statementId));
    }

    /**
     * 代理月账单概览-客户公司账单详情下载
     * @param statementId 结算单ID
     * @param response
     * @throws IOException
     */
    @GetMapping("/agent/month-bill/detail/download")
    @ApiOperation(value = "代理月账单概览-客户公司账单详情下载")
    public void  agentMonthlyBillDownload(@RequestParam(value = "statementId") Integer statementId,
                                          HttpServletResponse response) throws IOException {

        AgentMonthlyBillDetailPageResp result = statementBillManager.selectAgentMonthlyBill(getUserInfoVO(),statementId);
        if(result ==null || CollectionUtils.isEmpty(result.getAgentMonthlyBillDetailList())) {
            return;
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("月账单详情", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        Set<String> excludeColumnFiledNames = new HashSet<String>();
        excludeColumnFiledNames.add("orderId");
        excludeColumnFiledNames.add("changeId");
        excludeColumnFiledNames.add("serviceStartTime");
        excludeColumnFiledNames.add("serviceEndTime");
        excludeColumnFiledNames.add("providerPayAmount");
        excludeColumnFiledNames.add("agentLevel1PayAmount");
        excludeColumnFiledNames.add("agentLevel2PayAmount");
        excludeColumnFiledNames.add("agentLevel3PayAmount");
        excludeColumnFiledNames.add("endUserPayAmount");
        excludeColumnFiledNames.add("orgId");
        excludeColumnFiledNames.add("orgType");
        excludeColumnFiledNames.add("productCode");
        EasyExcel.write(response.getOutputStream(), AgentMonthlyBillDetailResp.class)
                .excludeColumnFiledNames(excludeColumnFiledNames)
                .sheet("客户账单详情")
                .doWrite(result.getAgentMonthlyBillDetailList());
    }
}
