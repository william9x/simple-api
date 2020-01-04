package simpleapi2.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simpleapi2.dto.user.UserDTO;
import simpleapi2.io.request.UserSignUpRequest;
import simpleapi2.io.request.UserUpdateRequest;
import simpleapi2.io.response.*;
import simpleapi2.service.user.IUserService;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("api/users")
@Api(value = "User APIs")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "Get all user", response = OperationStatus.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping()
    public ResponseEntity<?> getUser() {
        OperationStatus operationStatus;

        ArrayList<UserDTO> userDTOS = userService.getUser();

        if (null == userDTOS) {
            operationStatus = new OperationStatus(HttpStatus.NOT_FOUND.value(), ErrorResponse.NO_RECORD_FOUND.getErrorMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(operationStatus);

        } else {
            ArrayList<UserFullDetailsResponse> userFullDetailsResponses = new ArrayList<>();

            for (UserDTO userDTO : userDTOS) {
                UserFullDetailsResponse userFullDetailsResponse = new UserFullDetailsResponse();
                BeanUtils.copyProperties(userDTO, userFullDetailsResponse);
                userFullDetailsResponses.add(userFullDetailsResponse);
            }

            operationStatus = new OperationStatus(HttpStatus.OK.value(), SuccessResponse.FOUND_RECORD.getSuccessResponse(), userFullDetailsResponses);
            return ResponseEntity.status(HttpStatus.OK).body(operationStatus);
        }
    }

    @ApiOperation(value = "Get a user by userId", response = OperationStatus.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(path = "/{usernameOrEmail}")
    public ResponseEntity<?> getUser(@PathVariable String usernameOrEmail) {
        OperationStatus operationStatus;

        UserDTO userDTO = userService.getUser(usernameOrEmail);

        if (null == userDTO) {
            operationStatus = new OperationStatus(HttpStatus.NOT_FOUND.value(),
                    ErrorResponse.NO_RECORD_FOUND.getErrorMessage(), null);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(operationStatus);
        } else {

            UserFullDetailsResponse returnValue = new UserFullDetailsResponse();
            BeanUtils.copyProperties(userDTO, returnValue);

            operationStatus = new OperationStatus(HttpStatus.OK.value(),
                    SuccessResponse.FOUND_RECORD.getSuccessResponse(), returnValue);

            return ResponseEntity.status(HttpStatus.OK).body(operationStatus);
        }

    }

    @ApiOperation(value = "Create a user", response = OperationStatus.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserSignUpRequest userSignUpRequest) {
        OperationStatus operationStatus;

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userSignUpRequest, userDTO);

        UserDTO createdUser = userService.createUser(userDTO);
        if (null == createdUser) {
            operationStatus = new OperationStatus(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ErrorResponse.COULD_NOT_CREATE_RECORD.getErrorMessage(), null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(operationStatus);

        } else {
            UserDetailsResponse returnValue = new UserDetailsResponse();
            BeanUtils.copyProperties(createdUser, returnValue);

            operationStatus = new OperationStatus(HttpStatus.CREATED.value(),
                    SuccessResponse.CREATED_RECORD.getSuccessResponse(), returnValue);

            return ResponseEntity.status(HttpStatus.CREATED).body(operationStatus);
        }
    }

    @ApiOperation(value = "Update a user by userId", response = OperationStatus.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Updated"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PutMapping(path = "/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        OperationStatus operationStatus;

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userUpdateRequest, userDTO);

        UserDTO updatedUser = userService.updateUser(userId, userDTO);

        if (null == updatedUser) {
            operationStatus = new OperationStatus(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ErrorResponse.COULD_NOT_UPDATE_RECORD.getErrorMessage(), null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(operationStatus);

        } else {
            UserDetailsResponse returnValue = new UserDetailsResponse();
            BeanUtils.copyProperties(updatedUser, returnValue);

            operationStatus = new OperationStatus(HttpStatus.OK.value(),
                    SuccessResponse.UPDATED_RECORD.getSuccessResponse(), returnValue);

            return ResponseEntity.status(HttpStatus.OK).body(operationStatus);
        }
    }

    @ApiOperation(value = "Delete user by userId", response = OperationStatus.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        OperationStatus operationStatus;

        boolean deletedUser = userService.deleteUser(userId);

        if (false == deletedUser) {
            operationStatus = new OperationStatus(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ErrorResponse.COULD_NOT_DELETE_RECORD.getErrorMessage(), null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(operationStatus);

        } else {
            operationStatus = new OperationStatus(HttpStatus.OK.value(),
                    SuccessResponse.DELETED_RECORD.getSuccessResponse(), null);

            return ResponseEntity.status(HttpStatus.OK).body(operationStatus);
        }
    }
}