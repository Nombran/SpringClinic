package by.bsuir.clinic.rest;

import by.bsuir.clinic.dto.FileUrlDto;
import by.bsuir.clinic.utils.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/storage")
public class BucketController {

    private FileManager amazonClient;

    @Autowired
    BucketController(FileManager amazonClient) {
        this.amazonClient = amazonClient;
    }

    @PostMapping
    public FileUrlDto uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return new FileUrlDto(this.amazonClient.uploadFile(file));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteFile(@RequestBody FileUrlDto dto) {
        this.amazonClient.deleteFileFromS3Bucket(dto.getFileUrl());
    }
}