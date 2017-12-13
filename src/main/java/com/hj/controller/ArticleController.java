package com.hj.controller;

import com.hj.po.Article;
import com.hj.po.easyui.DataGrid;
import com.hj.po.easyui.Json;
import com.hj.po.easyui.PageHelper;
import com.hj.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hongjin on 2017/12/11.
 */
@Controller
@RequestMapping(value = "/BBS")
public class ArticleController {
    @Autowired
   private ArticleService articleService;
    @RequestMapping(value = "/articlelist",method = RequestMethod.GET)
    public String articleList(){
        return "BBS/articleList";
    }
    @RequestMapping(value = "/fileList",method = RequestMethod.GET)
    public String discussList(){
        return "BBS/fileManagerList";
    }
    @ResponseBody
    @RequestMapping(value = "/getArticle")
    public DataGrid getArticle(String searchText, PageHelper pageHelper){
        return articleService.getArticle(searchText,pageHelper);
    }
    @ResponseBody
    @RequestMapping(value = "/editArticle",method = RequestMethod.POST)
    public Json editArticle(Article article){
        return articleService.editArticleDelete(article);
    }
}
