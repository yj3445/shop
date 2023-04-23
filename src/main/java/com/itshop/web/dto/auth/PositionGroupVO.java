package com.itshop.web.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 *   岗位组
 */
@Data
public class PositionGroupVO implements Serializable {
    /**
     *   岗位组ID
     */
    private Integer positionGroupId;

    /**
     *   应用系统ID
     */
    private String appId;

    /**
     *   用户组编码
     */
    private String code;

    /**
     *   用户组名
     */
    private String name;

    /**
     *   备注
     */
    private String comment;

    /**
     *   删除标识
     */
    private Integer isDelete;
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionGroupVO positionGroupVO = (PositionGroupVO) o;
        return positionGroupId.equals(positionGroupVO.positionGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionGroupId);
    }
}
