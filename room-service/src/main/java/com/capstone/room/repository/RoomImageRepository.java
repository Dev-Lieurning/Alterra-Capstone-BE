package com.capstone.room.repository;

import com.capstone.room.entity.RoomImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomImageRepository extends JpaRepository<RoomImageEntity, Integer> {
    RoomImageEntity findByFileName(String fileName);
}
