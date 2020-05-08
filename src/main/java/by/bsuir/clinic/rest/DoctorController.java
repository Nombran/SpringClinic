package by.bsuir.clinic.rest;

import by.bsuir.clinic.dto.DoctorDto;
import by.bsuir.clinic.dto.TicketForDoctorDto;
import by.bsuir.clinic.service.doctor.DoctorService;
import by.bsuir.clinic.utils.FileManager;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    public Map create(@RequestBody DoctorDto doctorDto) {
       long id =  doctorService.save(doctorDto);
       return Collections.singletonMap("doctorId", id);
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


    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable(name = "id")Long id) {
        doctorService.delete(id);
    }

    @ResponseStatus(value=HttpStatus.BAD_REQUEST,
            reason="There is no doctor with such id")
    @ExceptionHandler(IllegalArgumentException.class)
    public void doctorNotFound() {
    }

    @GetMapping(value = "/{id}/appointments")
    public List<TicketForDoctorDto> findDoctorAppointments(
            @PathVariable(name = "id")long doctorId,
            @RequestParam(name = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate day){
        if(!doctorService.findById(doctorId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if(day != null) {
            return doctorService.findDoctorAppointmentsByDay(doctorId, day);
        } else {
            return doctorService.findDoctorAppointments(doctorId);
        }
    }
}
