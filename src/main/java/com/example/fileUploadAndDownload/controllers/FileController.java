package com.example.fileUploadAndDownload.controllers;

import com.example.fileUploadAndDownload.services.FileStorageService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public List<String> upload(@RequestParam MultipartFile[] files) throws IOException {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            String singleUploadedFileName = fileStorageService.upload(file);
            fileNames.add(singleUploadedFileName);
        }
        return fileNames;
    }

    @GetMapping("/download")
    public @ResponseBody byte[] download(@RequestParam String fileName, HttpServletResponse response) throws IOException {
        System.out.println("Downloading " + fileName);
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
        return fileStorageService.download(fileName);
    }
}
