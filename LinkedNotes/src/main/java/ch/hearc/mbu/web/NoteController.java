package ch.hearc.mbu.web;

import ch.hearc.mbu.repository.note.Note;
import ch.hearc.mbu.repository.user.User;
import ch.hearc.mbu.service.note.NoteService;
import ch.hearc.mbu.service.user.UserService;
import ch.hearc.mbu.web.helper.AuthentificationHelper;
import org.aspectj.weaver.ast.Not;
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

    @GetMapping(value = "/")
    public ResponseEntity<Iterable<Note>> getNotesOfUser(@PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Iterable<Note> notes = noteService.getNotesOfUser(api_key);
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
        if(note.isEmpty())
        {
            return ResponseEntity.notFound().build();

        }
        if (!note.get().getUser().getApiKey().equals(api_key)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(note.get());
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
        note.setUser(user);
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
        Note actualNote = noteService.getNote(note.getId()).get();
        if(!actualNote.getUser().getApiKey().equals(api_key))
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            noteService.updateNote(note);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
        //FIXME return note when n to n relationships are implemented
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"id\": " + note.getId() + "}");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteNoteOfUser(@PathVariable long id, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Note note = noteService.getNote(id).orElse(null);
        if (note != null)
        {
            if(!note.getUser().getApiKey().equals(api_key))
            {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            noteService.deleteNote(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }
}
