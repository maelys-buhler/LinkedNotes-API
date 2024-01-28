package ch.hearc.mbu.web;

import ch.hearc.mbu.repository.type.Type;
import ch.hearc.mbu.repository.user.User;
import ch.hearc.mbu.web.helper.AuthentificationHelper;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ch.hearc.mbu.service.type.TypeService;

import java.util.Optional;

@RestController
@RequestMapping(value = "/{api_key}/types")
public class TypeController {

    @Autowired
    TypeService typeService;

    @Autowired
    AuthentificationHelper authentificationHelper;

    @GetMapping(value = "")
    public ResponseEntity<Iterable<Type>> getTypes(@PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            System.out.println("User is null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Iterable<Type> types = typeService.getTypes();
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(types);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Type> getType(@PathVariable long id, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Type> type = typeService.getType(id);
        if(type.isPresent())
        {
            return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(type.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<String> addType(@RequestBody Type type, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (type.getName() == null) {
            return ResponseEntity.badRequest().body("Name is null");
        }
        try {
            typeService.addType(type);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Type already exists");
        }
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body("{\"id\": " + type.getId() + "}");
    }

}
