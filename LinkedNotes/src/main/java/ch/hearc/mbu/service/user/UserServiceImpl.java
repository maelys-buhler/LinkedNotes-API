package ch.hearc.mbu.service.user;

import ch.hearc.mbu.repository.user.User;
import ch.hearc.mbu.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public Optional<User> getUser(long id) {
        return Optional.ofNullable(userRepository.findById(id).orElse(null));
    }

    @Override
    public Optional<User> getUserByApiKey(String apiKey) {
        return userRepository.findUserByApiKey(apiKey).stream().findFirst();
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    //TODO check if useful or not, if not delete
    @Override
    public void updateUser(User user) {
        if(userRepository.existsById(user.getId()))
        {
            userRepository.save(user);
        }
        else
        {
            throw new IllegalArgumentException("User does not exist");
        }
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean idExists(long id) {
        return userRepository.existsById(id);
    }
}
