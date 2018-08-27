package com.cimr.boot.redis.utils;

import java.util.Map;

import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;

public class MyHashMapper<T, K, V> implements HashMapper<T, K, V>{
	private HashMapper<T, K, V> mapper;

    public MyHashMapper(HashMapper<T, K, V> mapper) {
        // this.mapper = mapper;
        this.mapper = mapper;
    }

    @Override
    public Map<K, V> toHash(T obj) {
        Map<K, V> map = mapper.toHash(obj);
        // 去掉Object类中的class属性生成的key/value
        map.remove("class");
        return map;
    }

    @Override
    public T fromHash(Map<K, V> map) {
        return mapper.fromHash(map);
    }
    

    public static <T, K, V> HashMapper<T, K, V> getInstance(Class<T> tClazz, Class<K> kClazz,
            Class<V> vClazz) {
        return new MyHashMapper<T, K, V>((HashMapper<T, K, V>) new DecoratingStringHashMapper<T>(
                new MyBeanUtilsHashMapper<T>(tClazz)));
    }
}
