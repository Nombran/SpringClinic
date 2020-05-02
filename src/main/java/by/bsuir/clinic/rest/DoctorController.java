package by.bsuir.clinic.rest;

import by.bsuir.clinic.dto.DoctorDto;
import by.bsuir.clinic.service.doctor.DoctorService;
import by.bsuir.clinic.utils.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/doctors")
public class DoctorController {

    private FileManager amazonClient;
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService,
                            FileManager amazonClient) {
        this.doctorService = doctorService;
        this.amazonClient = amazonClient;
    }

    @GetMapping
    public List<DoctorDto> find(@RequestParam(required = false, name = "userId") Long userId) {
       if(userId != null) {
           Optional<DoctorDto> doctorDto = doctorService.findDoctorByUserId(userId);
           return Collections.singletonList(doctorDto.orElseThrow(
                   () -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
       } else {
           return doctorService.findAll();
       }
    }

    @GetMapping(value = "/{id}")
    public DoctorDto findById(@PathVariable(value = "id") Long id) {
        return doctorService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody DoctorDto doctorDto) {
        doctorService.save(doctorDto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody DoctorDto doctorDto, @PathVariable(value = "id") Long id) {
        doctorDto.setId(id);
        Optional<DoctorDto> updated = doctorService.update(doctorDto);
        if(!updated.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Cannot update doctor");
        }
    }

    @PostMapping(value = "/{id}/photo")
    @ResponseStatus(value = HttpStatus.OK)
    public void setPhoto(@RequestPart(value = "file") MultipartFile file,
                         @PathVariable(value = "id") Long doctorId) {
        DoctorDto doctor = doctorService.findById(doctorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        String imageUrl = doctor.getImageUrl();
        if(imageUrl != null) {
            amazonClient.deleteFileFromS3Bucket(imageUrl);
        }
        imageUrl = amazonClient.uploadFile(file);
        doctor.setImageUrl(imageUrl);
        doctorService.update(doctor);
    }


    @ResponseStatus(value=HttpStatus.BAD_REQUEST,
            reason="There is no doctor with such id")
    @ExceptionHandler(IllegalArgumentException.class)
    public void doctorNotFound() {
    }


}
