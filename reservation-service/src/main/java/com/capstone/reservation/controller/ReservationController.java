package com.capstone.reservation.controller;

import com.capstone.reservation.dto.ResponseDto;
import com.capstone.reservation.entity.ReservationEntity;
import com.capstone.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class ReservationController {
    @Autowired
    ReservationService reservationService;

    @GetMapping("/getAllReservation")
    public ResponseEntity<ResponseDto> getAllReservation() {
        try {
            List<ReservationEntity> allReservations = reservationService.getAllReservation();
            if(allReservations.size() < 1) {
                throw new Exception();
            } else {
                ResponseDto response = new ResponseDto(200, "Successfully get all Reservation!!", allReservations);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(new ResponseDto(403, "Data no exist!!", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/getReservation/{reservationId}")
    public ResponseEntity<ResponseDto> getReservationById(@PathVariable("reservationId") int id) {
        try {
            ReservationEntity reservation = reservationService.getRoomById(id);
            ResponseDto response = new ResponseDto(200, "Successfully get Reservation!!", reservation);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(new ResponseDto(403, "Data no exist!!", new ArrayList<>()), HttpStatus.FORBIDDEN);

        }
    }

    @PostMapping("/addReservation")
    public ResponseEntity<ResponseDto> addReservation(@RequestBody ReservationEntity reservation) {
        try {
            ReservationEntity addReservation = reservationService.addReservation(reservation);
            ResponseDto response = new ResponseDto(200, "Successfully add reservation!!", addReservation);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(403, "Failed add Reservation!", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/updateReservation")
    public ResponseEntity<ResponseDto> updateReservation(@RequestBody ReservationEntity reservation) {
        try {
            ReservationEntity updateReservation = reservationService.updateReservation(reservation);
            ResponseDto response = new ResponseDto(200, "Successfully update Reservation", updateReservation);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(403, "Failed update Reservation!", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

}
