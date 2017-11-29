package com.redis;

import com.redis.manager.CacheManagerImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by enHui.Chen on 2017/11/21 0021.
 */
// 1.创建CacheManagerImpl时会先创建studentCache
// 2.studentCache会注入objectMapper以及redisTemplate
// 3.最后调用caches字段的set方法，注入studentCache。并且进行初始化。
public class RedisTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext-redis.xml");
        CacheManagerImpl cacheManagerImpl = (CacheManagerImpl) applicationContext.getBean("cacheManagerImpl");
      //  cacheManagerImpl.cachesInit(applicationContext);
    /*    System.out.println("===============获取学生成绩=======================");
        System.out.println("学号:" + student.getStuId() + "分数:" + student.getScore());
        System.out.println("==================================================");*/
    }
}
