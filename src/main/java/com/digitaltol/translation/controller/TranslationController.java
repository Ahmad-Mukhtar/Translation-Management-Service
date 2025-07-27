package com.digitaltol.translation.controller;

import com.digitaltol.translation.dto.TranslationDto;
import com.digitaltol.translation.entity.Translation;
import com.digitaltol.translation.service.TranslationService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/translations")
public class TranslationController {
    private final TranslationService svc;
    public TranslationController(TranslationService svc) { this.svc = svc; }

    @PostMapping
    public ResponseEntity<TranslationDto> create(@RequestBody TranslationDto dto) {
        return ResponseEntity.ok(svc.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TranslationDto> update(@PathVariable Long id, @RequestBody TranslationDto dto) {
        return ResponseEntity.ok(svc.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TranslationDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(svc.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<TranslationDto>> search(
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String key,
            @RequestParam(required = false) String content,
            Pageable page) {
        return ResponseEntity.ok(svc.search(tag, key, content, page));
    }

    @GetMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StreamingResponseBody> streamTranslations() {
        StreamingResponseBody stream = outputStream -> {
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(outputStream);
            ObjectMapper mapper = new ObjectMapper();

            jsonGenerator.writeStartArray(); // [

            try (Stream<Translation> translations = svc.streamAll()) {
                translations.forEach(translation -> {
                    try {
                        TranslationDto dto = svc.toDto(translation);
                        mapper.writeValue(jsonGenerator, dto); // writes one object
                    } catch (IOException e) {
                        throw new RuntimeException("Error writing JSON", e);
                    }
                });
            }

            jsonGenerator.writeEndArray(); // ]
            jsonGenerator.flush();
        };

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=translations.json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(stream);
    }

}