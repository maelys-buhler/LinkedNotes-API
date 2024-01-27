package ch.hearc.mbu.repository.type;

import jakarta.persistence.*;

@Entity
@Table(name = "types")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    //GETTERS AND SETTERS
    public Long getId() {
        return this.id;
    }
    public String getName() {
        return name;
    }
}
