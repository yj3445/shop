package com.itshop.web.dao.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TAgentOemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TAgentOemExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andAgentOemIdIsNull() {
            addCriterion("agent_oem_id is null");
            return (Criteria) this;
        }

        public Criteria andAgentOemIdIsNotNull() {
            addCriterion("agent_oem_id is not null");
            return (Criteria) this;
        }

        public Criteria andAgentOemIdEqualTo(Integer value) {
            addCriterion("agent_oem_id =", value, "agentOemId");
            return (Criteria) this;
        }

        public Criteria andAgentOemIdNotEqualTo(Integer value) {
            addCriterion("agent_oem_id <>", value, "agentOemId");
            return (Criteria) this;
        }

        public Criteria andAgentOemIdGreaterThan(Integer value) {
            addCriterion("agent_oem_id >", value, "agentOemId");
            return (Criteria) this;
        }

        public Criteria andAgentOemIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("agent_oem_id >=", value, "agentOemId");
            return (Criteria) this;
        }

        public Criteria andAgentOemIdLessThan(Integer value) {
            addCriterion("agent_oem_id <", value, "agentOemId");
            return (Criteria) this;
        }

        public Criteria andAgentOemIdLessThanOrEqualTo(Integer value) {
            addCriterion("agent_oem_id <=", value, "agentOemId");
            return (Criteria) this;
        }

        public Criteria andAgentOemIdIn(List<Integer> values) {
            addCriterion("agent_oem_id in", values, "agentOemId");
            return (Criteria) this;
        }

        public Criteria andAgentOemIdNotIn(List<Integer> values) {
            addCriterion("agent_oem_id not in", values, "agentOemId");
            return (Criteria) this;
        }

        public Criteria andAgentOemIdBetween(Integer value1, Integer value2) {
            addCriterion("agent_oem_id between", value1, value2, "agentOemId");
            return (Criteria) this;
        }

        public Criteria andAgentOemIdNotBetween(Integer value1, Integer value2) {
            addCriterion("agent_oem_id not between", value1, value2, "agentOemId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNull() {
            addCriterion("org_id is null");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNotNull() {
            addCriterion("org_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrgIdEqualTo(Integer value) {
            addCriterion("org_id =", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotEqualTo(Integer value) {
            addCriterion("org_id <>", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThan(Integer value) {
            addCriterion("org_id >", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("org_id >=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThan(Integer value) {
            addCriterion("org_id <", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThanOrEqualTo(Integer value) {
            addCriterion("org_id <=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIn(List<Integer> values) {
            addCriterion("org_id in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotIn(List<Integer> values) {
            addCriterion("org_id not in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdBetween(Integer value1, Integer value2) {
            addCriterion("org_id between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotBetween(Integer value1, Integer value2) {
            addCriterion("org_id not between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andSiteNameIsNull() {
            addCriterion("site_name is null");
            return (Criteria) this;
        }

        public Criteria andSiteNameIsNotNull() {
            addCriterion("site_name is not null");
            return (Criteria) this;
        }

        public Criteria andSiteNameEqualTo(String value) {
            addCriterion("site_name =", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotEqualTo(String value) {
            addCriterion("site_name <>", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameGreaterThan(String value) {
            addCriterion("site_name >", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameGreaterThanOrEqualTo(String value) {
            addCriterion("site_name >=", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLessThan(String value) {
            addCriterion("site_name <", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLessThanOrEqualTo(String value) {
            addCriterion("site_name <=", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLike(String value) {
            addCriterion("site_name like", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotLike(String value) {
            addCriterion("site_name not like", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameIn(List<String> values) {
            addCriterion("site_name in", values, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotIn(List<String> values) {
            addCriterion("site_name not in", values, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameBetween(String value1, String value2) {
            addCriterion("site_name between", value1, value2, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotBetween(String value1, String value2) {
            addCriterion("site_name not between", value1, value2, "siteName");
            return (Criteria) this;
        }

        public Criteria andCustomDomainNameIsNull() {
            addCriterion("custom_domain_name is null");
            return (Criteria) this;
        }

        public Criteria andCustomDomainNameIsNotNull() {
            addCriterion("custom_domain_name is not null");
            return (Criteria) this;
        }

        public Criteria andCustomDomainNameEqualTo(String value) {
            addCriterion("custom_domain_name =", value, "customDomainName");
            return (Criteria) this;
        }

        public Criteria andCustomDomainNameNotEqualTo(String value) {
            addCriterion("custom_domain_name <>", value, "customDomainName");
            return (Criteria) this;
        }

        public Criteria andCustomDomainNameGreaterThan(String value) {
            addCriterion("custom_domain_name >", value, "customDomainName");
            return (Criteria) this;
        }

        public Criteria andCustomDomainNameGreaterThanOrEqualTo(String value) {
            addCriterion("custom_domain_name >=", value, "customDomainName");
            return (Criteria) this;
        }

        public Criteria andCustomDomainNameLessThan(String value) {
            addCriterion("custom_domain_name <", value, "customDomainName");
            return (Criteria) this;
        }

        public Criteria andCustomDomainNameLessThanOrEqualTo(String value) {
            addCriterion("custom_domain_name <=", value, "customDomainName");
            return (Criteria) this;
        }

        public Criteria andCustomDomainNameLike(String value) {
            addCriterion("custom_domain_name like", value, "customDomainName");
            return (Criteria) this;
        }

        public Criteria andCustomDomainNameNotLike(String value) {
            addCriterion("custom_domain_name not like", value, "customDomainName");
            return (Criteria) this;
        }

        public Criteria andCustomDomainNameIn(List<String> values) {
            addCriterion("custom_domain_name in", values, "customDomainName");
            return (Criteria) this;
        }

        public Criteria andCustomDomainNameNotIn(List<String> values) {
            addCriterion("custom_domain_name not in", values, "customDomainName");
            return (Criteria) this;
        }

        public Criteria andCustomDomainNameBetween(String value1, String value2) {
            addCriterion("custom_domain_name between", value1, value2, "customDomainName");
            return (Criteria) this;
        }

        public Criteria andCustomDomainNameNotBetween(String value1, String value2) {
            addCriterion("custom_domain_name not between", value1, value2, "customDomainName");
            return (Criteria) this;
        }

        public Criteria andCustomDomainEnableIsNull() {
            addCriterion("custom_domain_enable is null");
            return (Criteria) this;
        }

        public Criteria andCustomDomainEnableIsNotNull() {
            addCriterion("custom_domain_enable is not null");
            return (Criteria) this;
        }

        public Criteria andCustomDomainEnableEqualTo(Boolean value) {
            addCriterion("custom_domain_enable =", value, "customDomainEnable");
            return (Criteria) this;
        }

        public Criteria andCustomDomainEnableNotEqualTo(Boolean value) {
            addCriterion("custom_domain_enable <>", value, "customDomainEnable");
            return (Criteria) this;
        }

        public Criteria andCustomDomainEnableGreaterThan(Boolean value) {
            addCriterion("custom_domain_enable >", value, "customDomainEnable");
            return (Criteria) this;
        }

        public Criteria andCustomDomainEnableGreaterThanOrEqualTo(Boolean value) {
            addCriterion("custom_domain_enable >=", value, "customDomainEnable");
            return (Criteria) this;
        }

        public Criteria andCustomDomainEnableLessThan(Boolean value) {
            addCriterion("custom_domain_enable <", value, "customDomainEnable");
            return (Criteria) this;
        }

        public Criteria andCustomDomainEnableLessThanOrEqualTo(Boolean value) {
            addCriterion("custom_domain_enable <=", value, "customDomainEnable");
            return (Criteria) this;
        }

        public Criteria andCustomDomainEnableIn(List<Boolean> values) {
            addCriterion("custom_domain_enable in", values, "customDomainEnable");
            return (Criteria) this;
        }

        public Criteria andCustomDomainEnableNotIn(List<Boolean> values) {
            addCriterion("custom_domain_enable not in", values, "customDomainEnable");
            return (Criteria) this;
        }

        public Criteria andCustomDomainEnableBetween(Boolean value1, Boolean value2) {
            addCriterion("custom_domain_enable between", value1, value2, "customDomainEnable");
            return (Criteria) this;
        }

        public Criteria andCustomDomainEnableNotBetween(Boolean value1, Boolean value2) {
            addCriterion("custom_domain_enable not between", value1, value2, "customDomainEnable");
            return (Criteria) this;
        }

        public Criteria andHomePageBackgroundImageIsNull() {
            addCriterion("home_page_background_image is null");
            return (Criteria) this;
        }

        public Criteria andHomePageBackgroundImageIsNotNull() {
            addCriterion("home_page_background_image is not null");
            return (Criteria) this;
        }

        public Criteria andHomePageBackgroundImageEqualTo(String value) {
            addCriterion("home_page_background_image =", value, "homePageBackgroundImage");
            return (Criteria) this;
        }

        public Criteria andHomePageBackgroundImageNotEqualTo(String value) {
            addCriterion("home_page_background_image <>", value, "homePageBackgroundImage");
            return (Criteria) this;
        }

        public Criteria andHomePageBackgroundImageGreaterThan(String value) {
            addCriterion("home_page_background_image >", value, "homePageBackgroundImage");
            return (Criteria) this;
        }

        public Criteria andHomePageBackgroundImageGreaterThanOrEqualTo(String value) {
            addCriterion("home_page_background_image >=", value, "homePageBackgroundImage");
            return (Criteria) this;
        }

        public Criteria andHomePageBackgroundImageLessThan(String value) {
            addCriterion("home_page_background_image <", value, "homePageBackgroundImage");
            return (Criteria) this;
        }

        public Criteria andHomePageBackgroundImageLessThanOrEqualTo(String value) {
            addCriterion("home_page_background_image <=", value, "homePageBackgroundImage");
            return (Criteria) this;
        }

        public Criteria andHomePageBackgroundImageLike(String value) {
            addCriterion("home_page_background_image like", value, "homePageBackgroundImage");
            return (Criteria) this;
        }

        public Criteria andHomePageBackgroundImageNotLike(String value) {
            addCriterion("home_page_background_image not like", value, "homePageBackgroundImage");
            return (Criteria) this;
        }

        public Criteria andHomePageBackgroundImageIn(List<String> values) {
            addCriterion("home_page_background_image in", values, "homePageBackgroundImage");
            return (Criteria) this;
        }

        public Criteria andHomePageBackgroundImageNotIn(List<String> values) {
            addCriterion("home_page_background_image not in", values, "homePageBackgroundImage");
            return (Criteria) this;
        }

        public Criteria andHomePageBackgroundImageBetween(String value1, String value2) {
            addCriterion("home_page_background_image between", value1, value2, "homePageBackgroundImage");
            return (Criteria) this;
        }

        public Criteria andHomePageBackgroundImageNotBetween(String value1, String value2) {
            addCriterion("home_page_background_image not between", value1, value2, "homePageBackgroundImage");
            return (Criteria) this;
        }

        public Criteria andCreaterByIsNull() {
            addCriterion("creater_by is null");
            return (Criteria) this;
        }

        public Criteria andCreaterByIsNotNull() {
            addCriterion("creater_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreaterByEqualTo(Integer value) {
            addCriterion("creater_by =", value, "createrBy");
            return (Criteria) this;
        }

        public Criteria andCreaterByNotEqualTo(Integer value) {
            addCriterion("creater_by <>", value, "createrBy");
            return (Criteria) this;
        }

        public Criteria andCreaterByGreaterThan(Integer value) {
            addCriterion("creater_by >", value, "createrBy");
            return (Criteria) this;
        }

        public Criteria andCreaterByGreaterThanOrEqualTo(Integer value) {
            addCriterion("creater_by >=", value, "createrBy");
            return (Criteria) this;
        }

        public Criteria andCreaterByLessThan(Integer value) {
            addCriterion("creater_by <", value, "createrBy");
            return (Criteria) this;
        }

        public Criteria andCreaterByLessThanOrEqualTo(Integer value) {
            addCriterion("creater_by <=", value, "createrBy");
            return (Criteria) this;
        }

        public Criteria andCreaterByIn(List<Integer> values) {
            addCriterion("creater_by in", values, "createrBy");
            return (Criteria) this;
        }

        public Criteria andCreaterByNotIn(List<Integer> values) {
            addCriterion("creater_by not in", values, "createrBy");
            return (Criteria) this;
        }

        public Criteria andCreaterByBetween(Integer value1, Integer value2) {
            addCriterion("creater_by between", value1, value2, "createrBy");
            return (Criteria) this;
        }

        public Criteria andCreaterByNotBetween(Integer value1, Integer value2) {
            addCriterion("creater_by not between", value1, value2, "createrBy");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifiedByIsNull() {
            addCriterion("modified_by is null");
            return (Criteria) this;
        }

        public Criteria andModifiedByIsNotNull() {
            addCriterion("modified_by is not null");
            return (Criteria) this;
        }

        public Criteria andModifiedByEqualTo(Integer value) {
            addCriterion("modified_by =", value, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByNotEqualTo(Integer value) {
            addCriterion("modified_by <>", value, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByGreaterThan(Integer value) {
            addCriterion("modified_by >", value, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByGreaterThanOrEqualTo(Integer value) {
            addCriterion("modified_by >=", value, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByLessThan(Integer value) {
            addCriterion("modified_by <", value, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByLessThanOrEqualTo(Integer value) {
            addCriterion("modified_by <=", value, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByIn(List<Integer> values) {
            addCriterion("modified_by in", values, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByNotIn(List<Integer> values) {
            addCriterion("modified_by not in", values, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByBetween(Integer value1, Integer value2) {
            addCriterion("modified_by between", value1, value2, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedByNotBetween(Integer value1, Integer value2) {
            addCriterion("modified_by not between", value1, value2, "modifiedBy");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeIsNull() {
            addCriterion("modified_time is null");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeIsNotNull() {
            addCriterion("modified_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeEqualTo(Date value) {
            addCriterion("modified_time =", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeNotEqualTo(Date value) {
            addCriterion("modified_time <>", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeGreaterThan(Date value) {
            addCriterion("modified_time >", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modified_time >=", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeLessThan(Date value) {
            addCriterion("modified_time <", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeLessThanOrEqualTo(Date value) {
            addCriterion("modified_time <=", value, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeIn(List<Date> values) {
            addCriterion("modified_time in", values, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeNotIn(List<Date> values) {
            addCriterion("modified_time not in", values, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeBetween(Date value1, Date value2) {
            addCriterion("modified_time between", value1, value2, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andModifiedTimeNotBetween(Date value1, Date value2) {
            addCriterion("modified_time not between", value1, value2, "modifiedTime");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Boolean value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Boolean value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Boolean value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Boolean value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Boolean> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Boolean> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}