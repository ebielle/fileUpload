package com.example.fileUploadAndDownload.services;

import com.example.fileUploadAndDownload.DTO.DownloadProfilePictureDTO;
import com.example.fileUploadAndDownload.entities.User;
import com.example.fileUploadAndDownload.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public User createNewUser(User user){
        return userRepository.save(user);
    }

    public User uploadProfilePicture(Integer id, MultipartFile profilePicture) throws IOException {
        User user = getUserById(id);
        if (user.getProfilePicture() != null){
            fileStorageService.remove(user.getProfilePicture());
        }
        String fileName = fileStorageService.upload(profilePicture);
        user.setProfilePicture(fileName);
        userRepository.save(user);
        return user;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Integer id){
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()) throw new EntityNotFoundException("User not found");
        return userOptional.get();
    }

    public DownloadProfilePictureDTO downloadProfilePicture(Integer id) throws IOException {
        User user = getUserById(id);
        DownloadProfilePictureDTO dto = new DownloadProfilePictureDTO();
        dto.setUser(user);
        if(user.getProfilePicture() == null) return dto;

        byte[] profilePictureBytes = fileStorageService.download(user.getProfilePicture());
        dto.setProfilePicture(profilePictureBytes);
        return dto;
    }

    public User updateUserById(Integer userId, User user){
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isPresent()){
            userRepository.save(user);
        } return user;
    }

    public void deleteUserById(Integer userId) throws IOException {
        User user = getUserById(userId);
        if (user.getProfilePicture() != null) {
            fileStorageService.remove(user.getProfilePicture());
        }
        userRepository.deleteById(userId);
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
    }
}
