package ch.hearc.mbu.repository.link;

import ch.hearc.mbu.repository.note.Note;
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
    private int type;
    @ManyToOne
    @JoinColumn(name = "note_1_id", referencedColumnName = "id")
    private Note note1Id;
    @ManyToOne
    @JoinColumn(name = "note_2_id", referencedColumnName = "id")
    private Note note2Id;

    //GETTERS AND SETTERS

    public String getName() {
        return this.name;
    }

    public Color getColor() {
        return this.color;
    }

    public int getType() {
        return this.type;
    }

    public Note getNote1Id() {
        return this.note1Id;
    }

    public Note getNote2Id() {
        return this.note2Id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setNote1Id(Note note1Id) {
        this.note1Id = note1Id;
    }

    public void setNote2Id(Note note2Id) {
        this.note2Id = note2Id;
    }
}
