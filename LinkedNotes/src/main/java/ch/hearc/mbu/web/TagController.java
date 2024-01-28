package ch.hearc.mbu.web;

import ch.hearc.mbu.repository.note.Note;
import ch.hearc.mbu.repository.tag.Tag;
import ch.hearc.mbu.repository.user.User;
import ch.hearc.mbu.service.tag.TagService;
import ch.hearc.mbu.service.user.UserService;
import ch.hearc.mbu.web.helper.AuthentificationHelper;
import org.apache.commons.lang3.stream.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/{api_key}/tags")
public class TagController {
    @Autowired
    TagService tagService;
    @Autowired
    AuthentificationHelper authentificationHelper;
    @GetMapping(value = "")
    public ResponseEntity<Iterable<Tag>> getTags(@PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Iterable<Tag> tags = tagService.getTags();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(tags);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable long id, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Tag tag = tagService.getTag(id);
        if(tag == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(tag);
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<String> addTag(@RequestBody Tag tag, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (tag.getName() == null) {
            return ResponseEntity.badRequest().body("Name is null");
        }
        try {
            tagService.addTag(tag);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Tag already exists");
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"id\": " + tag.getId() + "}");
    }

    @GetMapping(value = "/{id}/notes")
    public ResponseEntity<Iterable<Note>> getNotesOfTag(@PathVariable long id, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Tag tag = tagService.getTag(id);
        if(tag == null)
        {
            return ResponseEntity.notFound().build();
        }
        Iterable<Note> notes = tag.getNotes();
        Iterable<Note> notesOfUser = StreamSupport.stream(notes.spliterator(), false).filter(note -> note.getUser().equals(user)).toList();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(notesOfUser);
    }
}
