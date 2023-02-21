package com.capstone.room.repository;

import com.capstone.room.entity.RoomEntity;
import com.capstone.room.entity.RoomImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {
    String FIND_BY_LOCATION = "select * from room where location in (?1)";
    @Query(value = FIND_BY_LOCATION, nativeQuery = true)
    List<RoomEntity> findByLocation(String location);
}
