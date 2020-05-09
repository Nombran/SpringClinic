package by.bsuir.clinic.rest;

import by.bsuir.clinic.dto.AppointmentDto;
import by.bsuir.clinic.dto.TicketsCreationDto;
import by.bsuir.clinic.service.appointment.AppointmentService;
import by.bsuir.clinic.service.doctor.DoctorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/appointments")
public class AppointmentController {

    private final AppointmentService service;

    @Autowired
    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<AppointmentDto> find() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public AppointmentDto findById(@PathVariable(name = "id") Long id) {
        return service.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody TicketsCreationDto ticketsCreationDto) {
        if(!service.save(ticketsCreationDto)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable(name = "id") long id,
                       @RequestBody AppointmentDto appointmentDto) {
        appointmentDto.setId(id);
        if(!service.update(appointmentDto).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Cannot update appointment");
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable(name = "id") long id) {
        service.delete(id);
    }


    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DoctorNotFoundException.class)
    public void illegalArgumentHandler() {
    }

}
