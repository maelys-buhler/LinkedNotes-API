package ch.hearc.mbu.repository.note;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface NoteRepository extends CrudRepository<Note, Long> {
    @Query("SELECT n FROM Note n WHERE n.user.apiKey = ?1")
    Collection<Note> findAllByUser(String apiKey);
}
