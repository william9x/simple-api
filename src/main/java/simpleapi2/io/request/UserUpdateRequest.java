package simpleapi2.io.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserUpdateRequest {
    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Size(max = 100, message = "Email can only have maximum 100 characters")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotNull(message = "Address is required")
    @NotBlank(message = "Address is required")
    @Size(max = 100, message = "Address can only have maximum 100 characters")
    private String address;
}
