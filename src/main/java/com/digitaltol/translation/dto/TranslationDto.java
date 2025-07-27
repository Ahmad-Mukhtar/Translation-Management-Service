package com.digitaltol.translation.dto;

public record TranslationDto(
        Long id,
        String locale,
        String translationKey,
        String content,
        String tag
) {}