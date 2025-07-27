package com.digitaltol.translation.repository;

import com.digitaltol.translation.entity.Translation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface TranslationRepository extends JpaRepository<Translation, Long> {
    Page<Translation> findByTag(String tag, Pageable page);
    Page<Translation> findByTranslationKeyContainingIgnoreCase(String translation_key, Pageable page);
    Page<Translation> findByContentContainingIgnoreCase(String content, Pageable page);

    @Query("SELECT t FROM Translation t")
    Stream<Translation> streamAll();
}