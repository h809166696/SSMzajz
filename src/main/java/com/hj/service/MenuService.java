package com.hj.service;

import com.hj.dao.MenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import com.hj.po.SysMenu;
import java.util.List;
import com.hj.po.easyui.PageHelper;
/**
 * Created by hongjin on 2017/11/27.
 */
@Service
public class MenuService {
    @Resource
    private MenuMapper menuMapper;
    public List<SysMenu> listAllParentMenu(){
        return menuMapper.listAllParentMenu();
    };
    public List<SysMenu> listSubMenuByParentid(Integer parentid){
        return menuMapper.listSubMenuByParentid(parentid);
    };
//    public List<SysMenu> getDatagrid(){
//
//    };

    public List<SysMenu> getAll(PageHelper page){
        page.setStart((page.getPage()-1)*page.getRows());
        page.setEnd(page.getPage()*page.getRows());
        return menuMapper.getAll(page);
    };
 //获取总数
    public Long getDatagridTotal(SysMenu menu){
        return menuMapper.getDatagridTotal(menu);
    };
//获取一级列表
    public List<SysMenu> datagridMenu(PageHelper page){
        page.setStart((page.getPage()-1)*page.getRows());
        page.setEnd(page.getPage()*page.getRows());
        return menuMapper.datagridMenu(page);
    };

    public SysMenu getMenuById(Integer menuId){
        return menuMapper.getMenuById(menuId);
    };

   public void saveMenu(SysMenu menu){
       if (menu.getId() != null && menu.getId().intValue()>0){
           menuMapper.updateMenu(menu);
       }else
       {
           menuMapper.insertMenu(menu);
       }
   };

    public void deleteMenuById(Integer menuId){
        menuMapper.deleteMenuById(menuId);
    };
   public List<SysMenu> listAllMenu(){
       List<SysMenu> rl = this.listAllParentMenu();
       for (SysMenu menu:rl
            ) {
           List<SysMenu> subList = this.listSubMenuByParentid(menu.getId());
           menu.setSubMenu(subList);
       }
       return rl;
   };
    public List<SysMenu> listAllSubMenu(){
        return menuMapper.listAllSubMenu();
    };
}
