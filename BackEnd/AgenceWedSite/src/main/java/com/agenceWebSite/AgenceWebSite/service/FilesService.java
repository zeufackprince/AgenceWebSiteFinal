package com.agenceWebSite.AgenceWebSite.service;

import java.io.File;
import java.io.IOException;

import com.agenceWebSite.AgenceWebSite.entity.Files;
import com.agenceWebSite.AgenceWebSite.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesService {

    @Autowired
    private FileRepository fileRepository;

    private final String FILE_PATH = "C:\\Users\\Prince Levis\\Pictures\\Camera Roll\\";

    public String storeFile(MultipartFile file) throws IOException {
        Files files = Files
                .builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(file.getBytes())
                .build();

        files = fileRepository.save(files);

        if (files.getId() != null) {
            return "File uploaded successfuly into database";
        }

        return null;
    }

    public byte[] getFiles(String fileName) {
        return fileRepository.findByName(fileName).getImageData();
    }

    public String storeDataIntoFileSystem(MultipartFile fil) throws IOException {
        String path = FILE_PATH + fil.getOriginalFilename();

        Files files = Files
                    .builder()
                .name(fil.getOriginalFilename())
                .type(fil.getContentType())
                .imageData(fil.getBytes())
                .path(path)
                .build();

        Files  file = fileRepository.save(files);
        if (file.getId() > 0) {
            return "File uploaded successfuly into database";
        }else {

            throw new IllegalStateException("error");
        }

    }

//    public String storeDataIntoFileSystem(MultipartFile file) throws IOException {
//        String filePath = FILE_PATH + file.getOriginalFilename();
//
//        Files files = Files
//                .builder()
//                .name(file.getOriginalFilename())
//                .path(filePath)
//                .type(file.getContentType())
//                .imageData(file.getBytes())
//                .build();
//
//        files = fileRepository.save(files);
//
////        file.transferTo(new File(filePath));
//
//        if (files.getId() != null) {
//            return "File uploaded successfuly into database";
//        }
//
//        return null;
//    }

    public byte[] downloadFilesFromFileSystem(String fileName) throws IOException {
        String path = fileRepository.findByName(fileName).getPath();

        return java.nio.file.Files.readAllBytes(new File(path).toPath());
    }
}
