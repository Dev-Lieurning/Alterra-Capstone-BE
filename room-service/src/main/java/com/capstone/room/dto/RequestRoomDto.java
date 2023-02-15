package com.capstone.room.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestRoomDto<T> implements Serializable {
    private int id;
    private String name;
    private String type;
    private Double price;
    private String location;
    private int max_guest;
    private String description;
    private List<T> files = new ArrayList<>();
}
