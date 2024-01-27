package ch.hearc.mbu.service.note;

import ch.hearc.mbu.repository.note.Note;

import java.util.Iterator;
import java.util.Optional;

public interface NoteService {
    public Optional<Note> getNote(long id);
    public Long addNote(Note note);
    public void updateNote(Note note);
    public void deleteNote(long id);
    public Iterable<Note> getNotes();
    public Iterable<Note> getNotesOfUser(String userId);
    public boolean idExists(long id);
}
