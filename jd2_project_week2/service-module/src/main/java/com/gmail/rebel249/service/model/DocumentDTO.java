package com.gmail.rebel249.service.model;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DocumentDTO {

    private Long id;
    private UUID uniqueNumber;
    private String description;

    @Bean
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
        return "DocumentDTO{uniqueNumber=" + uniqueNumber +
                ", description='" + description + '\'' +
                '}';
    }
}
