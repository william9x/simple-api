package simpleapi2.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import simpleapi2.Api2Application;
import simpleapi2.entity.user.UserEntity;
import simpleapi2.io.request.UserSignUpRequest;
import simpleapi2.io.request.UserUpdateRequest;
import simpleapi2.repository.user.IUserRepository;

import java.text.ParseException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Api2Application.class)
@AutoConfigureMockMvc
class UserControllerItegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private IUserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterTestClass
    public void resetDb() {
        userRepository.deleteAll();
    }

    @Test
    void testGetAllUser() throws Exception {
        createTestUser("test@test.com");

        mvc.perform(get("/api/users")
                .header("Authentication", "simple_api_key_for_authentication")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testGetUser() throws Exception {
        mvc.perform(get("/api/users/test@test.com")
                .header("Authentication", "simple_api_key_for_authentication")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("test@test.com"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testCreateUser() throws Exception {

        UserSignUpRequest req = new UserSignUpRequest();
        req.setUsername("test123");
        req.setEmail("test123@test.com");
        req.setAddress("test");

        mvc.perform(post("/api/users")
                .header("Authentication", "simple_api_key_for_authentication")
                .content(asJsonString(req))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value(req.getUsername()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testUpdateUser() throws Exception {

        UserUpdateRequest req = new UserUpdateRequest();
        req.setAddress("test update address");

        mvc.perform(put("/api/users/testId")
                .header("Authentication", "simple_api_key_for_authentication")
                .content(asJsonString(req))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.address").value(req.getAddress()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testDeleteUser() throws Exception {

        mvc.perform(delete("/api/users/testId")
                .header("Authentication", "simple_api_key_for_authentication")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    private void createTestUser(String email) throws ParseException {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testEntity");
        userEntity.setEmail(email);
        userEntity.setAddress("testAddress");
        userEntity.setUserId("testId");
        userRepository.save(userEntity);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}