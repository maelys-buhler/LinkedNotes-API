package ch.hearc.mbu.repository.tag;

import ch.hearc.mbu.repository.note.Note;
import jakarta.persistence.*;

import java.util.Set;
import java.util.stream.Stream;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    private Set<Note> notes;

    //GETTERS AND SETTERS

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stream<Note> getNotes()
    {
        return this.notes.stream();
    }
}
