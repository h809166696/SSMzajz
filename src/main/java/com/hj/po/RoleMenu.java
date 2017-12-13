package com.hj.po;

/**
 * Created by hongjin on 2017/12/10.
 */
public class RoleMenu {
    private Integer id;
    private Integer role_id;
    private Integer menu_id;

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }

    public void setRole_id(Integer role_id){
        this.role_id = role_id;
    }
    public Integer getRole_id(){
        return this.role_id;
    }

    public void setMenu_id(Integer menu_id){
        this.menu_id = menu_id;
    }
    public Integer getMenu_id(){
        return this.menu_id;
    }

}
