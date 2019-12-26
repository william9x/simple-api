package simpleapi2.repository.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import simpleapi2.entity.user.UserEntity;

import java.util.ArrayList;

@Repository
public interface IUserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
    UserEntity findByUserId(String userId);
    ArrayList<UserEntity> findAllByUsernameNotNull();
}
