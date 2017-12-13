package com.hj.controller;

import com.hj.po.FileModel;
import com.hj.po.easyui.DataGrid;
import com.hj.po.easyui.Json;
import com.hj.po.easyui.PageHelper;
import com.hj.service.ArticleService;
import com.hj.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletResponse;
/**
 * Created by hongjin on 2017/12/12.
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
  private FileService fileService;
    /**
     * 文件上传功能
     * @param file
     * @return
     * @throws IOException
     */

    @RequestMapping(value="/upload",method=RequestMethod.POST)
    @ResponseBody

    public Json upload(MultipartFile file, HttpServletRequest request) throws IOException {
        Json j = new Json();
        try {
            String path = request.getSession().getServletContext().getRealPath("")+"/Images/file";
            String fileName = file.getOriginalFilename();
            File dir = new File(path,fileName);
            if(!dir.exists()){
                dir.mkdirs();
            }
            //MultipartFile自带的解析方法
            file.transferTo(dir);
            j.setSuccess(true);
            j.setMsg("保存文件成功");
            FileModel fileModel = new FileModel();
            fileModel.setFileName(fileName);
            j.setObj(fileModel);
        }catch (Exception e){
            j.setMsg(e.getMessage());
        }

        return j;
    }

    /**
     * 文件下载功能
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/down")
    public void down(HttpServletRequest request,HttpServletResponse response,String downFileName) throws Exception{
        //模拟文件，myfile.txt为需要下载的文件
        String fileName = request.getSession().getServletContext().getRealPath("")+"/Images/file/"+downFileName;
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
        //假如以中文名下载的话
        String filename = downFileName;
        //转码，免得文件名中文乱码
        filename = URLEncoder.encode(filename,"UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        out.close();
    }
    @ResponseBody
   @RequestMapping(value = "/getDataGrid")
    public DataGrid getFileList(FileModel fileModel,PageHelper pageHelper){
        return fileService.getDatagrid(fileModel,pageHelper);
   }
   @ResponseBody
   @RequestMapping(value = "/addFile",method = RequestMethod.POST)
    public Json addFile(FileModel fileModel){
        return fileService.addFile(fileModel);
   }
}
