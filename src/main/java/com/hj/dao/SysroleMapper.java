package com.hj.dao;

import com.hj.po.RoleMenu;
import com.hj.po.Sysrole;
import com.hj.po.UserRole;
import com.hj.po.easyui.PageHelper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hongjin on 2017/12/7.
 */
public interface SysroleMapper {
    public List<Sysrole> getAllRole(@Param("sysrole") Sysrole sysrole);
    public void editUserRole(@Param("sysRole") UserRole sysrole);
    public void addUserRole(@Param("sysRole") UserRole sysrole);
   public void deleteUserRole(Integer UID);

   //对角色表的增删改查操作
    public List<Sysrole> datagrid(@Param("sysrole") Sysrole sysrole, @Param("page")PageHelper page);
   public long datagridTotal(@Param("sysrole") Sysrole sysrole);
   public void editRole(@Param("sysRole") Sysrole sysrole);
    public void addRole(@Param("sysRole") Sysrole sysrole);
    public void deleteRole(Integer id);

    //对rolemenu中间表的操作
    public void deleteRoleMenu(@Param("roleMenu") RoleMenu roleMenu);
    public void addRoleMenu(@Param("roleMenu") RoleMenu roleMenu);
}
