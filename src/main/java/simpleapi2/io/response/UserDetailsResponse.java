package simpleapi2.io.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsResponse {
    private String username;
    private String email;
    private String address;
}
