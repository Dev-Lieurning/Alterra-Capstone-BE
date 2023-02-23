package com.capstone.reservation.service;

import com.capstone.reservation.dto.ResponseDto;
import com.capstone.reservation.dto.RoomDTO;
import com.capstone.reservation.entity.ReservationEntity;
import com.capstone.reservation.entity.ReservationUser;
import com.capstone.reservation.entity.ResponseMessage;
import com.capstone.reservation.entity.UserEntity;
import com.capstone.reservation.repository.ReservationRepository;
import com.capstone.reservation.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

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
        UserEntity user = userRepository.getById(id);
        ModelMapper modelMapper = new ModelMapper();
        for(ReservationEntity reservation : reservationList) {
            ReservationUser reserveUser = modelMapper.map(reservation, ReservationUser.class);

            ResponseDto restRoom = restTemplate.getForObject("http://api.capstone-meeting.online/room/getRoom/"+ reservation.getId_room(), ResponseDto.class);

            RoomDTO roomDTO = modelMapper.map(restRoom.getData(), RoomDTO.class);

            reserveUser.setUser(user.getName());
            reserveUser.setRoom(roomDTO.getName());
            reservationUsers.add(reserveUser);
        }
        return reservationUsers;
    }
    public ResponseMessage deleteReservation(int id) {
        reservationRepository.deleteById(id);
        return new ResponseMessage("Reservation Deleted!!");
    }


}
