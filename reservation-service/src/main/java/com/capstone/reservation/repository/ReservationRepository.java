package com.capstone.reservation.repository;

import com.capstone.reservation.entity.ReservationEntity;
import com.capstone.reservation.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {

    String FIND_RESERVATION_BY_ID_USER = "select * from reservation where id_user = ?1";
    @Query(value = FIND_RESERVATION_BY_ID_USER, nativeQuery = true)
    List<ReservationEntity> findReservationByUserId(int id);
}
