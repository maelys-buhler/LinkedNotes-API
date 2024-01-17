package ch.hearc.mbu.service.note;

import ch.hearc.mbu.repository.note.Note;

import java.util.Iterator;

public interface NoteService {
    public Note getNote(long id);
    public Long addNote(Note note);
    public void updateNote(Note note);
    public void deleteNote(long id);
    public Iterable<Note> getNotes();
    public boolean idExists(long id);
}
