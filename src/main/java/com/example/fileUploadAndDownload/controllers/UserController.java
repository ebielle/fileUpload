package com.example.fileUploadAndDownload.controllers;

import com.example.fileUploadAndDownload.DTO.DownloadProfilePictureDTO;
import com.example.fileUploadAndDownload.entities.User;
import com.example.fileUploadAndDownload.services.FileStorageService;
import com.example.fileUploadAndDownload.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public User createUser(@RequestBody User user){
        return userService.createNewUser(user);
    }

    @PostMapping("/profile/{id}")
    public User uploadProfilePicture(@PathVariable Integer id, @RequestParam MultipartFile profilePicture) throws IOException {
        return userService.uploadProfilePicture(id, profilePicture);
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/profile/{id}")
    public @ResponseBody byte[] getProfilePicture(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        DownloadProfilePictureDTO downloadProfilePictureDTO = userService.downloadProfilePicture(id);
        String fileName = downloadProfilePictureDTO.getUser().getProfilePicture();
        if(fileName == null) throw new IOException("User does not have a profile picture");
        String extension = FilenameUtils.getExtension(fileName);
        switch (extension) {
            case "gif":
                response.setContentType(MediaType.IMAGE_GIF_VALUE);
                break;
            case "jpg":
            case "jpeg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
        }
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        return downloadProfilePictureDTO.getProfilePicture();
    }

    @PutMapping("/update/{id}")
    public User updateUserById(@PathVariable Integer userId, @RequestBody User user){
        return userService.updateUserById(userId, user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Integer id) throws IOException {
        userService.deleteUserById(id);
    }

    @DeleteMapping("/delete")
    public void deleteAllUsers(){
        userService.deleteAllUsers();
    }
}
