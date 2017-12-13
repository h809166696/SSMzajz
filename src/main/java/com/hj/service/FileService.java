package com.hj.service;

import com.hj.dao.FileMapper;
import com.hj.po.FileModel;
import com.hj.po.easyui.DataGrid;
import com.hj.po.easyui.Json;
import com.hj.po.easyui.PageHelper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hongjin on 2017/12/13.
 */
@Service
public class FileService {
    @Resource
   private FileMapper fileMapper;
//    public List<FileModel> getFileList(PageHelper page){
//        return fileMapper.getFileList(page);
//    }
//    public long getFileTotal(){
//        return fileMapper.getFileTotal();
//    }
    public DataGrid getDatagrid(FileModel flieModel,PageHelper page){
        page.setStart((page.getPage()-1)*page.getRows());
        page.setEnd(page.getRows());
        DataGrid dg = new DataGrid();
        dg.setTotal(fileMapper.getFileTotal(flieModel));

        dg.setRows(fileMapper.getFileList(flieModel,page));
        return dg;
    }
    public Json addFile(FileModel fileModel){
        Json json = new Json();
        try {
            fileMapper.addFile(fileModel);
            json.setObj(fileModel);
            json.setMsg("添加文件成功");
            json.setSuccess(true);
        }catch (Exception e){
            json.setMsg(e.getMessage());
        }
       return json;
    }
}
