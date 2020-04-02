package cn.hy.infoReport.common.entity;

import java.util.List;
import java.util.Set;

/**
 * 用户角色VO
 */
public class UserRoleVo {

    private String userId;

    private List<SysRole> roleList;

    private Set<String> roleIdSet;

    public UserRoleVo() {
    }

    public UserRoleVo(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }

    public Set<String> getRoleIdSet() {
        return roleIdSet;
    }

    public void setRoleIdSet(Set<String> roleIdSet) {
        this.roleIdSet = roleIdSet;
    }
}
