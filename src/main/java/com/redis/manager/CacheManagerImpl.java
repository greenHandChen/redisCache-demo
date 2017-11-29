package com.redis.manager;

import com.redis.cache.Cache;
import com.redis.cache.CommonCache;
import com.redis.cache.StudentCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Created by enHui.Chen on 2017/11/21 0021.
 */
public class CacheManagerImpl implements CacheManager {
    private HashMap<String, Cache> mapCache = new HashMap<>();
    private List<Cache> caches;

    /**
     * @Author: enHui.Chen
     * @Description: 覆盖缓存，并且初始化
     * @Data 2017/11/22 0022
     */
    @Override
    public void setCaches(List<Cache> caches) {
        this.caches = caches;
        if (this.caches != null && this.caches.size() > 0) {
            caches.forEach(cache -> {
                this.mapCache.put(cache.getName(), cache);
                cache.init();
            });
        }
    }

    /**
     * @Author: enHui.Chen
     * @Description: 根据key获取缓存
     * @Data 2017/11/22 0022
     */
    @Override
    public <T> Cache<T> getCache(String cacheName) {
        return this.mapCache.get(cacheName);
    }

    /**
     * @Author: enHui.Chen
     * @Description: 添加缓存，不进行初始化
     * @Data 2017/11/22 0022
     */
    @Override
    public void addCache(Cache<?> cache) {
        if (this.caches == null) {
            this.caches = new ArrayList<>();
        }
        if (!this.caches.contains(cache)) {
            this.caches.add(cache);
        }
        this.mapCache.put(cache.getName(), cache);
    }

    /**
     * @Author: enHui.Chen
     * @Description: 获取所有缓存
     * @Data 2017/11/22 0022
     */
    @Override
    public List<Cache> getCaches() {
        return this.caches;
    }

    /**
     * @Author: enHui.Chen
     * @Description: 项目启动时的初始化方法
     * @Data 2017/11/22 0022
     */
    public void cachesInit(ApplicationContext applicationContext) {
        // 获取Cache体系所有的单例
        Map<String, Cache> cacheMap = applicationContext.getBeansOfType(Cache.class);
        if (cacheMap != null) {
            // 1.遍历cacheMap
            // 2.如果有cache不在集合内并且cache的name不为空,则添加到list集合中,并且调用cahe的init();
            cacheMap.forEach((key, value) -> {
                if (!this.caches.contains(value)) {
                    System.out.println(value);
                    if (StringUtils.isEmpty(value.getName())) {
                        throw new RuntimeException("cache name can't be empty");
                    }
                    this.addCache(value);
                    value.init();
                }
            });
        }
    }
}
