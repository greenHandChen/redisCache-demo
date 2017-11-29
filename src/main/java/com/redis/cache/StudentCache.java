package com.redis.cache;

import com.redis.annotation.Id;
import com.redis.dto.BaseDto;

import java.lang.reflect.Field;

/**
 * Created by enHui.Chen on 2017/11/21 0021.
 */
public class StudentCache<T extends BaseDto> extends CommonCache<T> {
    public void init() {
        this.strSerializer = redisTemplate.getStringSerializer();
        initLoad();
    }

    /**
     * @Author: enHui.Chen
     * @Description: 初始化数据(模拟连接数据库)
     * @Data 2017/11/22 0022
     */
    protected void initLoad() {
        try {
            for (int i = 133055, s = 90; i < 133065; i++, s++) {
                T entity = this.entityClass.newInstance();
                Field[] fields = entityClass.getDeclaredFields();
                if (fields != null) {
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(Id.class)) {
                            field.setAccessible(true);
                            field.set(entity, "" + i);
                        }
                        if ("score".equals(field.getName())) {
                            field.setAccessible(true);
                            field.set(entity, "" + s);
                        }
                    }
                }
                super.setValue("" + i, entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
