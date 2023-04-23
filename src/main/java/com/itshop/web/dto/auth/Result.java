package com.itshop.web.dto.auth;

/**
 * @author liufenglong
 * @date 2022/7/5
 */

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 通用返回结果表示
 * @param <S>
 * @param <F>
 */
@Data
public class Result<S,F> implements Serializable {
    public final static String OK="200";
    public final static String EXCEPTION="500";
    public final static String USER_NAME_OR_PASS_WORD_ERROR ="70001";
    public final static String USER_DOES_NOT_EXIST="70000";
    public final static String PASS_WORD_ERROR="70002";
    private String code;//返回码信息,200成功,其余为失败
    private String msg;//返回码说明
    private S sobj;//请求成功对象
    private F fobj;//请求失败对象

}
