package com.hj.po;

import java.io.Serializable;

/**
 * Created by hongjin on 2017/12/7.
 */
public class Sysrole implements Serializable{
    private Integer id;
    private String name;

    public void setId(int id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
