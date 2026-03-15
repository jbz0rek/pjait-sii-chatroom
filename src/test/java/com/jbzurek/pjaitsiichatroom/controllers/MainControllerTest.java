package com.jbzurek.pjaitsiichatroom.controllers;

import com.jbzurek.pjaitsiichatroom.domain.Message;
import com.jbzurek.pjaitsiichatroom.services.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MessageService messageService;

    @WithMockUser(username = "testUser")
    @Test
    void shouldReturnIndexViewWithMessagesAndLoggedUserName() throws Exception {
        List<Message> messages = List.of(
                new Message("Jan", "hello", false),
                new Message("testUser", "my message", true)
        );

        when(messageService.lastForActiveUser(100, "testUser")).thenReturn(messages);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("loggedUserName", "testUser"))
                .andExpect(model().attribute("messages", messages));
    }
}
