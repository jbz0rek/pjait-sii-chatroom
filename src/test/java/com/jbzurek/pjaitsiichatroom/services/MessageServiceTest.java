package com.jbzurek.pjaitsiichatroom.services;

import com.jbzurek.pjaitsiichatroom.domain.Message;
import com.jbzurek.pjaitsiichatroom.domain.MessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MessageServiceTest {

    private MessageService messageService;

    @BeforeEach
    void setUp() {
        messageService = new MessageService();
    }

    @Test
    void shouldIgnoreNullMessage() {
        messageService.addMessageWithNormalization(null);

        List<Message> result = messageService.lastForActiveUser(10, "user");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldNormalizeBlankAuthorToAnon() {
        messageService.addMessageWithNormalization(new MessageDto("   ", "hello"));

        List<Message> result = messageService.lastForActiveUser(10, "user");

        assertEquals(1, result.size());
        assertEquals("Anon", result.get(0).author());
        assertEquals("hello", result.get(0).content());
    }

    @Test
    void shouldTrimAuthorAndContent() {
        messageService.addMessageWithNormalization(new MessageDto("  Jan  ", "  hi there  "));

        List<Message> result = messageService.lastForActiveUser(10, "user");

        assertEquals(1, result.size());
        assertEquals("Jan", result.get(0).author());
        assertEquals("hi there", result.get(0).content());
    }

    @Test
    void shouldConvertNullContentToEmptyString() {
        messageService.addMessageWithNormalization(new MessageDto("Jan", null));

        List<Message> result = messageService.lastForActiveUser(10, "user");

        assertEquals(1, result.size());
        assertEquals("Jan", result.get(0).author());
        assertEquals("", result.get(0).content());
    }

    @Test
    void shouldMarkMessageAsMineWhenAuthorMatchesLoggedUserIgnoringCase() {
        messageService.addMessageWithNormalization(new MessageDto("Jan", "hello"));

        List<Message> result = messageService.lastForActiveUser(10, "jAn");

        assertEquals(1, result.size());
        assertTrue(result.get(0).mine());
    }

    @Test
    void shouldMarkMessageAsNotMineWhenAuthorDoesNotMatchLoggedUser() {
        messageService.addMessageWithNormalization(new MessageDto("Jan", "hello"));

        List<Message> result = messageService.lastForActiveUser(10, "Adam");

        assertEquals(1, result.size());
        assertFalse(result.get(0).mine());
    }

    @Test
    void shouldReturnOnlyLastMessagesUpToLimit() {
        messageService.addMessageWithNormalization(new MessageDto("A", "one"));
        messageService.addMessageWithNormalization(new MessageDto("B", "two"));
        messageService.addMessageWithNormalization(new MessageDto("C", "three"));

        List<Message> result = messageService.lastForActiveUser(2, "user");

        assertEquals(2, result.size());
        assertEquals("B", result.get(0).author());
        assertEquals("two", result.get(0).content());
        assertEquals("C", result.get(1).author());
        assertEquals("three", result.get(1).content());
    }

    @Test
    void shouldReturnEmptyListWhenLimitIsZero() {
        messageService.addMessageWithNormalization(new MessageDto("Jan", "hello"));

        List<Message> result = messageService.lastForActiveUser(0, "Jan");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldKeepOnlyLast200Messages() {
        for (int i = 1; i <= 205; i++) {
            messageService.addMessageWithNormalization(new MessageDto("user" + i, "message" + i));
        }

        List<Message> result = messageService.lastForActiveUser(300, "user");

        assertEquals(200, result.size());
        assertEquals("user6", result.get(0).author());
        assertEquals("message6", result.get(0).content());
        assertEquals("user205", result.get(199).author());
        assertEquals("message205", result.get(199).content());
    }
}
