package com.hj.dao;

import com.hj.po.systemLog;
import org.apache.ibatis.annotations.Param;

/**
 * Created by hongjin on 2018/1/4.
 */
public interface systemLogMapper {
   public void saveLog(@Param("systemLog1") systemLog systemLog1);
}
