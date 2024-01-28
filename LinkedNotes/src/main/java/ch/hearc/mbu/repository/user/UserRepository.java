package ch.hearc.mbu.repository.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.apiKey LIKE ?1")
    Collection<User> findUserByApiKey(String apiKey);
}
