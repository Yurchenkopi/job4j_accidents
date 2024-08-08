package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserSpringDataRepository;
import ru.job4j.accidents.service.SpringDataUserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL_INT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class RegControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpringDataUserService users;

    @Test
    @WithMockUser
    public void whenRequestRegPageThenGetThisPage() throws Exception {
        this.mockMvc.perform(get("/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    @WithMockUser
    public void whenTryToRegisterThenSuccess() throws Exception {
        var expectedUserName = "test_user";
        var expectedPassword = "12345";

        var expectedUser = new User();
        expectedUser.setUsername(expectedUserName);
        expectedUser.setPassword(expectedPassword);

        when(users.save(any(User.class))).thenReturn(Optional.of(expectedUser));

        this.mockMvc.perform(post("/reg")
                        .param("username", expectedUserName)
                        .param("password", expectedPassword))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(users).save(argument.capture());
        assertThat(argument.getValue().getUsername()).isEqualTo(expectedUserName);
    }

    @Test
    @WithMockUser
    public void whenTryToRegisterThenRedirectAndErrorMessage() throws Exception {
        var expectedUserName = "test_user";
        var expectedPassword = "12345";

        var expectedErrorMessage = "Пользователь с таким username уже зарегистрирован.";

        when(users.save(any(User.class))).thenReturn(Optional.empty());

        this.mockMvc.perform(post("/reg")
                        .param("username", expectedUserName)
                        .param("password", expectedPassword))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", expectedErrorMessage));
    }

}
