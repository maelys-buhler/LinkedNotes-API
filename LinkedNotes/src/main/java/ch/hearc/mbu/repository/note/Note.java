package ch.hearc.mbu.repository.note;

import ch.hearc.mbu.repository.tag.Tag;
import ch.hearc.mbu.repository.user.User;
import jakarta.persistence.*;

import java.util.Set;
import java.util.stream.Stream;

@Entity
@Table(name = "notes")
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

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public User getUser() {
        return this.user;
    }

    public Stream<Tag> getTags() {
        return this.tags.stream();
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
