package simpleapi2.service.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simpleapi2.dto.user.UserDTO;
import simpleapi2.entity.user.UserEntity;
import simpleapi2.middleware.utilities.string.IStringUtilities;
import simpleapi2.repository.user.IUserRepository;

import java.util.ArrayList;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IStringUtilities utilities;


    @Override
    public ArrayList<UserDTO> getUser() {

        ArrayList<UserEntity> userEntities = userRepository.findAllByUsernameNotNull();
        ArrayList<UserDTO> userDTOS = new ArrayList<>();

        for (UserEntity source : userEntities) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(source, userDTO);
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

        userEntity.setUserId(createUserId());

        UserDTO returnValue = new UserDTO();
        BeanUtils.copyProperties(userRepository.save(userEntity), returnValue);

        return returnValue;
    }

    @Override
    public UserDTO updateUser(String userId, UserDTO userDTO) {

        String updateUsername = userDTO.getUsername();
        String updateEmail = userDTO.getEmail();
        String updateAddress = userDTO.getAddress();

        UserEntity userEntity = userRepository.findByUserId(userId);

        if (null == userEntity) throw new RuntimeException("User not found");

        if (false == isUsernameFieldNull(updateUsername)) {
            if (isUsernameExist(updateUsername)) throw new RuntimeException("Update user failed");
            userEntity.setUsername(updateUsername);
        }
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

    private String createUserId() {
        int UserIdLength = 30;
        String userId;

        do userId = utilities.generateRandomString(UserIdLength);
        while (isUserIdExist(userId));

        return userId;
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

    private boolean isUserIdExist(String userId) {
        if (null != userRepository.findByUserId(userId)) return true;
        else return false;
    }

    private boolean isUsernameExist(String username) {
        if (null != userRepository.findByUsername(username)) return true;
        else return false;
    }

    private boolean isEmailExist(String email) {
        if (null != userRepository.findByEmail(email)) return true;
        else return false;
    }
}
