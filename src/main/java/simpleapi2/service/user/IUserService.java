package simpleapi2.service.user;

import simpleapi2.dto.user.UserDTO;
import java.util.ArrayList;

public interface IUserService {
    ArrayList<UserDTO> getUser();
    UserDTO getUser(String usernameOrEmail);
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(String userId, UserDTO userDTO);
    void deleteUser(String userId);
    void validateForUserCreate(UserDTO userDTO);
}
