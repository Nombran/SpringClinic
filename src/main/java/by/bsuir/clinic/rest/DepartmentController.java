package by.bsuir.clinic.rest;

import by.bsuir.clinic.dto.DepartmentDto;
import by.bsuir.clinic.service.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/departments")
public class DepartmentController {

    private final DepartmentService service;

    @Autowired
    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public DepartmentDto findById(@PathVariable Long id) {
        return service.findDepartmentById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@Valid @RequestBody DepartmentDto departmentDto) {
        service.save(departmentDto);
    }

    @GetMapping
    public List<DepartmentDto> findAll() {
        return service.findAll();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@Valid @RequestBody DepartmentDto departmentDto, @PathVariable Long id) {
        departmentDto.setId(id);
        Optional<DepartmentDto> updated  = service.update(departmentDto);
        if(!updated.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Cannot update department");
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
    }


    @ResponseStatus(value=HttpStatus.BAD_REQUEST,
            reason="There is no department with such id")
    @ExceptionHandler(IllegalArgumentException.class)
    public void departmentNotFound() {
    }
}
