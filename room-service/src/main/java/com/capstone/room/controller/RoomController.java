package com.capstone.room.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.capstone.room.dto.RequestRoomDto;
import com.capstone.room.dto.ResponseDto;
import com.capstone.room.entity.ResponseMessage;
import com.capstone.room.entity.RoomEntity;
import com.capstone.room.entity.RoomImageEntity;
import com.capstone.room.repository.RoomImageRepository;
import com.capstone.room.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class RoomController {
    @Autowired
    RoomService roomService;

    @Autowired
    private AmazonS3 amazonS3;

    @GetMapping("/getAllRooms")
    public ResponseEntity<ResponseDto> getAllRooms() {
        try {
            List<RoomEntity> allRooms = roomService.getAllRooms();
            if(allRooms.size() < 1) {
                throw new Exception();
            } else {
                ResponseDto response = new ResponseDto(200, "Successfully get all Room!", allRooms);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(403, "Data no Exist!", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(path = "/getRoom/{roomId}")
    public ResponseEntity<ResponseDto> getRoomById(@PathVariable("roomId") int id) {
        try {
            RoomEntity room = roomService.getRoomById(id);
            ResponseEntity<ResponseDto> response = new ResponseEntity<>(new ResponseDto(200, "Successfully get Data Room", room), HttpStatus.OK);
            return response;
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(403, "Data no Exist!", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/addRoom")
    public ResponseEntity<ResponseDto> addRoom(@ModelAttribute RequestRoomDto requestRoom) {
        try {
            RoomEntity room = RoomEntity.builder()
                                .name(requestRoom.getName())
                                .type(requestRoom.getType())
                                .price(requestRoom.getPrice())
                                .location(requestRoom.getLocation())
                                .max_guest(requestRoom.getMax_guest())
                                .description(requestRoom.getDescription())
                                .build();
            RoomEntity response = roomService.addRoom(room);
            roomService.uploadFileToS3(requestRoom.getFiles(), response.getId());
            return new ResponseEntity<>(new ResponseDto(200, "Successfully add Room", response), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(403, "Failed add Room", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/updateRoom")
    public ResponseEntity<ResponseDto> updateRoom (@ModelAttribute RequestRoomDto requestRoom) {
        try {
            RoomEntity room = RoomEntity.builder()
                                .id(requestRoom.getId())
                                .name(requestRoom.getName())
                                .type(requestRoom.getType())
                                .price(requestRoom.getPrice())
                                .location(requestRoom.getLocation())
                                .max_guest(requestRoom.getMax_guest())
                                .description(requestRoom.getDescription())
                                .build();
            RoomEntity response = roomService.updateRoom(room);
            return new ResponseEntity<>(new ResponseDto(200, "Succesfully update Room", response), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(403, "Failed update Room", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/deleteRoom/{roomId}")
    public ResponseEntity<ResponseDto> deleteRoom (@PathVariable("roomId") int id) {
        try {
            ResponseMessage response = roomService.deleteRoom(id);
            return new ResponseEntity<>(new ResponseDto(200, "Succesfully delete Room!!", response), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(403,"Failed Delete Room", new ArrayList<>()), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(path = "/file/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("fileName") String fileName) {
        RoomImageEntity file = roomService.getImageByName(fileName);
        byte[] data = roomService.previewFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .contentType(MediaType.valueOf(file.getType_data()))
                .body(resource);
    }
}
