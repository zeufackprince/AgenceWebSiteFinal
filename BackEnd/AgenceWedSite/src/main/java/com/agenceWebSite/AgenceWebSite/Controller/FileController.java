package com.agenceWebSite.AgenceWebSite.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/file/")
public class FileController {

    public static final String path = "images";

    @PostMapping("/upload")
    public String uploadFileHandler(@RequestPart MultipartFile file) throws IOException, IOException {
//        String uploadedFileName = fileService.uploadFile(path, file);
        String uploadedFileName = file.getOriginalFilename();

        // to get the file path
        String filePath = path + File.separator + uploadedFileName;

        // create file object
        File f = new File(path);
        if(!f.exists()) {
            f.mkdir();
        }

        // copy the file or upload file to the path
        Files.copy(file.getInputStream(), Paths.get(filePath));

//        return fileName;
        return  uploadedFileName;
    }

    @GetMapping(value = "/{fileName}")
    public void serveFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException {
//        InputStream resourceFile = fileService.getResourceFile(path, fileName);
        String filePath = path + File.separator + fileName;

        InputStream resourceFile =  new FileInputStream(filePath);

        response.setContentType(MediaType.IMAGE_PNG_VALUE);

        StreamUtils.copy(resourceFile, response.getOutputStream());
    }
}

