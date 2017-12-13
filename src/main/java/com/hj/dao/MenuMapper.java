package com.hj.dao;
import com.hj.po.SysMenu;
import java.util.List;
import com.hj.po.easyui.PageHelper;
/**
 * Created by hongjin on 2017/11/26.
 */
public interface MenuMapper {
    public List<SysMenu> listAllParentMenu();
    public List<SysMenu> listSubMenuByParentid(Integer parentid);
    public List<SysMenu> getDatagrid();

    public List<SysMenu> getAll(PageHelper page);

    public Long getDatagridTotal(SysMenu menu);

    public List<SysMenu> datagridMenu(PageHelper page);

    public SysMenu getMenuById(Integer menuId);

    public void insertMenu(SysMenu menu);

    public void updateMenu(SysMenu menu);

    public void deleteMenuById(Integer menuId);

    public List<SysMenu> listAllSubMenu();

}
