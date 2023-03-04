package ru.estartsev.kindleanki.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "WORDS")
public class Word {

    @Id
    private String id;

    private String word;

    private String stem;

    private String lang;

    @OneToMany(mappedBy = "word", fetch = FetchType.EAGER)
    private List<Lookup> lookups;

    private Integer category;

    private LocalDateTime timestamp;

    public String getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getStem() {
        return stem;
    }

    public String getLang() {
        return lang;
    }

    public Integer getCategory() {
        return category;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<Lookup> getLookups() {
        return lookups;
    }
}