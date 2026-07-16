package com.GA.gymApp.exceptions;


public class Exceptions {

    public static class ResourceNotFoundException extends RuntimeException {

        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    public static class ConflictException extends RuntimeException {

        public ConflictException(String message) {
            super(message);
        }
    }

    public static class BadRequestException extends RuntimeException {

        public BadRequestException(String message) {
            super(message);
        }
    }

    public static class AuthorizationException extends RuntimeException{

        public AuthorizationException(String message){
            super(message);
        }
    }
}