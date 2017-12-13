package com.hj.dao;

import com.hj.po.SysMenu;
import com.hj.po.Sysrole;
import com.hj.po.User;
import com.hj.po.UserRole;
import com.hj.po.easyui.PageHelper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hongjin on 2017/11/13.
 */
public interface UserMapper {
    User findUserById(int id);
    public String getUsernameById(@Param("id") int id);
    User findUserByACCOUNT(String ACCOUNT);
    List<SysMenu> getMenuByUserId(@Param("userId") int userId);

    List<SysMenu> getMenuByRoleId(@Param("sysrole") Sysrole sysrole);
  public  Long getDatagridTotal(@Param("user") User user);
    List<User> datagridUser(@Param("page") PageHelper page,@Param("user") User user);
    void addUser(User user);
    void editUser(User user);
    void deleteUser(Integer UID);
   public List<UserRole> datagridUserRole(@Param("page") PageHelper page,@Param("user") User user);

}
