package com.hj.controller;

import com.hj.po.SysMenu;
import com.hj.po.easyui.DataGrid;
import com.hj.po.easyui.Json;
import com.hj.po.easyui.PageHelper;

import com.hj.service.MenuService;

//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import net.sf.*;
//import org.json.JSONObject;
//import org.json.JSONArray;
import com.alibaba.fastjson.*;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by hongjin on 2017/11/27.
 */
@Controller
@RequestMapping(value = "/menu")
public class MenuController extends BaseController {
@Resource
    private MenuService menuService;
//跳转到菜单表格界面
  @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
      return "menu/list";
  }
  //资源管理页面
  @RequestMapping(value = "/listtree",method = RequestMethod.GET)
    public String listTree(Model model){
        return "menu/list_tree";
  }
  //菜单信息-列表
    @ResponseBody
    @RequestMapping(value = "/datagrid",method = RequestMethod.POST)
    public DataGrid dataGrid(PageHelper page,SysMenu menu){
        DataGrid dg = new DataGrid();
        dg.setTotal(menuService.getDatagridTotal(menu));

        List<SysMenu> menuList = menuService.datagridMenu(page);
        dg.setRows(menuList);
        return dg;
    }
    //菜单列表 树
    @ResponseBody
    @RequestMapping(value = "/treegrid",method = RequestMethod.POST)
    public void treegrid(HttpServletResponse response,PageHelper page){
        List<SysMenu> menuList = menuService.getAll(page);
        String json = createTreeJson(menuList);
        this.write(response,json);
    }

    //保存 新增修改
    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Json add(SysMenu menu){
        Json j = new Json();
        try {
            menuService.saveMenu(menu);
            j.setSuccess(true);
            j.setMsg("保存成功");
            j.setObj(menu);
        }catch (Exception e){
            j.setMsg(e.getMessage());
        }
        return j;
    }
    //获取当前菜单所有子菜单
    @RequestMapping(value = "/sub")
    public void getSub(@RequestParam Integer menuId,HttpServletResponse response){
        List<SysMenu> subMenu = menuService.listSubMenuByParentid(menuId);
        JSONArray arr = (JSONArray) JSONArray.toJSON(subMenu);

        this.write(response,arr.toString());
    }
    //删除
    @ResponseBody
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public Json deleteMenu(SysMenu menu){
        Json j = new Json();
        try {
            menuService.deleteMenuById(menu.getId());
            j.setSuccess(true);
            j.setMsg("删除成功");
        }catch (Exception e){
            j.setMsg(e.getMessage());
        }
        return j;
    }
    //创建树json
    private String createTreeJson(List<SysMenu> list){
        JSONArray rootArray = new JSONArray();

        for (SysMenu menu:list
             ) {
            if (menu.getParentid() == 0){
                JSONObject rootObj = createBranch(list,menu);
                rootArray.add(rootObj);
            }
        }
        return rootArray.toString();
    }

    private JSONObject createBranch(List<SysMenu> list,SysMenu currentNode){
        //将javabean对象解析成为JSON对象
        JSONObject currenObj = (JSONObject) JSONObject.toJSON(currentNode);
       JSONArray childArray = new JSONArray();
        for (SysMenu newNode:list
             ) {
            if((newNode.getParentid() != 0)&&(newNode.getParentid().compareTo(currentNode.getId()) == 0)){
                JSONObject childObj = createBranch(list,newNode);
                childArray.add(childObj);
            }
        }
        if (!childArray.isEmpty()){
            currenObj.put("children",childArray);
        }
        return currenObj;

    }
}
