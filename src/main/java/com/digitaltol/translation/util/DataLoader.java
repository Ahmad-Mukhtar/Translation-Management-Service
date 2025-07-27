package com.digitaltol.translation.util;


import com.digitaltol.translation.entity.Translation;
import com.digitaltol.translation.repository.TranslationRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DataLoader implements ApplicationListener<ApplicationReadyEvent> {
    private final TranslationRepository repo;
    public DataLoader(TranslationRepository repo) {
        this.repo = repo;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Random rnd = new Random();
        for (int i = 0; i < 100_000; i++) {
            var t = new Translation();
            t.setLocale(i % 3 == 0 ? "en" : i % 3 == 1 ? "fr" : "es");
            t.setTranslationKey("key" + i);
            t.setContent("content" + rnd.nextInt(1_000_000));
            t.setTag(i % 2 == 0 ? "mobile" : "desktop");
            repo.save(t);
        }
        repo.flush();
    }
}

