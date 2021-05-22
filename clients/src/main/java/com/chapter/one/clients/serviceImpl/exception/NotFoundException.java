package com.chapter.one.clients.serviceImpl.exception;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = -201408306528205787L;

    public NotFoundException(String msg) {
        super(msg);
    }
}
