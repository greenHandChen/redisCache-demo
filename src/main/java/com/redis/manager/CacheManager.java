package com.redis.manager;

import com.redis.cache.Cache;

import java.util.List;

/**
 * Created by enHui.Chen on 2017/11/21 0021.
 */
public interface CacheManager {
    /**
     * @Author: enHui.Chen
     * @Description: 根据key获取缓存
     * @Data 2017/11/22 0022
     */
    <T> Cache<T> getCache(String cacheName);

    /**
     * @Author: enHui.Chen
     * @Description: 覆盖缓存，并且初始化
     * @Data 2017/11/22 0022
     */
    void setCaches(List<Cache> caches);

    /**
     * @Author: enHui.Chen
     * @Description: 添加缓存，不进行初始化
     * @Data 2017/11/22 0022
     */
    void addCache(Cache<?> cache);

    /**
     * @Author: enHui.Chen
     * @Description: 获取所有缓存
     * @Data 2017/11/22 0022
     */
    List<Cache> getCaches();
}
