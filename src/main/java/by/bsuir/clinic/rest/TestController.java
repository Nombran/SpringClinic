package by.bsuir.clinic.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Value("#{ systemEnvironment['ACCESS_KEY'] }")
    private String accessKey;

    @Value("#{ systemEnvironment['SECRET_KEY'] }")
    private String secretKey;

    @GetMapping
    public String getPropes() {
        return accessKey + "   " + secretKey + "   ";
    }
}
