package ch.hearc.mbu.repository.link;

import ch.hearc.mbu.repository.note.Note;
import ch.hearc.mbu.repository.type.Type;
import jakarta.persistence.*;

import java.awt.*;

@Entity
@Table(name = "links")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Color color;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private Type type;
    @ManyToOne
    @JoinColumn(name = "note_1_id", referencedColumnName = "id")
    private Note note1;
    @ManyToOne
    @JoinColumn(name = "note_2_id", referencedColumnName = "id")
    private Note note2;

    //GETTERS AND SETTERS
    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }

    public Color getColor() {
        return this.color;
    }

    public Type getType() {
        return this.type;
    }

    public Note getNote1() {
        return this.note1;
    }

    public Note getNote2() {
        return this.note2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setNote1(Note note1) {
        this.note1 = note1;
    }

    public void setNote2(Note note2) {
        this.note2 = note2;
    }
}
