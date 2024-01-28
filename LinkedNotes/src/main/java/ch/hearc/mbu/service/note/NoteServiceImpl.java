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
    public Note getNote(long id) {
        return noteRepository.findById(id).orElse(null);
    }

    @Override
    public Note addNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note updateNote(Note note) {
        Note actualNote = noteRepository.findById(note.getId()).orElse(null);
        if(actualNote != null)
        {
            actualNote.setTitle(note.getTitle());
            actualNote.setContent(note.getContent());
            noteRepository.save(actualNote);
            return actualNote;
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
    public Iterable<Note> getNotesOfUser(String apiKey) {
        return noteRepository.findAllByUser(apiKey);
    }

    @Override
    public boolean idExists(long id) {
        return noteRepository.existsById(id);
    }
}
