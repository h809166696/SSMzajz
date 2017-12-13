package com.hj.po;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import com.hj.po.User;
/**
 * Created by hongjin on 2017/12/11.
 */
public class Article implements Serializable{
    private String ARTICLENAME;
    private Integer UID;
    private String ARTICLECONTENT;
    private String ARTICLEIMG;
    private String ARTICLEDATE;
    private String DISCUSSCOUNT;
    private String LOOKCOUNT;
    private Integer ARTICLEID;
    private Integer commentCount;
    private String lastEditTime;
    private Integer isDelete;
    private Integer isJing;
    private Integer isDing;

    public Integer getIsJing() {
        return isJing;
    }

    public void setIsJing(Integer isJing) {
        this.isJing = isJing;
    }

    public Integer getIsDing() {
        return isDing;
    }

    public void setIsDing(Integer isDing) {
        this.isDing = isDing;
    }

    private User user;
    @JSONField(name = "ARTICLENAME")
    public String getARTICLENAME() {
        return ARTICLENAME;
    }

    public void setARTICLENAME(String ARTICLENAME) {
        this.ARTICLENAME = ARTICLENAME;
    }
    @JSONField(name = "UID")
    public Integer getUID() {
        return UID;
    }

    public void setUID(Integer UID) {
        this.UID = UID;
    }
    @JSONField(name = "ARTICLECONTENT")
    public String getARTICLECONTENT() {
        return ARTICLECONTENT;
    }

    public void setARTICLECONTENT(String ARTICLECONTENT) {
        this.ARTICLECONTENT = ARTICLECONTENT;
    }
    @JSONField(name = "ARTICLEIMG")
    public String getARTICLEIMG() {
        return ARTICLEIMG;
    }

    public void setARTICLEIMG(String ARTICLEIMG) {
        this.ARTICLEIMG = ARTICLEIMG;
    }
    @JSONField(name = "ARTICLEDATE")
    public String getARTICLEDATE() {
        return ARTICLEDATE;
    }

    public void setARTICLEDATE(String ARTICLEDATE) {
        this.ARTICLEDATE = ARTICLEDATE;
    }
    @JSONField(name = "DISCUSSCOUNT")
    public String getDISCUSSCOUNT() {
        return DISCUSSCOUNT;
    }

    public void setDISCUSSCOUNT(String DISCUSSCOUNT) {
        this.DISCUSSCOUNT = DISCUSSCOUNT;
    }
    @JSONField(name = "LOOKCOUNT")
    public String getLOOKCOUNT() {
        return LOOKCOUNT;
    }

    public void setLOOKCOUNT(String LOOKCOUNT) {
        this.LOOKCOUNT = LOOKCOUNT;
    }
    @JSONField(name = "ARTICLEID")
    public Integer getARTICLEID() {
        return ARTICLEID;
    }

    public void setARTICLEID(Integer ARTICLEID) {
        this.ARTICLEID = ARTICLEID;
    }
    @JSONField(name = "commentCount")
    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
    @JSONField(name = "lastEditTime")
    public String getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(String lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
    @JSONField(name = "isDelete")
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
