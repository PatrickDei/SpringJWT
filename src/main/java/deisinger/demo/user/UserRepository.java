package deisinger.demo.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<StaycationUser, Long> {

    Optional<StaycationUser> findFirstByUsername(String username);
}
