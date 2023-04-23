package com.itshop.web.enums;

/**
 * 设备控制-业务出口
 */
public enum DeviceControlIspId {
    NOT_SPECIFIED(0,"未指定"),
    UNICOM(1,"联调"),
    TELECOM(2,"电信"),
    MOBILE(3,"移动"),
    EDUCATION_NETWORK(4,"教育网"),
    NETWORK_OF_SCIENCE_AND_TECHNOLOGY(5,"科技网");
    /**
     * 出口ID
     */
    private Integer ispId;
    /**
     * 出口描述
     */
    private String desc;

    DeviceControlIspId(Integer ispId,String desc) {
        this.ispId = ispId;
        this.desc = desc;
    }

    public Integer getIspId() {
        return ispId;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 映射为设备控制的运营商出口
     *
     * @param export
     * @return
     */
    public static Integer getIspIdByExport(int export) {
        return export - 1;
    }
}
