package simpleapi2.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private long id;
    private String userId;
    private String username;
    private String email;
    private String address;
}
