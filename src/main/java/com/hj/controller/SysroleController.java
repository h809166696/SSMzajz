package com.hj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hj.po.RoleMenu;
import com.hj.po.SysMenu;
import com.hj.po.Sysrole;
import com.hj.po.easyui.DataGrid;
import com.hj.po.easyui.Json;
import com.hj.po.easyui.PageHelper;
import com.hj.po.easyui.Tree;
import com.hj.service.SysroleService;
import com.hj.service.UserService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongjin on 2017/12/7.
 */
@Controller
@RequestMapping(value = "/sysrole")
public class SysroleController extends BaseController{
    @Autowired
   private SysroleService sysroleService;
    @Autowired
    private UserService userService;
    @ResponseBody
    @RequestMapping(value = "/allRole")
    public List<Sysrole> allrole(Sysrole sysrole){
        List<Sysrole> roleArray = sysroleService.getAllRole(sysrole);
        Sysrole emptyRole = new Sysrole();
        emptyRole.setId(0);
        emptyRole.setName("无");
        roleArray.add(emptyRole);
        return roleArray;
    }
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String sysRoleList(){
        return "system/roleList";
    }
    @ResponseBody
    @RequestMapping(value = "/datagrid",method = RequestMethod.POST)
    public DataGrid datagrid (Sysrole sysrole, PageHelper page){
        DataGrid dg = new DataGrid();
        dg.setTotal(sysroleService.datagridTotal(sysrole));
        dg.setRows(sysroleService.datagrid(sysrole,page));
        return dg;
    }
    @ResponseBody
    @RequestMapping(value = "/saveRole",method = RequestMethod.POST)
    public Json saveRole(Sysrole sysrole){
        Json j = new Json();
        try {
            if (sysrole.getId() == null || sysrole.getId() == 0){
                j.setMsg("新增角色成功");
            }else{
                j.setMsg("编辑角色成功");
            }
            sysroleService.saveRole(sysrole);
            j.setSuccess(true);
            j.setObj(sysrole);
        }catch (Exception e){
            j.setMsg(e.getMessage());
        }
        return j;
    }
    @ResponseBody
    @RequestMapping(value = "/deleteRole",method = RequestMethod.POST)
    public Json deleteRole(Sysrole sysrole){
        Json j = new Json();
        try {

            j.setMsg("删除角色成功");

            sysroleService.deleteRole(sysrole.getId());
            j.setSuccess(true);
            j.setObj(sysrole);
        }catch (Exception e){
            j.setMsg(e.getMessage());
        }
        return j;
    }

   @ResponseBody
    @RequestMapping(value = "/changeRoleMenu",method = RequestMethod.POST)
//    @RequestBody List<RoleMenu> roleMenuArr
    public Json editRoleMenu(String roleMenuStr, HttpServletResponse response){
//        if (jsonStr.size() >0 ){
//
//        }
       Json j = new Json();
        List<RoleMenu> list = JSONObject.parseArray(roleMenuStr, RoleMenu.class);
        List<RoleMenu> addRoleMenuList = new ArrayList<RoleMenu>();
        Integer roleId = list.get(0).getRole_id();
        Sysrole sysrole = new Sysrole();
        sysrole.setId(roleId);
        List<SysMenu> menuList = userService.getMenuByRoleId(sysrole);
        for (RoleMenu roleMenu:list
             ) {
            boolean isFind = false;
            for (SysMenu sysmenu:menuList
                 ) {
                if(sysmenu.getId().equals(roleMenu.getMenu_id())){
                    isFind = true;
                    menuList.remove(sysmenu);
                    break;
                }
            }
            if(!isFind) addRoleMenuList.add(roleMenu);
        }
        try {
            for (RoleMenu roleM:addRoleMenuList
                    ) {
                 sysroleService.addRoleMenu(roleM);
            }
            for (SysMenu sysM:menuList
                 ) {
                RoleMenu tmpRoleMenu = new RoleMenu();
                tmpRoleMenu.setMenu_id(sysM.getId());
                tmpRoleMenu.setRole_id(roleId);

                sysroleService.deleteRoleMenu(tmpRoleMenu);
            }

            j.setMsg("分配权限成功");
            j.setSuccess(true);

        }catch (Exception e){
            j.setMsg(e.getMessage());
        }

       return j;
    }
}
