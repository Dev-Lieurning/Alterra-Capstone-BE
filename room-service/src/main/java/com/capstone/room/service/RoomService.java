package com.capstone.room.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.capstone.room.entity.ResponseMessage;
import com.capstone.room.entity.ResponseRoom;
import com.capstone.room.entity.RoomEntity;
import com.capstone.room.entity.RoomImageEntity;
import com.capstone.room.repository.RoomImageRepository;
import com.capstone.room.repository.RoomRedisRepository;
import com.capstone.room.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    @Autowired(required = false)
    RoomRepository roomRepository;

    @Autowired(required = false)
    RoomImageRepository roomImageRepository;

    @Autowired(required = false)
    RoomRedisRepository roomRedisRepository;

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${bucketAws}")
    private String bucket;
    @Value("${regionAws}")
    private String region;

    public static final String HASH_KEY = "Room";

    public List<ResponseRoom> getAllRooms() {
        List<ResponseRoom> responseRooms = new ArrayList<>();
        List<RoomEntity> roomList = roomRedisRepository.findAll();
        for(RoomEntity room : roomList) {
            responseRooms.add(ResponseRoom.builder()
                            .id(room.getId())
                            .name(room.getName())
                            .type(room.getType())
                            .price(room.getPrice())
                            .location(room.getLocation())
                            .max_guest(room.getMax_guest())
                            .description(room.getDescription())
                            .image(roomImageRepository.findByIdRoom(room.getId()))
                            .build());
        }

        return responseRooms;
    }

    public ResponseRoom getRoomById(Integer id) {
        RoomEntity roomData = roomRedisRepository.findById(id);
        ResponseRoom room = ResponseRoom.builder()
                                .id(roomData.getId())
                                .name(roomData.getName())
                                .type(roomData.getType())
                                .price(roomData.getPrice())
                                .location(roomData.getLocation())
                                .max_guest(roomData.getMax_guest())
                                .description(roomData.getDescription())
                                .image(roomImageRepository.findByIdRoom(roomData.getId()))
                                .build();
        return room;
    }

    public List<ResponseRoom> getRoomByLocation(String location) {
        List<ResponseRoom> responseRooms = new ArrayList<>();
        List<RoomEntity> roomList = roomRepository.findByLocation(location);
        for(RoomEntity room : roomList) {
            responseRooms.add(ResponseRoom.builder()
                    .id(room.getId())
                    .name(room.getName())
                    .type(room.getType())
                    .price(room.getPrice())
                    .location(room.getLocation())
                    .max_guest(room.getMax_guest())
                    .description(room.getDescription())
                    .image(roomImageRepository.findByIdRoom(room.getId()))
                    .build());
        }

        return responseRooms;
    }

    public RoomEntity addRoom(RoomEntity room) {
        RoomEntity addRoom = roomRedisRepository.save(room);
        return addRoom;
    }

    public RoomEntity updateRoom(RoomEntity room) {
        RoomEntity updateRoom = roomRedisRepository.update(room);
        return updateRoom;
    }

    public ResponseMessage deleteRoom(int id){
        roomRedisRepository.deleteById(id);
        roomImageRepository.deleteByIdRoom(id);
        return new ResponseMessage("Room Deleted!!");
    }

    public void deleteImage(List<Integer> id) {
        for(int id_image : id) {
            roomImageRepository.deleteById(id_image);
        }
    }

    public RoomImageEntity getImageByName(String fileName) {
        RoomImageEntity image = roomImageRepository.findByFileName(fileName);
        return image;
    }

    public List<RoomImageEntity> uploadFileToS3(List<MultipartFile> files, int idRoom) {
        try{
            List<RoomImageEntity> response = new ArrayList<>();
            for(MultipartFile file : files){
                File modifiedFile = new File(file.getOriginalFilename());
                FileOutputStream os = new FileOutputStream(modifiedFile);
                os.write(file.getBytes());

                String filename = System.currentTimeMillis()+"_"+file.getOriginalFilename();
                amazonS3.putObject(new PutObjectRequest(bucket, filename, modifiedFile).withCannedAcl(CannedAccessControlList.PublicRead));
                RoomImageEntity roomImage = RoomImageEntity.builder()
                                            .id_room(idRoom)
                                            .fileName(filename)
                                            .type_data(file.getContentType())
                                            .link(amazonS3.getUrl(bucket, filename).toString())
                                            .build();
                RoomImageEntity saveImage = roomImageRepository.save(roomImage);
                response.add(saveImage);
                os.close();
                modifiedFile.delete();
            }
            return response;
        } catch (IOException e) {
            return new ArrayList<>();
        }

    }

//    public byte[] previewFile(String fileName){
//        try {
//            S3Object s3Object = amazonS3.getObject(bucket, fileName);
//            S3ObjectInputStream inputStream = s3Object.getObjectContent();
//
//            byte[] data = IOUtils.toByteArray(inputStream);
//            return data;
//        } catch (IOException e){
//            e.printStackTrace();
//            return null;
//        }
//    }
}
