package ch.hearc.mbu.web;

import ch.hearc.mbu.repository.link.Link;
import ch.hearc.mbu.repository.note.Note;
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
        Iterable<Link> links = linkService.getLinksOfUser(api_key);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(links);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Link> getLink(@PathVariable long id, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Link> optionalLink = linkService.getLink(id);
        if(!optionalLink.isPresent())
        {
            return ResponseEntity.notFound().build();
        }
        Link link = optionalLink.get();
        Note note1 = noteService.getNote(link.getNote1().getId()).orElse(null);
        Note note2 = noteService.getNote(link.getNote2().getId()).orElse(null);
        if(!note1.getUser().equals(user) || !note2.getUser().equals(user))
        {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(link);
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
        Note note1 = noteService.getNote(link.getNote1().getId()).orElse(null);
        Note note2 = noteService.getNote(link.getNote2().getId()).orElse(null);
        if(note1 == null || note2 == null)
        {
            return ResponseEntity.badRequest().body("One of the notes does not exist");
        }
        if(!note1.getUser().equals(user) || !note2.getUser().equals(user))
        {
            return ResponseEntity.badRequest().body("One of the notes does not belong to the user");
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
        if (!linkService.idExists(link.getId()) || link.getType() == null || link.getName() == null) {
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
        //TODO test and fix
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
