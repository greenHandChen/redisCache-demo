package com.redis.cache;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.IOException;

/**
 * Created by enHui.Chen on 2017/11/21 0021.
 */
public class CommonCache<T> implements Cache<T> {
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected RedisTemplate<String, String> redisTemplate;
    protected Class<T> entityClass;
    private String category = "enhui:cache:";
    private String name;
    protected RedisSerializer<String> strSerializer;
    private Boolean loadOnStartUp = false;

    @Override
    public void init() {
        if (loadOnStartUp) {
            this.strSerializer = redisTemplate.getStringSerializer();
        }
    }

     /**
       * @Author: enHui.Chen
       * @Description: 获取对应缓存的值
       * @Data 2017/11/22 0022
       */
    @Override
    public T getValue(String field) {
        return redisTemplate.execute((RedisCallback<T>) connection -> {
            try {
                return this.hGet(connection, this.getFullCacheName(), field);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

     /**
       * @Author: enHui.Chen
       * @Description: 设置对应缓存
       * @Data 2017/11/22 0022
       */
    @Override
    public void setValue(String field, T value) {
        redisTemplate.execute((RedisCallback) connection -> {
            try {
                hSet(connection, field, value);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

     /**
       * @Author: enHui.Chen
       * @Description: hget
       * @Data 2017/11/22 0022
       */
    public T hGet(RedisConnection connection, String key, String field) throws IOException {
        byte[] byteValue = connection.hGet(this.strSerializer.serialize(key), this.strSerializer.serialize(field));
        if (byteValue != null) {
            return this.objectMapper.readValue(byteValue, entityClass);
        }
        return null;
    }

     /**
       * @Author: enHui.Chen
       * @Description: hset
       * @Data 2017/11/22 0022
       */
    public void hSet(RedisConnection connection, String var1, T var2) throws JsonProcessingException {
        connection.hSet(strSerializer.serialize(this.getFullCacheName()),
                strSerializer.serialize(var1),
                objectMapper.writeValueAsBytes(var2));
    }

     /**
       * @Author: enHui.Chen
       * @Description: 移除对应缓存
       * @Data 2017/11/22 0022
       */
    @Override
    public void remove(String field) {
        redisTemplate.execute((RedisCallback) connection -> {
            connection.hDel(this.strSerializer.serialize(this.getFullCacheName()), this.strSerializer.serialize(field));
            return null;
        });
    }

    @Override
    public String getCacheKey(T var1) {
        return null;
    }

     /**
       * @Author: enHui.Chen
       * @Description: 刷新对应缓存
       * @Data 2017/11/22 0022
       */
    @Override
    public void reload() {
        this.clear();
        this.init();
    }

     /**
       * @Author: enHui.Chen
       * @Description: 清除对应所有缓存
       * @Data 2017/11/22 0022
       */
    @Override
    public void clear() {
         redisTemplate.execute((RedisCallback) connection->{
             connection.del(this.strSerializer.serialize(this.getFullCacheName()));
             return null;
         });
    }

    public String getFullCacheName() {
        return this.category + this.name;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public RedisSerializer<String> getStrSerializer() {
        return strSerializer;
    }

    public void setStrSerializer(RedisSerializer<String> strSerializer) {
        this.strSerializer = strSerializer;
    }

    public Boolean getLoadOnStartUp() {
        return loadOnStartUp;
    }

    public void setLoadOnStartUp(Boolean loadOnStartUp) {
        this.loadOnStartUp = loadOnStartUp;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
