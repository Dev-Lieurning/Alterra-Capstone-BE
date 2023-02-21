package com.capstone.room.repository;

import com.capstone.room.entity.RoomEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RoomRedisRepository {

    @Autowired
    private RoomRepository roomRepository;
    private final String HASH_VALUE = "Room";

    @Cacheable(value = HASH_VALUE ,key = "#id")
    public RoomEntity findById(int id){
        System.out.println("ga ke caching bre");
        return roomRepository.findById(id).get();
    }

    @Cacheable(value = HASH_VALUE)
    public List<RoomEntity> findAll(){
        System.out.println("ini ga ke caching juga wey");
        return roomRepository.findAll();
    }

    @Transactional
    @CacheEvict(value = HASH_VALUE, key = "T(org.springframework.cache.interceptor.SimpleKey).EMPTY")
    public RoomEntity save(RoomEntity room){
        return roomRepository.save(room);
    }

    @CachePut(value = HASH_VALUE, key = "#room.id")
    @CacheEvict(value = HASH_VALUE, key = "T(org.springframework.cache.interceptor.SimpleKey).EMPTY")
    public RoomEntity update(RoomEntity room) {
        return roomRepository.save(room);
    }

    @CacheEvict(value = HASH_VALUE, key = "#id")
    public void deleteById(int id) {
        roomRepository.deleteById(id);
    }
}
