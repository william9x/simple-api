package simpleapi2.controller.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import simpleapi2.dto.user.UserDTO;
import simpleapi2.entity.user.UserEntity;
import simpleapi2.io.request.UserSignUpRequest;
import simpleapi2.io.request.UserUpdateRequest;
import simpleapi2.io.response.OperationStatus;
import simpleapi2.io.response.UserFullDetailsResponse;
import simpleapi2.io.response.UserDetailsResponse;
import simpleapi2.service.user.IUserService;

import java.util.ArrayList;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping()
    public ArrayList<UserFullDetailsResponse> getUser() {

        ArrayList<UserDTO> userDTOS = userService.getUser();
        ArrayList<UserFullDetailsResponse> userFullDetailsResponses = new ArrayList<>();

        for (UserDTO userDTO : userDTOS) {
            UserFullDetailsResponse userFullDetailsResponse = new UserFullDetailsResponse();
            BeanUtils.copyProperties(userDTO, userFullDetailsResponse);
            userFullDetailsResponses.add(userFullDetailsResponse);
        }

        return userFullDetailsResponses;
    }

    @GetMapping(path = "/{usernameOrEmail}")
    public UserFullDetailsResponse getUser(@PathVariable String usernameOrEmail) {

        UserDTO userDTO = userService.getUser(usernameOrEmail);
        UserFullDetailsResponse returnValue = new UserFullDetailsResponse();
        BeanUtils.copyProperties(userDTO, returnValue);

        return returnValue;
    }

    @PostMapping
    public UserDetailsResponse createUser(@RequestBody UserSignUpRequest userSignUpRequest) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userSignUpRequest, userDTO);

        UserDetailsResponse returnValue = new UserDetailsResponse();
        BeanUtils.copyProperties(userService.createUser(userDTO), returnValue);

        return returnValue;
    }

    @PutMapping(path = "/{userId}")
    public UserDetailsResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest userUpdateRequest) {

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userUpdateRequest, userDTO);

        UserDetailsResponse returnValue = new UserDetailsResponse();
        BeanUtils.copyProperties(userService.updateUser(userId, userDTO), returnValue);

        return returnValue;
    }

    @DeleteMapping(path = "/{userId}")
    public OperationStatus deleteUser(@PathVariable String userId) {
        OperationStatus deleteOps = new OperationStatus();

        userService.deleteUser(userId);

        deleteOps.setOperationName("Delete Operation");
        deleteOps.setOperationResult("Success");

        return deleteOps;
    }
}
