package com.digitaltol.translation;

import com.digitaltol.translation.dto.TranslationDto;
import com.digitaltol.translation.entity.Translation;
import com.digitaltol.translation.repository.TranslationRepository;
import com.digitaltol.translation.service.TranslationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TranslationServiceTest {

    private TranslationRepository repo;
    private TranslationService service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(TranslationRepository.class);
        service = new TranslationService(repo);
    }

    @Test
    void createAndGet() {
        var ent = new Translation(); ent.setId(1L);
        ent.setLocale("en"); ent.setTranslationKey("k"); ent.setContent("c"); ent.setTag("t");
        Mockito.when(repo.save(Mockito.any())).thenReturn(ent);
        var dto = new TranslationDto(null, "en", "k", "c", "t");
        var created = service.create(dto);
        assertThat(created.id()).isEqualTo(1L);

        Mockito.when(repo.findById(1L)).thenReturn(java.util.Optional.of(ent));
        var fetched = service.get(1L);
        assertThat(fetched.content()).isEqualTo("c");
    }

    @Test
    void searchByTag() {
        var ent = new Translation(); ent.setId(2L);
        var page = new PageImpl<>(List.of(ent));
        Mockito.when(repo.findByTag("mobile", PageRequest.of(0,10))).thenReturn(page);
        Page<TranslationDto> result = service.search("mobile", null, null, PageRequest.of(0,10));
        assertThat(result.getContent()).hasSize(1);
    }
}