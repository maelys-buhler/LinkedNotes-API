package ch.hearc.mbu.service.note;

import ch.hearc.mbu.repository.note.Note;

public interface NoteService {
    public Note getNote(long id);
    public Note addNote(Note note);
    public Note updateNote(Note note);
    public void deleteNote(long id);
    public Iterable<Note> getNotes();
    public Iterable<Note> getNotesOfUser(String userId);
    public boolean idExists(long id);
}
