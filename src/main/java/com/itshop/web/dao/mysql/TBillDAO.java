package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TBill;
import com.itshop.web.dto.request.CompanyBillQueryParam;
import com.itshop.web.dto.response.AgentMonthlyBillDetailResp;
import com.itshop.web.dto.response.CompanyStatementBillResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TBillDAO {
    int deleteByPrimaryKey(Integer billId);

    int insert(TBill record);

    int insertSelective(TBill record);

    TBill selectByPrimaryKey(Integer billId);

    int updateByPrimaryKeySelective(TBill record);

    int updateByPrimaryKey(TBill record);

    /**
     * 根据订单id及变更id查询账单信息
     * @param record
     * @return
     */
    TBill selectByOrderIdAndChangeId(TBill record);

    /**
     * 删除账单
     * @param record
     * @return
     */
    int deleteByAfterBillYearMonth(TBill record);

    /**
     * 批量新增账单
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<TBill> list);

    /**
     * 根据组织id及账单年月查询账单信息
     *
     * @param orgFullPath
     * @param billYearMonth
     * @return
     */
    List<TBill> selectByOrgIdAndLteBillYearMonth(@Param("orgFullPath") String orgFullPath,@Param("billYearMonth") String billYearMonth);

    /**
     * 根据组织id及账单年月更新账单信息
     * @param record
     * @return
     */
    int updateByOrgIdAndLteBillYearMonth(TBill record);

    /**
     * 更新账单状态为 未出账
     *
     * @param statementMonth 账期年月
     * @return
     */
    int updateBillStatus2UnOutAccount(@Param("statementMonth") String statementMonth);

    /**
     * 根据结算单id得到账单信息
     *
     * @param companyBillQueryParam
     * @return
     */
    List<CompanyStatementBillResp> selectCompanyStatementBill(CompanyBillQueryParam companyBillQueryParam );


    List<AgentMonthlyBillDetailResp> selectAgentMonthlyBill(@Param("statementId") Integer statementId);
}