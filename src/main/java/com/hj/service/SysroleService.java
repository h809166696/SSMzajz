package com.hj.service;

import com.hj.dao.SysroleMapper;
import com.hj.po.RoleMenu;
import com.hj.po.Sysrole;
import com.hj.po.UserRole;
import com.hj.po.easyui.PageHelper;
import com.hj.utils.StringCatagroy;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hongjin on 2017/12/7.
 */
@Service
public class SysroleService {
    @Resource
    private SysroleMapper sysroleMapper;
    //根据条件获取所有角色信息
    public List<Sysrole> getAllRole(Sysrole sysrole){
        return sysroleMapper.getAllRole(sysrole);
    }
    //新增或者编辑相关信息
    public void save(UserRole userRole){
    if(userRole.getUID() == null || userRole.getRoleName() == null){
      sysroleMapper.addUserRole(userRole);
    }else {
        sysroleMapper.editUserRole(userRole);
    }
    }
    //删除对应的userRole
    public void deleteUserRole(Integer UID){
        sysroleMapper.deleteUserRole(UID);
    }
    //角色表相关增删改查操作
    public List<Sysrole> datagrid(Sysrole sysrole,PageHelper page){
        page.setStart((page.getPage()-1) * page.getRows());
        page.setEnd(page.getRows());
        return sysroleMapper.datagrid(sysrole,page);
    };
    public long datagridTotal( Sysrole sysrole){
        return sysroleMapper.datagridTotal(sysrole);
    };

    public void saveRole(Sysrole sysrole){
      if(sysrole.getId() != null){
          sysroleMapper.editRole(sysrole);
      }else {
          sysroleMapper.addRole(sysrole);
      }
    };

    public void deleteRole(Integer id){
      sysroleMapper.deleteRole(id);
    };

    public void deleteRoleMenu(RoleMenu roleMenu){
        sysroleMapper.deleteRoleMenu(roleMenu);
    }
    public void addRoleMenu(RoleMenu roleMenu){
        sysroleMapper.addRoleMenu(roleMenu);
    }
}
