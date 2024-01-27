package ch.hearc.mbu.repository.link;

import ch.hearc.mbu.repository.note.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<Link, Long> {
    @Query("SELECT l FROM Link l WHERE l.note1.user.apiKey = ?1 OR l.note2.user.apiKey = ?1")
    Iterable<Link> findAllByUser(String apiKey);

    @Query("SELECT l FROM Link l WHERE l.note1 = ?1")
    Iterable<Link> findAllOutgoingLinkOfNote(Note note1);

    @Query("SELECT l FROM Link l WHERE l.note2 = ?1")
    Iterable<Link> findAllIncomingLinkOfNote(Note note2);

    @Query("SELECT l FROM Link l WHERE l.note1 = ?1 OR l.note2 = ?1")
    Iterable<Link> findAllLinkOfNote(Note note);
}
