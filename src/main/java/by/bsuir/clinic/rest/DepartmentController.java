package by.bsuir.clinic.rest;

import by.bsuir.clinic.dto.DepartmentDto;
import by.bsuir.clinic.service.department.DepartmentService;
import by.bsuir.clinic.utils.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/departments")
public class DepartmentController {

    private FileManager amazonClient;
    private final DepartmentService service;

    @Autowired
    public DepartmentController(DepartmentService service,
                                FileManager amazonClient) {
        this.service = service;
        this.amazonClient = amazonClient;
    }

    @GetMapping(value = "/{id}")
    public DepartmentDto findById(@PathVariable Long id) {
        return service.findDepartmentById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Map create(@Valid @RequestBody DepartmentDto departmentDto) {
        long id = service.save(departmentDto);
        return Collections.singletonMap("departmentId", id);
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

    @PostMapping(value = "/{id}/photo")
    @ResponseStatus(value = HttpStatus.OK)
    public void setPhoto(@RequestPart(value = "file") MultipartFile file,
                         @PathVariable(value = "id") Long departmentId) {
        DepartmentDto department = service.findDepartmentById(departmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        String imageUrl = department.getImageUrl();
        if(imageUrl != null) {
            amazonClient.deleteFileFromS3Bucket(imageUrl);
        }
        String newImageUrl = amazonClient.uploadFile(file);
        department.setImageUrl(newImageUrl);
        service.update(department);
    }

    @ResponseStatus(value=HttpStatus.BAD_REQUEST,
            reason="There is no department with such id")
    @ExceptionHandler(IllegalArgumentException.class)
    public void departmentNotFound() {
    }

    @ResponseStatus(value = HttpStatus.CONFLICT,
            reason = "Department is not clear")
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void cannotDeleteDepartment() {
    }
}
