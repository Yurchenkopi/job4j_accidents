package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyInt;
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
    @Qualifier("springDataAccidentService")
    private AccidentService springDataAccidentService;

    @MockBean
    @Qualifier("springDataAccidentTypeService")
    private AccidentTypeService springDataAccidentTypeService;

    @MockBean
    @Qualifier("springDataRuleService")
    private RuleService springDataRuleService;

    @Test
    @WithMockUser
    public void whenRequestAccidentsPageThenGetPageWithAccidents() throws Exception {
        var defaultAccidentType = new AccidentType(4, "Неизвестно");
        var defaultRules = Set.of(new Rule(1, "Статья 1"));
        var accident1 = new Accident(0, "Тестовое название 1", defaultAccidentType, defaultRules, "Тестовый текст 1", "Адрес 1");
        var accident2 = new Accident(0, "Тестовое название 2", defaultAccidentType, defaultRules, "Тестовый текст 2", "Адрес 2");
        List<Accident> mockAccidents = List.of(accident1, accident2);

        when(springDataAccidentService.findAll()).thenReturn(mockAccidents);

        this.mockMvc.perform(get("/accidents"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/list"))
                .andExpect(model().attributeExists("accidents"))
                .andExpect(model().attribute("accidents", mockAccidents));
    }

    @Test
    @WithMockUser
    public void whenRequestAccidentCreatePageThenGetThisPage() throws Exception {
        var mockTypes = List.of(
                new AccidentType(1, "Две машины"),
                new AccidentType(2, "Машина и человек"),
                new AccidentType(3, "Машина и велосипед"),
                new AccidentType(4, "Неизвестно")
                );
        var mockRules = Set.of(
                new Rule(1, "Статья 1"),
                new Rule(2, "Статья 2"),
                new Rule(3, "Статья 3"),
                new Rule(4, "Статья 4")
        );

        when(springDataAccidentTypeService.findAll()).thenReturn(mockTypes);
        when(springDataRuleService.findAll()).thenReturn(mockRules);

        this.mockMvc.perform(get("/accidents/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/create"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("rules"))
                .andExpect(model().attribute("types", mockTypes))
                .andExpect(model().attribute("rules", mockRules));
    }

    @Test
    @WithMockUser
    public void whenRequestAccidentUpdatePageThenGetThisPage() throws Exception {
        var mockTypes = List.of(
                new AccidentType(1, "Две машины"),
                new AccidentType(2, "Машина и человек"),
                new AccidentType(3, "Машина и велосипед"),
                new AccidentType(4, "Неизвестно")
        );
        var mockRules = Set.of(
                new Rule(1, "Статья 1"),
                new Rule(2, "Статья 2"),
                new Rule(3, "Статья 3"),
                new Rule(4, "Статья 4")
        );
        var mockAccident = new Accident(0, "Тестовое название 1", mockTypes.get(0), mockRules, "Тестовый текст 1", "Адрес 1");

        when(springDataAccidentTypeService.findAll()).thenReturn(mockTypes);
        when(springDataRuleService.findAll()).thenReturn(mockRules);
        when(springDataAccidentService.findById(anyInt())).thenReturn(Optional.of(mockAccident));

        this.mockMvc.perform(get("/accidents/update")
                        .param("id", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/edit"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("rules"))
                .andExpect(model().attributeExists("accident"))
                .andExpect(model().attribute("types", mockTypes))
                .andExpect(model().attribute("rules", mockRules))
                .andExpect(model().attribute("accident", mockAccident));
    }

    @Test
    @WithMockUser
    public void whenRequestAccidentUpdatePageThenGetErrorPage() throws Exception {
        var expectedErrorMessage = "Заявка с указанным ID не найдена.";

        when(springDataAccidentService.findById(anyInt())).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/accidents/update")
                        .param("id", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", expectedErrorMessage));
    }

}
