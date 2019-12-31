package simpleapi2.controller.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import simpleapi2.dto.user.UserDTO;
import simpleapi2.entity.user.UserEntity;
import simpleapi2.io.request.UserSignUpRequest;
import simpleapi2.io.request.UserUpdateRequest;
import simpleapi2.io.response.OperationStatus;
import simpleapi2.io.response.UserDetailsResponse;
import simpleapi2.io.response.UserFullDetailsResponse;
import simpleapi2.repository.user.IUserRepository;
import simpleapi2.service.user.UserService;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private UserDTO userDTO;
    private UserEntity userEntity;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userDTO = new UserDTO();
        userDTO.setUsername("test DTO");
        userDTO.setEmail("test@test.com");
        userDTO.setAddress("test address");

        userEntity = new UserEntity();
        userEntity.setUsername("test Entity");
        userEntity.setEmail("test@test.com");
        userEntity.setAddress("test address");
    }

    @Test
    void testGetAllUser() throws Exception {
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        userDTOS.add(userDTO);

        when(userService.getUser()).thenReturn(userDTOS);

        mvc.perform(get("/api/users")
                .header("Authentication", "simple_api_key_for_authentication")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUser() throws Exception {

        when(userService.getUser(anyString())).thenReturn(userDTO);

        mvc.perform(get("/api/users/test")
                .header("Authentication", "simple_api_key_for_authentication")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateUser() throws Exception {

//        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);
//
//        mvc.perform(post("/api/users")
//                .header("Authentication", "simple_api_key_for_authentication")
//
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateUser() {
//
//        when(userService.updateUser(anyString(), any(UserDTO.class))).thenReturn(userDTO);
//
//        ResponseEntity<?> userDetailsResponse = userController.updateUser("",new UserUpdateRequest());
//
//        assertNotNull(userDetailsResponse);
    }

    @Test
    void testDeleteUser() throws Exception {

//        when(userRepository.findByUserId(anyString())).thenReturn(userEntity);
//
//        mvc.perform(delete("/api/users/test")
//                .header("Authentication", "simple_api_key_for_authentication")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
    }

}