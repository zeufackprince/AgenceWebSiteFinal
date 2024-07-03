//package AgencyWebSite.AgencyWebSite.Service;
//
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//public class FileService {
//
//    public String uploadFile(String path, MultipartFile file) throws IOException, IOException {
//        // get name of the file
//        String fileName = file.getOriginalFilename();
//
//        // to get the file path
//        String filePath = path + File.separator + fileName;
//
//        // create file object
//        File f = new File(path);
//        if(!f.exists()) {
//            f.mkdir();
//        }
//
//        // copy the file or upload file to the path
//        Files.copy(file.getInputStream(), Paths.get(filePath));
//
//        return fileName;
//    }
//
//
//    public InputStream getResourceFile(String path, String fileName) throws  FileNotFoundException {
//        String filePath = path + File.separator + fileName;
//        return new FileInputStream(filePath);
//    }
//}
//
