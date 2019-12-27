package simpleapi2.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import simpleapi2.dto.user.UserDTO;
import simpleapi2.entity.user.UserEntity;
import simpleapi2.repository.user.IUserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    IUserRepository userRepository;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    final void testGetUser() {
        UserEntity user = new UserEntity();
        user.setAddress("test address");
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        UserDTO userDTO = userService.getUser("test");

        assertNotNull(userDTO);
        assertEquals("test address", userDTO.getAddress());
    }

    @Test
    final void testFindUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(RuntimeException.class,
                () -> userService.getUser("test")
        );
    }

    @Test
    final void createUser(){
        UserEntity user = new UserEntity();
        user.setUsername("test name");
        user.setEmail("test mail");
        user.setAddress("test address");
        user.setUserId("test address");
    }

//    @Test
//    final void testValidateForUserCreate()
//
//    @Test
//    final void testCheckIfRequiredFieldsNull()
//
//    @Test
//    final void testCheckIfUniqueFieldsExist()

//    @Test
//    final void testIsUsernameFieldNull(){
//        String username = null;
//
//
//    }
//
//    @Test
//    final void testIsEmailFieldNull(){
//
//    }
//    @Test
//    final void testIsAddressFieldNull(){
//
//    }
//    @Test
//    final void testIsUserIdExist(){
//
//    }
//    @Test
//    final void testIsUsernameExist(){
//
//    }
//    @Test
//    final void testIsEmailExist(){
//
//    }
//






}