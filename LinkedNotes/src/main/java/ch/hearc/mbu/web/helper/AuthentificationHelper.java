package ch.hearc.mbu.web.helper;

import ch.hearc.mbu.repository.user.User;
import ch.hearc.mbu.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthentificationHelper {
    @Autowired
    private UserService userService;
    public User getUserFromApiKey(String apiKey) {

        return null;
    }
}
