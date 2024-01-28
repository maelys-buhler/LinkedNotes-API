package ch.hearc.mbu.repository.note;

import ch.hearc.mbu.repository.tag.Tag;
import ch.hearc.mbu.repository.user.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "notes")
@JsonSerialize(using = NoteSerializer.class)
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany
    private Set<Tag> tags;


    //GETTERS AND SETTERS
    public Long getId() {
        return this.id;
    }
    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public User getUser() {
        return this.user;
    }

    public Iterable<Tag> getTags() {
        return this.tags;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
