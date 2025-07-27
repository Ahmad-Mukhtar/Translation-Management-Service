package com.digitaltol.translation.service;

import com.digitaltol.translation.dto.TranslationDto;
import com.digitaltol.translation.entity.Translation;
import com.digitaltol.translation.exceptions.ApiException;
import com.digitaltol.translation.exceptions.ResourceNotFoundException;
import com.digitaltol.translation.repository.TranslationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TranslationService {
    private final TranslationRepository repo;
    public TranslationService(TranslationRepository repo) { this.repo = repo; }

    public TranslationDto toDto(Translation t) {
        return new TranslationDto(t.getId(), t.getLocale(), t.getTranslationKey(), t.getContent(), t.getTag());
    }

    public TranslationDto create(TranslationDto dto) {
        var ent = new Translation();
        ent.setLocale(dto.locale()); ent.setTranslationKey(dto.translationKey());
        ent.setContent(dto.content()); ent.setTag(dto.tag());
        return toDto(repo.save(ent));
    }

    public TranslationDto update(Long id, TranslationDto dto) {
        var ent = repo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Translation not found with id " + id));
        if(dto.locale()!=null && !dto.locale().isEmpty())
        {
            if(dto.locale().length()<=5)
            {
                ent.setLocale(dto.locale());
            }
            else
            {
                throw new ApiException("Locale Length Cannot be greater tha 5", HttpStatus.BAD_REQUEST);

            }
        }

        if(dto.translationKey()!=null && !dto.translationKey().isEmpty())
        {
            if(dto.translationKey().length()<=255)
            {
                ent.setTranslationKey(dto.translationKey());
            }
            else
            {
                throw new ApiException("TranslationKey Length Cannot be greater tha 255", HttpStatus.BAD_REQUEST);

            }

        }
        if(dto.content()!=null && !dto.content().isEmpty())
        {

            ent.setContent(dto.content());

        }

        if(dto.tag()!=null && !dto.tag().isEmpty())
        {
            if(dto.tag().length()<=50)
            {
                ent.setTag(dto.tag());
            }
            else
            {
                throw new ApiException("Tag Length Cannot be greater tha 50", HttpStatus.BAD_REQUEST);

            }
        }

        return toDto(repo.save(ent));
    }

    public TranslationDto get(Long id) {
        return repo.findById(id).map(this::toDto).orElseThrow(() -> new ResourceNotFoundException("Translation not found with id " + id));
    }

    public Page<TranslationDto> search(String tag, String key, String content, Pageable page) {
        if (tag != null) return repo.findByTag(tag, page).map(this::toDto);
        if (key != null) return repo.findByTranslationKeyContainingIgnoreCase(key, page).map(this::toDto);
        if (content != null) return repo.findByContentContainingIgnoreCase(content, page).map(this::toDto);
        return repo.findAll(page).map(this::toDto);
    }

    public List<TranslationDto> exportAllS() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Stream<Translation> streamAll() {
        return repo.streamAll();
    }
}