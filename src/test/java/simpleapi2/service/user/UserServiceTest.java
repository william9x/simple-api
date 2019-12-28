package simpleapi2.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import simpleapi2.dto.user.UserDTO;
import simpleapi2.entity.user.UserEntity;
import simpleapi2.middleware.utilities.string.IStringUtilities;
import simpleapi2.repository.user.IUserRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private UserEntity userEntity;
    private UserDTO userDTO;

    @InjectMocks
    UserService userService;

    @Mock
    IUserRepository userRepository;

    @Mock
    IStringUtilities stringUtilities;


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
    final void testGetAllUser() {

        ArrayList<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(userEntity);

        when(userRepository.findAllByUsernameNotNull()).thenReturn(userEntities);

        ArrayList<UserDTO> userDTOS = userService.getUser();

        assertNotNull(userDTOS);
        assertEquals(1, userDTOS.size());
        assertEquals(userDTOS.get(0).getUsername(), userEntity.getUsername());
    }

    @Test
    final void testGetAllUser_userNotFound() {

        when(userRepository.findAllByUsernameNotNull()).thenReturn(null);

        assertThrows(RuntimeException.class, () -> userService.getUser());
    }

    @Test
    final void testGetUser() {

        when(userRepository.findByUsername(anyString())).thenReturn(userEntity);

        UserDTO findUser = userService.getUser(anyString());

        assertNotNull(findUser);
        assertEquals(userEntity.getUsername(), findUser.getUsername());

    }

    @Test
    final void testGetUser_userNotFound() {

        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> userService.getUser(anyString()));
    }

    @Test
    final void testCreateUser() {

        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(stringUtilities.generateRandomString(anyInt())).thenReturn("testUID");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDTO createUser = userService.createUser(userDTO);

        assertNotNull(createUser);
        assertEquals(userEntity.getUsername(), createUser.getUsername());
    }

    @Test
    final void testCreateUser_checkIfRequiredFieldsNull() {
        assertThrows(RuntimeException.class, () -> userService.createUser(new UserDTO()));
    }

    @Test
    final void testCreateUser_checkIfUniqueFieldsExist_username() {
        when(userRepository.findByUsername(anyString())).thenReturn(userEntity);

        userDTO.setUsername(userEntity.getUsername());
        assertThrows(RuntimeException.class, () -> userService.createUser(userDTO));
    }

    @Test
    final void testCreateUser_checkIfUniqueFieldsExist_email() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        userDTO.setEmail(userEntity.getEmail());
        assertThrows(RuntimeException.class, () -> userService.createUser(userDTO));
    }

    @Test
    final void testUpdateUser() {

        when(userRepository.findByUserId(anyString())).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDTO updateUser = userService.updateUser(anyString(), new UserDTO());

        assertNotNull(updateUser);
        assertEquals(userEntity.getUsername(), updateUser.getUsername());
    }

    @Test
    final void testUpdateUser_userNotFound() {

        when(userRepository.findByUserId(anyString())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> userService.updateUser(anyString(), new UserDTO()));
    }

    @Test
    final void testDeleteUser() {

        when(userRepository.findByUserId(anyString())).thenReturn(userEntity);

        assertDoesNotThrow(() -> userService.deleteUser(anyString()));
    }

    @Test
    final void testDeleteUser_userNotFound() {

        when(userRepository.findByUserId(anyString())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> userService.deleteUser(anyString()));
    }
}