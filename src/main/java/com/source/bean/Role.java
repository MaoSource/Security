package com.source.bean;

/**
 * @author http://www.sm1234.cn
 * @version 1.0
 * @description cn.sm1234.domain
 * @date 18/4/14
 */
public class Role {
    private Integer id; //int(10) NOT NULL,
    private String roleName; //varchar(50) DEFAULT NULL,
    private String roleDesc; //varchar(50) DEFAULT NULL,

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
}
