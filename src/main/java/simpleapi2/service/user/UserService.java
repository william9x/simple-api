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

    @Override
    public ArrayList<UserDTO> getUser() {

        ArrayList<UserEntity> userEntities = userRepository.findAllByUsernameNotNull();

        if (userEntities.isEmpty()) return null;
        else {
            ArrayList<UserDTO> userDTOS = new ArrayList<>();

            for (UserEntity userEntity : userEntities) {
                UserDTO userDTO = new UserDTO();
                BeanUtils.copyProperties(userEntity, userDTO);
                userDTOS.add(userDTO);
            }

            return userDTOS;
        }
    }

    @Override
    public UserDTO getUser(String usernameOrEmail) {

        UserEntity userEntity;

        userEntity = userRepository.findByUsername(usernameOrEmail);
        if (null == userEntity) userEntity = userRepository.findByEmail(usernameOrEmail);

        if (null == userEntity) return null;
        else {
            UserDTO returnValue = new UserDTO();
            BeanUtils.copyProperties(userEntity, returnValue);

            return returnValue;
        }
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        if (isUsernameExist(userDTO.getUsername()) || isEmailExist(userDTO.getEmail())) {
            return null;
        } else {

            UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(userDTO, userEntity);

            String UUID = Base64.getUrlEncoder().withoutPadding().encodeToString(userEntity.getUsername().getBytes());

            userEntity.setUserId(UUID);

            UserDTO returnValue = new UserDTO();
            BeanUtils.copyProperties(userRepository.save(userEntity), returnValue);

            return returnValue;
        }
    }

    @Override
    public UserDTO updateUser(String userId, UserDTO userDTO) {

        String updateEmail = userDTO.getEmail();
        String updateAddress = userDTO.getAddress();

        UserEntity userEntity = userRepository.findByUserId(userId);
        if (null == userEntity) return null;
        else {
            if (null != updateEmail &&
                    updateEmail.trim().length() > 0 &&
                    !isEmailExist(updateEmail))
            {
                userEntity.setEmail(updateEmail);
            }

            if (null != updateEmail) {
                userEntity.setAddress(updateAddress);
            }

            UserDTO returnValue = new UserDTO();
            BeanUtils.copyProperties(userRepository.save(userEntity), returnValue);

            return returnValue;
        }
    }

    @Override
    public boolean deleteUser(String userId) {

        UserEntity userEntity = userRepository.findByUserId(userId);
        if (null == userEntity) return false;
        else {
            userRepository.delete(userEntity);
            return true;
        }
    }

    private boolean isUsernameExist(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }
}
