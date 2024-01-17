package ch.hearc.mbu.repository.note;

import ch.hearc.mbu.repository.user.User;
import jakarta.persistence.*;

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
    private User userId;

    //GETTERS AND SETTERS

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public User getUserId() {
        return this.userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
