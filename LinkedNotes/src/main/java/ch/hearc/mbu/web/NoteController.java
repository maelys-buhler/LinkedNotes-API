package ch.hearc.mbu.web;

import ch.hearc.mbu.repository.link.Link;
import ch.hearc.mbu.repository.note.Note;
import ch.hearc.mbu.repository.user.User;
import ch.hearc.mbu.service.link.LinkService;
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
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/{api_key}/notes")
public class NoteController {
    @Autowired
    NoteService noteService;
    @Autowired
    UserService userService;
    @Autowired
    LinkService linkService;
    @Autowired
    AuthentificationHelper authentificationHelper;

    @GetMapping(value = "")
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
        Note note = noteService.getNote(id);
        if(note == null)
        {
            return ResponseEntity.notFound().build();

        }
        if (!note.getUser().getApiKey().equals(api_key)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(note);
    }

    @GetMapping(value = "/{id}/linked/")
    public ResponseEntity<Iterable<Note>> getLinkedNotes(@PathVariable long id, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Note note = noteService.getNote(id);
        if(note == null)
        {
            return ResponseEntity.notFound().build();

        }
        if (!note.getUser().getApiKey().equals(api_key)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Iterable<Link> linksOfNote = linkService.getLinksOfNote(note);
        Iterable<Note> linkedNotes = StreamSupport.stream(linksOfNote.spliterator(), false).map(link -> {
            if (link.getNote1().equals(note)) {
                return link.getNote2();
            } else {
                return link.getNote1();
            }
        }).toList();

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(linkedNotes);
    }
    @GetMapping(value = "/{id}/outgoinglinked")
    public ResponseEntity<Iterable<Note>> getOutgoingLinkedNotes(@PathVariable long id, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Note note = noteService.getNote(id);
        if(note == null)
        {
            return ResponseEntity.notFound().build();

        }
        if (!note.getUser().getApiKey().equals(api_key)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Iterable<Link> linksOfNote = linkService.getOutgoingLinksOfNote(note);
        Iterable<Note> linkedNotes = StreamSupport.stream(linksOfNote.spliterator(), false).map(Link::getNote2).toList();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(linkedNotes);
    }

    @GetMapping(value = "/{id}/incominglinked")
    public ResponseEntity<Iterable<Note>> getIncomingLinkedNotes(@PathVariable long id, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Note note = noteService.getNote(id);
        if(note == null)
        {
            return ResponseEntity.notFound().build();

        }
        if (!note.getUser().getApiKey().equals(api_key)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Iterable<Link> linksOfNote = linkService.getIncomingLinksOfNote(note);
        Iterable<Note> linkedNotes = StreamSupport.stream(linksOfNote.spliterator(), false).map(Link::getNote1).toList();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(linkedNotes);
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<Note> addNoteOfUser(@RequestBody Note note, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (note.getTitle() == null || note.getContent() == null) {
            return ResponseEntity.badRequest().build();
        }
        note.setUser(user);
        Note createdNote = noteService.addNote(note);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(createdNote);
    }

    @PutMapping(value = "", consumes = "application/json")
    public ResponseEntity<Note> updateNote(@RequestBody Note note, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Note actualNote = noteService.getNote(note.getId());
        if (!(noteService == null) || note.getTitle() == null || note.getContent() == null) {
            return ResponseEntity.badRequest().body(null);
        }
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
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(note);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteNoteOfUser(@PathVariable long id, @PathVariable String api_key) {
        User user = authentificationHelper.getUserFromApiKey(api_key);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Note note = noteService.getNote(id);
        if (note == null)
        {
            return ResponseEntity.notFound().build();
        }
        if(!note.getUser().getApiKey().equals(api_key))
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        noteService.deleteNote(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
