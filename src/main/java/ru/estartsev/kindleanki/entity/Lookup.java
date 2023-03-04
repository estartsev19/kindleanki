package ru.estartsev.kindleanki.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "LOOKUPS")
public class Lookup {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "word_key")
    private Word word;

    private String usage;

    private LocalDateTime timestamp;

    public String getId() {
        return id;
    }


    public String getUsage() {
        return usage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Word getWord() {
        return word;
    }
}
