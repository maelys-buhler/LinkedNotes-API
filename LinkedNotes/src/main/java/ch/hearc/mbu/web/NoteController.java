package ch.hearc.mbu.web;

import ch.hearc.mbu.repository.note.Note;
import ch.hearc.mbu.repository.user.User;
import ch.hearc.mbu.service.note.NoteService;
import ch.hearc.mbu.service.user.UserService;
import ch.hearc.mbu.web.helper.AuthentificationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/{api_key}/notes")
public class NoteController {
    @Autowired
    NoteService noteService;
    @Autowired
    UserService userService;
    @Autowired
    AuthentificationHelper authentificationHelper;
    //TODO only let the user access his own notes
    @GetMapping(value = "/")
    public ResponseEntity<Iterable<Note>> getNotesOfUser(@PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Iterable<Note> notes = noteService.getNotes();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(notes);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Note> getNote(@PathVariable long id, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Note> note = noteService.getNote(id);
        if(note.isPresent())
        {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(note.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<String> addNoteOfUser(@RequestBody Note note, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (note.getTitle() == null || note.getContent() == null) {
            return ResponseEntity.badRequest().body("Title or content is null");
        }
        Long id = noteService.addNote(note);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"id\": " + id + "}");
    }

    @PutMapping(value = "/", consumes = "application/json")
    public ResponseEntity<String> updateNote(@RequestBody Note note, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!noteService.idExists(note.getId()) || note.getTitle() == null || note.getContent() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            noteService.updateNote(note);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(null);
        }
        //FIXME return note when n to n relationships are implemented
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"id\": " + note.getId() + "}");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteNoteOfUser(@PathVariable long id, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(noteService.idExists(id)){
            noteService.deleteNote(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }
}
