package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentService accidentService;

    @Test
    @WithMockUser
    public void whenRequestAccidentsPageThenGetPageWithAccidents() throws Exception {
        var defaultAccidentType = new AccidentType(4, "Неизвестно");
        var defaultRules = Set.of(new Rule(1, "Статья 1"));
        var accident1 = new Accident(0, "Тестовое название 1", defaultAccidentType, defaultRules, "Тестовый текст 1", "Адрес 1");
        var accident2 = new Accident(0, "Тестовое название 2", defaultAccidentType, defaultRules, "Тестовый текст 2", "Адрес 2");
        List<Accident> mockAccidents = List.of(accident1, accident2);

        when(accidentService.findAll()).thenReturn(mockAccidents);

        this.mockMvc.perform(get("/accidents"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/list"))
                .andExpect(model().attributeExists("accidents"))
                .andExpect(model().attribute("accidents", mockAccidents));
    }

}
