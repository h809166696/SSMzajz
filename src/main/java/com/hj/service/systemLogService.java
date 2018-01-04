package com.hj.service;

import com.hj.dao.systemLogMapper;
import com.hj.po.systemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hongjin on 2018/1/4.
 */
@Service
public class systemLogService {
    @Autowired
    systemLogMapper mapper;
    public void saveLog(systemLog systemLog1){
        try {
            mapper.saveLog(systemLog1);
        }catch (Exception e){
            String message = e.getMessage();
            System.out.print(message);
//            systemLog
        }

    }
}
