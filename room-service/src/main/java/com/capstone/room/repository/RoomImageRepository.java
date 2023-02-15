package com.capstone.room.repository;

import com.capstone.room.entity.RoomImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomImageRepository extends JpaRepository<RoomImageEntity, Integer> {
    RoomImageEntity findByFileName(String fileName);
    String FIND_BY_ID_ROOM = "select * from room_image where id_room = ?1";
    @Query(value = FIND_BY_ID_ROOM, nativeQuery = true)
    List<RoomImageEntity> findByIdRoom(int id_room);
}
