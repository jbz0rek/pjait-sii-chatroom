package com.jbzurek.pjaitsiichatroom.controllers;

import com.jbzurek.pjaitsiichatroom.domain.Message;
import com.jbzurek.pjaitsiichatroom.services.MessageService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private final MessageService messageService;

    public MainController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/")
    public ModelAndView index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedUserName = authentication.getName();

        List<Message> messages = messageService.lastForActiveUser(100, loggedUserName);

        Map<String, Object> model = new HashMap<>();
        model.put("loggedUserName", loggedUserName);
        model.put("messages", messages);

        return new ModelAndView("index", model);
    }
}
