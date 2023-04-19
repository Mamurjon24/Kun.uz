package com.example.exp;

public class MethodNotAllowedExeption extends RuntimeException{
    public MethodNotAllowedExeption(String message) {
        super(message);
    }
}
