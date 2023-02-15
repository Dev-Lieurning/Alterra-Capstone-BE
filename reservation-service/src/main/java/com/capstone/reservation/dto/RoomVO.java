package com.capstone.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomVO {
    private int id;
    private String name;
    private String type;
    private Double price;
    private String location;
    private int max_guest;
    private String description;
}
