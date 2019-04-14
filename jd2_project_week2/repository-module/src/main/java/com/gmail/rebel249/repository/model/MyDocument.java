package com.gmail.rebel249.repository.model;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MyDocument {

    private Long id;
    private UUID uniqueNumber;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(UUID uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MyDocument{" +
                "id=" + id +
                ", uniqueNumber=" + uniqueNumber +
                ", description='" + description + '\'' +
                '}';
    }
}
