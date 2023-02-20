package com.capstone.payment.repository;

import com.capstone.payment.entity.PaymentEntity;
import com.capstone.payment.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {

    String UPDATE_STATUS_BY_ID = "update payment set status = :status where id = :id";
    String FIND_BY_ID_RESERVATION = "SELECT * FROM payment WHERE id_reservation = :id_reservation";

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = UPDATE_STATUS_BY_ID, nativeQuery = true)
    void updateStatusById(@Param("status") String status, @Param("id") int id);

    @Query(value = FIND_BY_ID_RESERVATION, nativeQuery = true)
    List<PaymentEntity> findByIdReservation(@Param("id_reservation") int id_reservation);
}
