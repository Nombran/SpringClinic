package by.bsuir.clinic;

import by.bsuir.clinic.config.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MethodSecurityConfig.class,
                                PersistenceJPAConfig.class,
                                SecurityConfig.class,
                                SpringConfig.class,
                                WebConfig.class})
@WebAppConfiguration
public class DepartmentsControllerTest {

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
    public void getMethodReturnStatusTest() throws Exception {
        this.mockMvc.perform(get("/departments"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void contentTypeTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/departments"))
                .andDo(print())
                .andReturn();
        Assert.assertEquals("application/json",mvcResult.getResponse().getContentType());
    }

    @Test
    public void invalidDepartmentCreationTest() throws Exception {
        this.mockMvc.perform((post("/departments")))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

}
