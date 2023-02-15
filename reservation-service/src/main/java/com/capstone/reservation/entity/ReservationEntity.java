package com.capstone.reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservation")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = true)
    private int id_room;
    @Column(nullable = true)
    private int id_user;
    @Column(nullable = true)
    private int number_of_persons;
    private Double total_price;
    private LocalDateTime check_in;
    private LocalDateTime check_out;
    private LocalDateTime order_date;
    private String status;
}
