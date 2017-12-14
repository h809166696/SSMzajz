package com.hj.po;

import java.io.Serializable;

/**
 * Created by hongjin on 2017/12/12.
 */
public class FileModel implements Serializable {

    private String fileName;
 private String addtime;

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }



    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
