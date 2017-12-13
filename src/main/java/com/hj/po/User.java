package com.hj.po;
import  java.io.Serializable;
//import org.codehaus.jackson.annotate.JsonIgnore;
//import org.codehaus.jackson.annotate.JsonProperty;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
/**
 * Created by hongjin on 2017/11/13.
 */
public class User implements Serializable{
//    @JsonProperty(value = "UID")
    private Integer UID;//ID

//    @JsonProperty(value = "NAME")
    private String NAME;

//    @JsonProperty(value = "SEX")
    private int SEX;

//    @JsonProperty(value = "ACCOUNT")
    private String ACCOUNT;

//    @JsonProperty(value = "PASSWORD")
    private String PASSWORD;

//    @JsonProperty(value = "HEADURL")
    private String HEADURL;

//    @JsonProperty(value = "DESCRIPTION")
    private String DESCRIPTION;
//    @JsonProperty(value = "regtime")
    private String regtime;

//    @JsonProperty(value = "MANAGER")
    private String MANAGER;

//    @JsonProperty(value = "KOULING")
    private String KOULING;

//    @JsonIgnore
@JSONField(name = "UID")
    public Integer getUID() {
        return UID;
    }

//    @JsonIgnore
    public void setUID(Integer id){
        this.UID = id;
    }

//    @JsonIgnore
@JSONField(name = "NAME")
    public String getNAME() {
        return NAME;
    }

//    @JsonIgnore
    public void setNAME(String NAME) {
        this.NAME = NAME == null? null:NAME.trim();
    }

//    @JsonIgnore
    public void  setSEX(Integer SEX){
        this.SEX = SEX;
    }

//    @JsonIgnore
@JSONField(name = "SEX")
    public  int getSEX(){
        return this.SEX;
    }

//    @JsonIgnore
    public void setACCOUNT(String ACCOUNT){
        this.ACCOUNT = ACCOUNT;
    }

//    @JsonIgnore
@JSONField(name = "ACCOUNT")
    public String getACCOUNT(){
        return this.ACCOUNT;
    }

//    @JsonIgnore
    public void setPASSWORD(String PASSWORD){
        this.PASSWORD = PASSWORD;
    }

//    @JsonIgnore
@JSONField(name = "PASSWORD")
    public String getPASSWORD(){
        return this.PASSWORD;
    }

//    @JsonIgnore
    public void setHEADURL(String HEADURL){
        this.HEADURL = HEADURL;
    }

//    @JsonIgnore
@JSONField(name = "HEADURL")
    public String getHEADURL(){
        return this.HEADURL;
    }

//    @JsonIgnore
    public void setDESCRIPTION(String DESCRIPTION){
        this.DESCRIPTION = DESCRIPTION;
    }

//    @JsonIgnore
@JSONField(name = "DESCRIPTION")
    public String getDESCRIPTION(){
        return this.DESCRIPTION;
    }

//    @JsonIgnore

    public String getRegtime() {
        return regtime;
    }

//    @JsonIgnore
    public void setRegtime(String regtime) {
        this.regtime = regtime;
    }

//    @JsonIgnore
@JSONField(name = "MANAGER")
    public String getMANAGER() {
        return MANAGER;
    }

//    @JsonIgnore
    public void setMANAGER(String MANAGER) {
        this.MANAGER = MANAGER;
    }

//    @JsonIgnore
@JSONField(name = "KOULING")
    public String getKOULING() {
        return KOULING;
    }

//    @JsonIgnore

    public void setKOULING(String KOULING) {
        this.KOULING = MANAGER;
    }
}
