package com.digitaltol.translation.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "translations", indexes = {
        @Index(columnList = "locale"),
        @Index(columnList = "tag"),
        @Index(columnList = "content")
})
@Data
public class Translation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String locale;
    @Column(name = "translation_key", nullable = false)
    private String translationKey;
    private String content;
    private String tag;
}