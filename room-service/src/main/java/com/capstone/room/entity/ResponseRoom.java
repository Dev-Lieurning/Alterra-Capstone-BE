package com.capstone.room.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseRoom<T> {
    private int id;
    private String name;
    private String type;
    private Double price;
    private String location;
    private int max_guest;
    private String description;
    private T image;
}
