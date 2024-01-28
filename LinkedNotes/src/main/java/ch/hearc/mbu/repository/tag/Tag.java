package ch.hearc.mbu.repository.tag;

import ch.hearc.mbu.repository.note.Note;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tags")
@JsonSerialize(using = TagSerializer.class)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Note> notes;

    //GETTERS AND SETTERS
    public Long getId() {
        return this.id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Iterable<Note> getNotes()
    {
        return this.notes;
    }
}
