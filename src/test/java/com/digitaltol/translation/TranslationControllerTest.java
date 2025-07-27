package com.digitaltol.translation;


import com.digitaltol.translation.controller.TranslationController;
import com.digitaltol.translation.dto.TranslationDto;
import com.digitaltol.translation.service.TranslationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TranslationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TranslationControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private TranslationService service;
    @Autowired
    private ObjectMapper mapper;



    @Test
    void searchDefault() throws Exception {
        var dto = new TranslationDto(2L, "fr", "k2", "c2", "t2");
        var page = new PageImpl<>(List.of(dto));
        Mockito.when(service.search(null, null, null, PageRequest.of(0,20))).thenReturn(page);
        mvc.perform(get("/api/translations"))
                .andExpect(status().isOk());
    }
}
