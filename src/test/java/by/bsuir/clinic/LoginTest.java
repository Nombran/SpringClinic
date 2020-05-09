package by.bsuir.clinic;

import by.bsuir.clinic.config.*;
import by.bsuir.clinic.dto.AuthenticationRequestDto;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.annotation.SecurityTestExecutionListeners;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MethodSecurityConfig.class,
        PersistenceJPAConfig.class,
        SecurityConfig.class,
        SpringConfig.class,
        WebConfig.class})
@WebAppConfiguration
public class LoginTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @ClassRule
    public static final EnvironmentVariables environmentVariables = new EnvironmentVariables()
            .set("JDBC_DATABASE_URL", "jdbc:postgresql://localhost:5432/clinic-db")
            .set("JDBC_DATABASE_USERNAME", "postgres")
            .set("JDBC_DATABASE_PASSWORD", "123")
            .set("ACCESS_KEY", "123")
            .set("SECRET_KEY", "123");

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    public void badCredentialsTest() throws Exception {
        AuthenticationRequestDto loginDto = new AuthenticationRequestDto("abv", "123");
        String jsonDto = new Gson().toJson(loginDto);
        this.mockMvc.perform(post("/auth/login").content(jsonDto).contentType("application/json"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void successfulAuthorization() throws Exception {
        AuthenticationRequestDto loginDto = new AuthenticationRequestDto("admin", "admin");
        String jsonDto = new Gson().toJson(loginDto);
        this.mockMvc.perform(post("/auth/login").content(jsonDto).contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void invalidAuthorizationRequestBody() throws Exception {
        this.mockMvc.perform((post("/auth/login")))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


}
