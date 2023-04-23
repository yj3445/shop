package com.itshop.web.enums;

import org.apache.logging.log4j.util.Strings;

import java.util.Objects;

/**
 * 组织类型
 */
public enum OrgTypeEnum {
    SERVICE_PROVIDER(10, "服务提供商","S"),
    AGENT_COMPANY(20, "代理商","A"),
    CUSTOMER_COMPANY(30, "客户公司","C");
    private Integer code;
    private String name;
    private String prefix;

    OrgTypeEnum(Integer code, String name,String prefix) {
        this.code = code;
        this.name = name;
        this.prefix = prefix;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public static String getName(Integer code) {
        for (OrgTypeEnum orgTypeEnum : OrgTypeEnum.values()) {
            if (Objects.equals(orgTypeEnum.getCode(), code)) {
                return orgTypeEnum.getName();
            }
        }
        return Strings.EMPTY;
    }
}
