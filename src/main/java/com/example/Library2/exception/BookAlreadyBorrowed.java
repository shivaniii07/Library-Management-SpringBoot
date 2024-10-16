package com.example.Library2.exception;

public class BookAlreadyBorrowed extends Exception{

    public BookAlreadyBorrowed(String message){
        super(message);
    }
}
