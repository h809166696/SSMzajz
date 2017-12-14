package com.hj.po;
import java.io.Serializable;
import java.util.List;
/**
 * Created by hongjin on 2017/11/17.
 */
public class SysMenu implements Serializable {
    /**
     * 菜单ID
     */
    private Integer id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 父级菜单ID 0表示根节点
     */
    private Integer parentid;
    /**
     * 菜单顺序
     */
    private String sequence;
    /**
     * 菜单图标样式
     */
    private String iconCls;
    /**
     * 菜单链接地址
     */
    private String url;
    /**
     * 可用状态
     */
    private Integer enable;
    /**
     * 子节点个数
     */
    private Integer countChildrens;

    private SysMenu parentMenu;
    private List<SysMenu> subMenu;
    private boolean hasMenu = false;

    public Integer getCountChildrens() {
        return countChildrens;
    }
    public void setCountChildrens(Integer countChildrens) {
        this.countChildrens = countChildrens;
    }
    public Integer getEnable() {
        return enable;
    }
    public void setEnable(Integer enable) {
        this.enable = enable;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getParentid() {
        return parentid;
    }
    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }
    public String getSequence() {
        return sequence;
    }
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
    public String getIconCls() {
        return iconCls;
    }
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public SysMenu getParentMenu() {
        return parentMenu;
    }
    public void setParentMenu(SysMenu parentMenu) {
        this.parentMenu = parentMenu;
    }
    public List<SysMenu> getSubMenu() {
        return subMenu;
    }
    public void setSubMenu(List<SysMenu> subMenu) {
        this.subMenu = subMenu;
    }
    public boolean isHasMenu() {
        return hasMenu;
    }
    public void setHasMenu(boolean hasMenu) {
        this.hasMenu = hasMenu;
    }

}
