package com.itshop.web.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceControlParam <T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;

    private Integer transId;

    private Integer currentUserId;

    private T request;
}
