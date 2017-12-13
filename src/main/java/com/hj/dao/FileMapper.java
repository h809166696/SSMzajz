package com.hj.dao;

import com.hj.po.FileModel;
import com.hj.po.easyui.PageHelper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hongjin on 2017/12/13.
 */
public interface FileMapper {
   public List<FileModel> getFileList(@Param("fileModel") FileModel flieModel, @Param("page")PageHelper page);
   public long getFileTotal(@Param("fileModel") FileModel flieModel);
   public void addFile(@Param("fileModel") FileModel fileModel);
}
