package com.agenceWebSite.AgenceWebSite.controller;


import java.io.IOException;


import com.agenceWebSite.AgenceWebSite.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api")
public class FilesController {

    @Autowired
    private FilesService filesService;

    @PostMapping("/uploadFilesIntoDB")
    public ResponseEntity<String> storeFilesIntoDB(@RequestPart("file")MultipartFile file) throws IOException {
        String response = filesService.storeFile(file);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getFileByName/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        byte[] imageData = filesService.getFiles(fileName);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }

    @PostMapping("/uploadFilesIntoFileSystem")
    public ResponseEntity<String> uploadFileIntoFileSystem(@RequestPart("file")MultipartFile file) throws IOException {
        String response = filesService.storeDataIntoFileSystem(file);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getFilesFromFileSystem/{fileName}")
    public ResponseEntity<byte[]> downloadImageFromFileSystem(@PathVariable String fileName) {
        byte[] imageData = filesService.getFiles(fileName);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }

}

