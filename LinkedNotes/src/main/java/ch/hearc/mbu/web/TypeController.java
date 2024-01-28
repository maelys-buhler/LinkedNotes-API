package ch.hearc.mbu.web;

import ch.hearc.mbu.repository.type.Type;
import ch.hearc.mbu.repository.user.User;
import ch.hearc.mbu.web.helper.AuthentificationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ch.hearc.mbu.service.type.TypeService;


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
        Type type = typeService.getType(id);
        if(type == null)
        {
            return ResponseEntity.notFound().build();
        }        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(type);
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<Type> addType(@RequestBody Type type, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (type.getName() == null) {
            return ResponseEntity.badRequest().build();
        }
        Type createdType = null;
        try {
            createdType = typeService.addType(type);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(createdType);
    }

}
