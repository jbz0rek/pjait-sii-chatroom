package com.jbzurek.pjaitsiichatroom.services;

import com.jbzurek.pjaitsiichatroom.domain.Message;
import com.jbzurek.pjaitsiichatroom.domain.MessageDto;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Service
public class MessageService {

    private static final int MAX_HISTORY_SIZE = 200;

    private final Deque<MessageDto> history = new ArrayDeque<>();

    public synchronized void addMessageWithNormalization(MessageDto message) {
        if (message == null) {
            return;
        }

        MessageDto normalizedMessage = normalize(message);
        history.addLast(normalizedMessage);

        if (history.size() > MAX_HISTORY_SIZE) {
            history.removeFirst();
        }
    }

    public synchronized List<Message> lastForActiveUser(int limit, String currentUser) {
        List<MessageDto> lastMessages = lastMessagesForLimit(limit);
        List<Message> result = new ArrayList<>();

        for (MessageDto messageDto : lastMessages) {
            Message message = toViewModel(messageDto, currentUser);
            result.add(message);
        }

        return result;
    }

    private List<MessageDto> lastMessagesForLimit(int limit) {
        List<MessageDto> result = new ArrayList<>();

        if (limit <= 0) {
            return result;
        }

        if (history.isEmpty()) {
            return result;
        }

        int startIndex = history.size() - limit;
        if (startIndex < 0) {
            startIndex = 0;
        }

        int currentIndex = 0;

        for (MessageDto messageDto : history) {
            if (currentIndex >= startIndex) {
                result.add(messageDto);
            }
            currentIndex++;
        }

        return result;
    }

    private Message toViewModel(MessageDto messageDto, String currentUser) {
        boolean mine = isMineMessage(messageDto, currentUser);

        return new Message(
                messageDto.author(),
                messageDto.content(),
                mine
        );
    }

    private boolean isMineMessage(MessageDto messageDto, String currentUser) {
        if (currentUser == null) {
            return false;
        }

        if (messageDto == null) {
            return false;
        }

        if (messageDto.author() == null) {
            return false;
        }

        String trimmedCurrentUser = currentUser.trim();

        return messageDto.author().equalsIgnoreCase(trimmedCurrentUser);
    }

    private MessageDto normalize(MessageDto message) {
        String normalizedAuthor = normalizeAuthor(message.author());
        String normalizedContent = normalizeContent(message.content());

        return new MessageDto(normalizedAuthor, normalizedContent);
    }

    private String normalizeAuthor(String author) {
        String trimmedAuthor = safeTrim(author);

        if (trimmedAuthor.isBlank()) {
            return "Anon";
        }

        return trimmedAuthor;
    }

    private String normalizeContent(String content) {
        return safeTrim(content);
    }

    private String safeTrim(String value) {
        if (value == null) {
            return "";
        }

        return value.trim();
    }
}
