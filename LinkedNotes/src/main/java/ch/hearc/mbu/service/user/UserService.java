package ch.hearc.mbu.service.user;

import ch.hearc.mbu.repository.user.User;

import java.util.Optional;

public interface UserService {
    public Optional<User> getUser(long id);
    public Optional<User> getUserByApiKey(String apiKey);
    public User addUser(User user);
    public void updateUser(User user);
    public void deleteUser(long id);
    public Iterable<User> getUsers();
    public boolean idExists(long id);
}
