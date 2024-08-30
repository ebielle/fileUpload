package com.example.fileUploadAndDownload.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${fileRepositoryFolder}")
    private String fileRepositoryFolder;

    public String upload(MultipartFile file) throws IOException {

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = UUID.randomUUID().toString();
        String completeFileName = fileName + "." + extension;

        File finalFolder = new File(fileRepositoryFolder);
        if(!finalFolder.exists()) throw new IOException("This folder does not exist");
        if(!finalFolder.isDirectory()) throw new IOException("This folder is not a directory");

        File finalDestination = new File(fileRepositoryFolder + File.separator + completeFileName);
        if(finalDestination.exists()) throw new IOException("This folder already exists");

        file.transferTo(finalDestination);
        return completeFileName;
    }

    public byte[] download(String fileName) throws IOException {
        File fileFromRepository = new File(fileRepositoryFolder + File.separator + fileName);
        if(!fileFromRepository.exists()) throw new IOException("The file does not exist");
        return IOUtils.toByteArray(new FileInputStream(fileFromRepository));
    }

    public void remove(String profilePicture) throws IOException {
        File fileFromRepository = new File(fileRepositoryFolder + File.separator + profilePicture);
        if (!fileFromRepository.exists()) return;
        if (!fileFromRepository.delete()) throw new IOException("Cannot delete file");
    }
}
