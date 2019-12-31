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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private UserDTO userDTO;
    private UserFullDetailsResponse userFullDetailsResponse;
    private UserDetailsResponse userDetailsResponse;

    @Autowired
    private MockMvc mvc;

//    @InjectMocks
//    private UserController userController;

    @MockBean
    private UserService userService;

//    @Mock
//    private IUserRepository userRepository;



    @BeforeEach
    void setUp() {
        //MockitoAnnotations.initMocks(this);

        userDTO = new UserDTO();
        userDTO.setUsername("test DTO");
        userDTO.setEmail("test@test.com");
        userDTO.setAddress("test address");

        userFullDetailsResponse = new UserFullDetailsResponse();
        userDetailsResponse = new UserDetailsResponse();
    }

    @Test
    void testGetAllUser() throws Exception{
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        userDTOS.add(userDTO);

        //when(userService.getUser()).thenReturn(userDTOS);

        given(userService.getUser()).willReturn(userDTOS);
//        userFullDetailsResponse.setUsername(userDTO.getUsername());
//
//        ResponseEntity<?> getUsers = userController.getUser();
//
//        assertNotNull(getUsers);
//        assertEquals(1, userDTOS.size());

        mvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        mvc.perform(get("/api/users")
                .header("Authentication", "simple_api_key_for_authentication")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//    @Test
//    void testGetUser() {
////
//        when(userService.getUser(anyString())).thenReturn(userDTO);
//
//        ResponseEntity<?> userFullDetailsResponse = userController.getUser(anyString());
//
//        assertNotNull(userFullDetailsResponse);
//    }
//
//    @Test
//    void testCreateUser() {
//
//        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);
//
//        ResponseEntity<?> userDetailsResponse = userController.createUser(new UserSignUpRequest());
//
//        assertNotNull(userDetailsResponse);
//    }
//
//    @Test
//    void testUpdateUser() {
//
//        when(userService.updateUser(anyString(), any(UserDTO.class))).thenReturn(userDTO);
//
//        ResponseEntity<?> userDetailsResponse = userController.updateUser("",new UserUpdateRequest());
//
//        assertNotNull(userDetailsResponse);
//    }
//
//    @Test
//    void testDeleteUser() {
//
//        when(userRepository.findByUserId(anyString())).thenReturn(new UserEntity());
//
//        ResponseEntity<?> operationStatus = userController.deleteUser("");
//
//        assertNotNull(operationStatus);
//    }

}