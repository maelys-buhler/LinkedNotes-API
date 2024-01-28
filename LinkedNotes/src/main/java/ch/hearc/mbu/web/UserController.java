package ch.hearc.mbu.web;

import ch.hearc.mbu.repository.user.User;
import ch.hearc.mbu.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        if (user.getUsername() == null) {
            return ResponseEntity.badRequest().body("Username is null");
        }
        String apiKey = user.generateApiKey();
        try {
            userService.addUser(user);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("User already exists");
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("api_key: " + apiKey);
    }
}
