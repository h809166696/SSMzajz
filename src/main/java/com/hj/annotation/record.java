package com.hj.annotation;

import java.lang.annotation.*;

/**
 * Created by hongjin on 2018/1/4.
 */
@Target(ElementType.METHOD) //表明该注解对成员方法起作用
@Retention(RetentionPolicy.RUNTIME) //在编译以后仍然起作用
@Documented //支持JavaDoc文档注释
public @interface record {
    String actionType() default "默认动作类型";
    String businessLogic() default "默认业务逻辑";
}
