package com.capstone.reservation.service;

import com.capstone.reservation.entity.ReservationEntity;
import com.capstone.reservation.entity.ResponseMessage;
import com.capstone.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    public List<ReservationEntity> getAllReservation() {
        List<ReservationEntity> reservationList = reservationRepository.findAll();
        return reservationList;
    }

    public ReservationEntity getRoomById(Integer id) {
        ReservationEntity reservation = reservationRepository.findById(id).get();
        return reservation;
    }

    public ReservationEntity addReservation(ReservationEntity reservation) {
        ReservationEntity addReservation = reservationRepository.save(reservation);
        return addReservation;
    }

    public ReservationEntity updateReservation(ReservationEntity reservation) {
        ReservationEntity addReservation = reservationRepository.save(reservation);
        return addReservation;
    }

    public ResponseMessage updateRoom(int id) {
        reservationRepository.deleteById(id);
        return new ResponseMessage("Reservation Deleted!!");
    }


}
