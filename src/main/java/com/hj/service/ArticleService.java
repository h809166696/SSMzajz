package com.hj.service;

import com.hj.dao.ArticleMapper;
import com.hj.po.Article;
import com.hj.po.easyui.DataGrid;
import com.hj.po.easyui.Json;
import com.hj.po.easyui.PageHelper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hongjin on 2017/12/11.
 */
@Service
public class ArticleService {
    @Resource
    ArticleMapper articleMapper;
    public DataGrid getArticle(String searchText, PageHelper page){
        page.setStart((page.getPage()-1)*page.getRows());
        page.setEnd(page.getRows());
        page.setOrder(" desc");
        page.setSort("lastEditTime");
       DataGrid dg = new DataGrid();
       dg.setRows(articleMapper.getArticle(searchText,page));
       dg.setTotal(articleMapper.getArticleTotal(searchText));
       return dg;
    }

    public Json editArticleDelete(Article article){
        Json j = new Json();
        try {
            articleMapper.editArticleDelete(article);
            j.setSuccess(true);
            j.setMsg("编辑帖子成功");
            j.setObj(article);
        }catch (Exception e){
            j.setMsg(e.getMessage());
        }
        return j;
    }
}
