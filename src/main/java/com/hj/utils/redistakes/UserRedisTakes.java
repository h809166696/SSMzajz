package com.hj.utils.redistakes;

import com.hj.po.User;
import java.util.logging.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hongjin on 2017/12/13.
 */
@Component("UserRedisTakes")
public class UserRedisTakes implements RedisBaiseTakes<String,String,User>{
    @Resource(name="redisTemplate")
    private RedisTemplate redisTemplate;
    private Logger logger = Logger.getLogger(String.valueOf(UserRedisTakes.class));
    @Override
    public void add(String key, String value) {
        if(redisTemplate==null){
            logger.warning("redisTemplate 实例化失败");
            return;
        }else{
            redisTemplate.opsForValue().set(key,value);
        }
    }

    @Override
    public void addObj(String objectKey, String key, User object) {
        if(redisTemplate==null){
            logger.warning("redisTemplate 实例化失败");
            return;
        }else{
            redisTemplate.opsForHash().put(objectKey,key,object);
        }
    }

    @Override
    public void delete(String key) {

    }

    @Override
    public void delete(List<String> listKeys) {

    }

    @Override
    public void deletObj(String objecyKey, String key) {

    }

    @Override
    public void update(String key, String value) {

    }

    @Override
    public void updateObj(String objectKey, String key, User object) {

    }

    @Override
    public String get(String key) {
        String value = (String) redisTemplate.opsForValue().get(key);
        return value;
    }

    @Override
    public User getObj(String objectKey, String key) {
        User seeUser = (User) redisTemplate.opsForHash().get(objectKey,key);
        return seeUser;
    }
}
