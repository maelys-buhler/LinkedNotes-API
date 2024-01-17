package ch.hearc.mbu.repository.note;

import ch.hearc.mbu.repository.link.Link;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Note, Long> {}
