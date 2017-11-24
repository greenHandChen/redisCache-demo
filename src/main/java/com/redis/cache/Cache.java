package com.redis.cache;

import com.redis.dto.Student;

/**
 * Created by enHui.Chen on 2017/11/21 0021.
 */
public interface Cache<T> {
    void init();

    String getName();

    void setName(String var1);

    T getValue(String field);

    void setValue(String key, T field);

    void remove(String field);

    String getCacheKey(T var1);

    void reload();

    void clear();
}
