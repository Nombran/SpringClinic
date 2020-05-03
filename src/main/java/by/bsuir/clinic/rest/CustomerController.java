package by.bsuir.clinic.rest;

import by.bsuir.clinic.dto.CustomerDto;
import by.bsuir.clinic.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    private final CustomerService service;


    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public List<CustomerDto> find(@RequestParam(name = "userId", required = false) Long userId) {
        if(userId != null) {
            CustomerDto customer = service.findCustomerByUserId(userId).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
            );
            return Collections.singletonList(customer);
        } else {
            return service.findAll();
        }
    }

    @GetMapping(value = "/{id}")
    public CustomerDto findById(@PathVariable(name = "id") Long id) {
        return service.findCustomerById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody CustomerDto customerDto) {
        service.save(customerDto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody CustomerDto customerDto, @PathVariable(name = "id") Long id) {
        customerDto.setId(id);
        service.update(customerDto).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Cannot update customer")
        );
    }

    @GetMapping(value = "/{id}/card")
    public void getMedicalCard(@PathVariable(name = "id") Long userId) {

    }
}
