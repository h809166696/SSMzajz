package com.hj.po;

import java.io.Serializable;

/**
 * Created by hongjin on 2017/12/6.
 */
public class UserRole extends User implements Serializable{
    private Integer roleId;
    private String roleName;

    public void setRoleId(int roleId){this.roleId = roleId;};
    public Integer getRoleId(){return this.roleId;};

    public void setRoleName(String roleName){this.roleName = roleName;};
    public String getRoleName(){return  this.roleName;};
}
