package simpleapi2.service.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simpleapi2.dto.user.UserDTO;
import simpleapi2.entity.user.UserEntity;
import simpleapi2.repository.user.IUserRepository;

import java.util.ArrayList;
import java.util.Base64;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    private static String testStatic = "test";

    @Override
    public ArrayList<UserDTO> getUser() {

        ArrayList<UserEntity> userEntities = userRepository.findAllByUsernameNotNull();
        ArrayList<UserDTO> userDTOS = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(userEntity, userDTO);
            userDTOS.add(userDTO);
        }

        return userDTOS;
    }

    @Override
    public UserDTO getUser(String usernameOrEmail) {

        UserEntity userEntity = findUser(usernameOrEmail);
        UserDTO returnValue = new UserDTO();
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        validateForUserCreate(userDTO);

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDTO, userEntity);

        String UUID = Base64.getUrlEncoder().withoutPadding().encodeToString(userEntity.getUsername().getBytes());

        userEntity.setUserId(UUID);

        UserDTO returnValue = new UserDTO();
        BeanUtils.copyProperties(userRepository.save(userEntity), returnValue);

        return returnValue;
    }

    @Override
    public UserDTO updateUser(String userId, UserDTO userDTO) {

        String updateEmail = userDTO.getEmail();
        String updateAddress = userDTO.getAddress();

        UserEntity userEntity = userRepository.findByUserId(userId);
        if (null == userEntity) throw new RuntimeException("User not found");

        if (false == isEmailFieldNull(updateEmail)) {
            if (isEmailExist(updateEmail)) throw new RuntimeException("Update user failed");
            userEntity.setEmail(updateEmail);
        }
        if (false == isAddressFieldNull(updateAddress)) userEntity.setAddress(updateAddress);

        UserDTO returnValue = new UserDTO();
        BeanUtils.copyProperties(userRepository.save(userEntity), returnValue);

        return returnValue;
    }

    @Override
    public void deleteUser(String userId) {

        UserEntity userEntity = userRepository.findByUserId(userId);
        if (null == userEntity) throw new RuntimeException("User not found");

        userRepository.delete(userEntity);
    }

    private UserEntity findUser(String usernameOrEmail) {

        UserEntity user;

        if (isUsernameExist(usernameOrEmail)) user = userRepository.findByUsername(usernameOrEmail);
        else if (isEmailExist(usernameOrEmail)) user = userRepository.findByEmail(usernameOrEmail);
        else throw new RuntimeException("User not found");

        return user;
    }

    private void validateForUserCreate(UserDTO userDTO) {
        checkIfRequiredFieldsNull(userDTO);
        checkIfUniqueFieldsExist(userDTO);
    }

    private void checkIfRequiredFieldsNull(UserDTO userDTO) {
        if (isUsernameFieldNull(userDTO.getUsername())) throw new RuntimeException("Missing username");
        if (isAddressFieldNull(userDTO.getAddress())) throw new RuntimeException("Missing address");
        if (isEmailFieldNull(userDTO.getEmail())) throw new RuntimeException("Missing email");
    }

    private void checkIfUniqueFieldsExist(UserDTO userDTO) {
        if (isUsernameExist(userDTO.getUsername()) || isEmailExist(userDTO.getEmail()))
            throw new RuntimeException("Create user failed");
    }

    private boolean isUsernameFieldNull(String username) {
        if (null == username) return true;
        else return false;
    }

    private boolean isEmailFieldNull(String email) {
        if (null == email) return true;
        else return false;
    }

    private boolean isAddressFieldNull(String address) {
        if (null == address) return true;
        else return false;
    }

    private boolean isUsernameExist(String username) { return userRepository.existsByUsername(username);  }

    private boolean isEmailExist(String email) {
       return userRepository.existsByEmail(email);
    }
}
