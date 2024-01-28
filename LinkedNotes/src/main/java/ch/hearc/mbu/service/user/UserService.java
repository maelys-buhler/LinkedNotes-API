package ch.hearc.mbu.service.user;

import ch.hearc.mbu.repository.user.User;

public interface UserService {
    public User getUser(long id);
    public User getUserByApiKey(String apiKey);
    public User addUser(User user);
    public void updateUser(User user);
    public void deleteUser(long id);
    public Iterable<User> getUsers();
    public boolean idExists(long id);
}
