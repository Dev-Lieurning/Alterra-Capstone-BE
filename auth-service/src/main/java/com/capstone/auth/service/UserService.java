package com.capstone.auth.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.capstone.auth.entity.ResponseMessage;
import com.capstone.auth.entity.Role;
import com.capstone.auth.entity.UserEntity;
import com.capstone.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired(required = false)
    UserRepository userRepository;

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${bucketAws}")
    private String bucket;
    @Value("${regionAws}")
    private String region;

    private final PasswordEncoder passwordEncoder;

    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users;
    }

    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id).get();
    }

    public UserEntity addUser(UserEntity user) {
        if(user.getFiles() != null) {
            List<String> linkS3 = uploadFileToS3(user.getFiles());
            user.setImage(linkS3.get(0));
            user.setFiles(null);
        }
        user.setRole(Role.USER);
        UserEntity addUser = userRepository.save(user);
        return addUser;
    }

    public UserEntity updateUser(UserEntity user) {
        if(user.getFiles() != null) {
            List<String> linkS3 = uploadFileToS3(user.getFiles());
            user.setImage(linkS3.get(0));
            user.setFiles(null);
        }
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity updUser = userRepository.save(user);
        return updUser;
    }

    public ResponseMessage deleteUser(int id) {
        userRepository.deleteById(id);
        return new ResponseMessage("User Deleted!!");
    }

    public List<String> uploadFileToS3(List<MultipartFile> files) {
        try{
            List<String> response = new ArrayList<>();
            for(MultipartFile file : files){
                File modifiedFile = new File(file.getOriginalFilename());
                FileOutputStream os = new FileOutputStream(modifiedFile);
                os.write(file.getBytes());

                String filename = System.currentTimeMillis()+"_"+file.getOriginalFilename();
                amazonS3.putObject(new PutObjectRequest(bucket, filename, modifiedFile).withCannedAcl(CannedAccessControlList.PublicRead));
                response.add(amazonS3.getUrl(bucket, filename).toString());

                os.close();
                modifiedFile.delete();
            }
            return response;
        } catch (IOException e) {
            return new ArrayList<>();
        }

    }

}
