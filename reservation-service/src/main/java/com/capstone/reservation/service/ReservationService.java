package com.capstone.reservation.service;

import com.capstone.reservation.entity.ReservationEntity;
import com.capstone.reservation.entity.ReservationUser;
import com.capstone.reservation.entity.ResponseMessage;
import com.capstone.reservation.repository.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    private ModelMapper modelMapper;

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

    public List<ReservationUser> getReservationByUserId(int id) {
        List<ReservationEntity> reservationList = reservationRepository.findReservationByUserId(id);
        List<ReservationUser> reservationUsers = new ArrayList<>();
        for(ReservationEntity reservation : reservationList) {
            ModelMapper modelMapper = new ModelMapper();
            ReservationUser reservUser = modelMapper.map(reservation, ReservationUser.class);
        }

        return reservationUsers;
    }
    public ResponseMessage deleteReservation(int id) {
        reservationRepository.deleteById(id);
        return new ResponseMessage("Reservation Deleted!!");
    }


}
