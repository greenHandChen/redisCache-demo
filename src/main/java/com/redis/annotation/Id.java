package com.redis.annotation;

import java.lang.annotation.*;

/**
 * Created by enHui.Chen on 2017/11/21 0021.
 * 主键注解
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
}
