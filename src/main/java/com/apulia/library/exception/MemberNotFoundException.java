package com.apulia.library.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(Integer id) {
        super("Membro con ID " + id + " non trovato");
    }
}
