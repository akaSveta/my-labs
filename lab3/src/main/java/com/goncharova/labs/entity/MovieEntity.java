package com.goncharova.labs.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieEntity {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "uuid")
    public UUID id;
    @Column(name = "title", nullable = false, length = 256)
    public String title;
    @Column(name = "message", nullable = false, columnDefinition = "text")
    public String message;
}