package by.bsuir.clinic.utils;

import org.springframework.web.multipart.MultipartFile;

public interface FileManager {
    String uploadFile(MultipartFile multipartFile);

    void deleteFileFromS3Bucket(String fileUrl);
}
