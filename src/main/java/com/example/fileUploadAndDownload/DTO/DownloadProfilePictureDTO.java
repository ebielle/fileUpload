package com.example.fileUploadAndDownload.DTO;

import com.example.fileUploadAndDownload.entities.User;

public class DownloadProfilePictureDTO {

    private User user;
    private byte[] profilePicture;

    public DownloadProfilePictureDTO(){}

    public DownloadProfilePictureDTO(User user, byte[] profilePicture) {
        this.user = user;
        this.profilePicture = profilePicture;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
}
