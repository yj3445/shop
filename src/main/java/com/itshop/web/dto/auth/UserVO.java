package com.itshop.web.dto.auth;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

/**
 * 用户权限信息
 *
 * @author liufenglong
 * @date 2022/7/5
 */
@Data
public class UserVO implements Serializable {
    private String appId;//应用ID
    private String userDomain;//用户域编码
    private String userId;//用户唯一标识
    private String userName;//用户名称
    private JSONObject config;//附加配置信息

    private Set<UserGroupVO> userGroups = new HashSet<>();
    private Set<RoleVO> roles = new HashSet<>();
    private Set<PermissionTargetVO> permissionTargets = new HashSet<>();
    private Set<PositionGroupVO> positionGroups = new HashSet<>();
    private Set<NameOrCodeVO> positions = new HashSet<>(0);

    //    @ApiSkip(type = "")
    @JSONField(serialize = false)
    private Set<PermissionVO> permissions = new HashSet<>();
    private Set<AuthorizationDataRangeVO> authorizationDataRanges = new HashSet<>();

    public void permissionToPermissionTarget() {
        Map<Integer,PermissionTargetVO> targets = new HashMap<>();
        permissions.forEach(f ->{
            PermissionTargetVO permissionTargetVO = f.getPermissionTargetVO();
            PermissionTargetVO targetVO = targets.computeIfAbsent(permissionTargetVO.getPermissionTargetId(), (key) -> {
                return permissionTargetVO;
            });
            f.setPermissionTargetVO(null);

            List<PermissionVO> permissions = targetVO.getPermissions();
            if(permissions == null){
                permissions = new ArrayList<>();
                targetVO.setPermissions(permissions);
            }
            permissions.add(f);
        });
        this.permissionTargets = new HashSet<>(targets.values());
    }
}