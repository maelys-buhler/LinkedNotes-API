package ch.hearc.mbu.service.note;

import ch.hearc.mbu.repository.note.Note;
import ch.hearc.mbu.repository.note.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService{

    @Autowired
    private NoteRepository noteRepository;
    @Override
    public Optional<Note> getNote(long id) {
        return Optional.ofNullable(noteRepository.findById(id).orElse(null));
    }

    @Override
    public Long addNote(Note note) {
        return noteRepository.save(note).getId();
    }

    //TODO check if useful or not, if not delete
    @Override
    public void updateNote(Note note) {
        if(noteRepository.existsById(note.getId()))
        {
            noteRepository.save(note);
        }
        else
        {
            throw new IllegalArgumentException("Note does not exist");
        }
    }

    @Override
    public void deleteNote(long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public Iterable<Note> getNotes() {
        return noteRepository.findAll();
    }

    @Override
    public boolean idExists(long id) {
        return noteRepository.existsById(id);
    }
}
