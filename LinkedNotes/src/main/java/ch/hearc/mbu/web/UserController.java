package ch.hearc.mbu.web;

import ch.hearc.mbu.repository.user.User;
import ch.hearc.mbu.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    UserService userService;

    //TODO check if useful or not, if not delete
//    @GetMapping(value = "/")
//    public ResponseEntity<UserService> getUsers() {
//        Iterable<User> users = userService.getUsers();
//        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService);
//    }

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

//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<String> deleteUser(@PathVariable long id) {
//        if(userService.idExists(id))
//        {
//            userService.deleteUser(id);
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//        }
//        return ResponseEntity.notFound().build();
//    }
}
