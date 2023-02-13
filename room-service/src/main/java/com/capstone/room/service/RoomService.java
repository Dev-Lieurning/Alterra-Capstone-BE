package com.capstone.room.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.capstone.room.entity.ResponseMessage;
import com.capstone.room.entity.RoomEntity;
import com.capstone.room.entity.RoomImageEntity;
import com.capstone.room.repository.RoomImageRepository;
import com.capstone.room.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class RoomService {

    @Autowired(required = false)
    RoomRepository roomRepository;

    @Autowired(required = false)
    RoomImageRepository roomImageRepository;

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${bucketAws}")
    private String bucket;
    @Value("${regionAws}")
    private String region;
    public List<RoomEntity> getAllRooms() {
        List<RoomEntity> roomList = roomRepository.findAll();
        return roomList;
    }

    public RoomEntity getRoomById(Integer id) {
        RoomEntity room = roomRepository.findById(id).get();
        return room;
    }

    public RoomEntity addRoom(RoomEntity room) {
        RoomEntity addRoom = roomRepository.save(room);
        return addRoom;
    }

    public RoomEntity updateRoom(RoomEntity room) {
        RoomEntity updateRoom = roomRepository.save(room);
        return updateRoom;
    }

    public ResponseMessage deleteRoom(int id){
        roomRepository.deleteById(id);
        return new ResponseMessage("Room Deleted!!");
    }

    public RoomImageEntity getImageByName(String fileName) {
        RoomImageEntity image = roomImageRepository.findByFileName(fileName);
        return image;
    }

    public void uploadFileToS3(List<MultipartFile> files, int idRoom) {
        try{
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
                                            .link("https://"+bucket+".s3."+region+".amazonaws.com/"+filename.replace(" ","+"))
                                            .build();
                roomImageRepository.save(roomImage);
                os.close();
                modifiedFile.delete();
            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public byte[] previewFile(String fileName){
        try {
            S3Object s3Object = amazonS3.getObject(bucket, fileName);
            S3ObjectInputStream inputStream = s3Object.getObjectContent();

            byte[] data = IOUtils.toByteArray(inputStream);
            return data;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
