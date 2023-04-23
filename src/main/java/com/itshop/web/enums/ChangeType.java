package com.itshop.web.enums;

/**
 * 变更类型
 */
public enum ChangeType {
    ADD("ADD","新增"),
    MODIFIED("MODIFIED","变更"),
    DELETED("DELETED","删除");
    String value;
    String desc;
    ChangeType(String value,String desc) {
        this.value = value;
        this.desc =desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
