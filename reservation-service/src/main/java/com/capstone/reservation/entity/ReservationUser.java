package com.capstone.reservation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReservationUser {
    private int id;
    private int id_room;
    private String room;
    private int id_user;
    private String user;
    private int number_of_persons;
    private Double total_price;
    private LocalDateTime check_in;
    private LocalDateTime check_out;
    private LocalDateTime order_date;
    private String status;
}
