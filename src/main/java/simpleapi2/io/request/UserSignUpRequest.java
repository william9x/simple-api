package simpleapi2.io.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserSignUpRequest {

    private String username;
    private String email;
    private String address;
}
