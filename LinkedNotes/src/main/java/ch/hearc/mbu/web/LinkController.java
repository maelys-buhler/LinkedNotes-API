package ch.hearc.mbu.web;

import ch.hearc.mbu.repository.link.Link;
import ch.hearc.mbu.repository.user.User;
import ch.hearc.mbu.service.link.LinkService;
import ch.hearc.mbu.service.note.NoteService;
import ch.hearc.mbu.web.helper.AuthentificationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/{api_key}/links")
public class LinkController {
    //TODO only let the user access his own links
    String apiKey;
    @Autowired
    LinkService linkService;
    @Autowired
    NoteService noteService;
    @Autowired
    AuthentificationHelper authentificationHelper;

    @GetMapping(value = "/")
    public ResponseEntity<Iterable<Link>> getLinks(@PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Iterable<Link> links = linkService.getLinks();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(links);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Link> getLink(@PathVariable long id, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Link> link = linkService.getLink(id);
        if(link.isPresent())
        {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(link.get());
        }
        return ResponseEntity.notFound().build();
    }

    //TODO
//    @GetMapping(value = "/note/{id}")
//    public ResponseEntity<Iterable<Link>> getLinksOfNoteId(@PathVariable long id) {
//        Iterable<Link> links = linkService.getLinksOfNoteId(id);
//        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(links);
//    }

    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<String> addLink(@RequestBody Link link, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (link.getType() == null || link.getName() == null || link.getNote1()== null || link.getNote2() == null) {
            return ResponseEntity.badRequest().body("Title, name or one of the note is null");
        }
        Long id = linkService.addLink(link);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"id\": " + id + "}");
    }

    @PutMapping(value = "/", consumes = "application/json")
    public ResponseEntity<String> updateLink(@RequestBody Link link, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!linkService.idExists(link.getId()) || link.getType() == null || link.getName() == null || link.getNote1()== null || link.getNote2() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            linkService.updateLink(link);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Link does not exist");
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"id\": " + link.getId() + "}");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteLink(@PathVariable long id, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(linkService.idExists(id)){
            linkService.deleteLink(id);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"id\": " + id + "}");
        }
        return ResponseEntity.badRequest().body("Link does not exist");
    }
}
