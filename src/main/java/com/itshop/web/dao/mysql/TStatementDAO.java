package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TStatement;
import com.itshop.web.dto.request.CompanyStatementQueryParam;
import com.itshop.web.dto.request.CustomerStatementBillQuery;
import com.itshop.web.dto.response.AgentMonthlyBillOverviewResp;
import com.itshop.web.dto.response.CompanyStatementResp;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TStatementDAO {
    int deleteByPrimaryKey(Integer statementId);

    int insert(TStatement record);

    int insertSelective(TStatement record);

    TStatement selectByPrimaryKey(Integer statementId);

    int updateByPrimaryKeySelective(TStatement record);

    int updateByPrimaryKey(TStatement record);

    /**
     * 根据 收款组织ID 及 付款方组织ID 得到计算单信息
     *
     * @param payeeOrgId     收款组织ID
     * @param payerOrgId     付款方组织ID
     * @param statementMonth 账期年月
     * @return
     */
    TStatement selectByPayeeAndPayerOrgId(@Param("payeeOrgId") Integer payeeOrgId,
                                          @Param("payerOrgId") Integer payerOrgId,
                                          @Param("statementMonth") String statementMonth);


    /**
     * 根据收款方ID 得到 结算金额
     *
     * @param payeeOrgId     收款组织ID
     * @param statementMonth 账期年月
     * @return
     */
    BigDecimal selectSumPayAmount(@Param("payeeOrgId") Integer payeeOrgId, @Param("statementMonth") String statementMonth);


    /**
     * 根据收款方ID及账期年月 更新 父结算单ID
     *
     * @param record
     * @return
     */
    int updateParentStatementId(TStatement record);

    /**
     * 得到公司月账单信息
     *
     * @param queryParam
     * @return
     */
    List<CompanyStatementResp> selectCompanyStatement(CompanyStatementQueryParam queryParam);


    int deleteByStatementMonth(@Param("statementMonth") String statementMonth);


    /**
     * 得到已结算的结算单数量
     *
     * @param statementMonth 结算单ID
     * @return
     */
    Long selectPayedCount(@Param("statementMonth") String statementMonth);

    /**
     * 得到代理月账单列表
     *
     * @param payeeOrgId     收款组织ID
     * @param statementMonth 账期年月
     * @return
     */
    List<AgentMonthlyBillOverviewResp> selectAgentMonthlyBill(@Param("payeeOrgId") Integer payeeOrgId,
                                                              @Param("statementMonth") String statementMonth);

    /**
     * 客户账户-月账单概览
     *
     * @param billQuery
     * @return List<AgentMonthlyBillOverviewResp>
     */
    List<AgentMonthlyBillOverviewResp> selectCustomerStatementBill(CustomerStatementBillQuery billQuery);

    /**
     * 给上级结算金额
     *
     * @param payerOrgId 付款方组织ID
     * @param statementMonth 账期年月
     * @return
     */
    BigDecimal selectPayAmountForParentOrg(@Param("payerOrgId") Integer payerOrgId,
                                           @Param("statementMonth") String statementMonth);

    int ResetStatementPaymentInfo(@Param("statementId") Integer statementId);
}