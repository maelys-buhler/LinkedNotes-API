package ch.hearc.mbu.repository.user;

import jakarta.persistence.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "users")
public class User {
    private final int API_KEY_LENGTH = 32;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String apiKey;

    public String generateApiKey() {
        this.apiKey = RandomStringUtils.randomAlphanumeric(API_KEY_LENGTH);
        return this.apiKey;
    }

    public User(String username, String apiKey) {
        this.username = username;
        this.apiKey = generateApiKey();
    }

    public User() {
    }

    //GETTERS AND SETTERS
    public Long getId() {
        return this.id;
    }
    public String getUsername() {
        return this.username;
    }

    public String getApiKey() {
        return this.apiKey;
    }
}
