package com.hj.dao;

import com.hj.po.Article;
import com.hj.po.easyui.PageHelper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hongjin on 2017/12/11.
 */
public interface ArticleMapper {
    public List<Article> getArticle(@Param("searchText") String searchText, @Param("page")PageHelper page);
    public void editArticleDelete(@Param("article") Article article);
    public long getArticleTotal(@Param("searchText")String searchText);
}
