package com.jbzurek.pjaitsiichatroom.domain;

public record Message(
        String author,
        String content,
        boolean mine
) {

}
