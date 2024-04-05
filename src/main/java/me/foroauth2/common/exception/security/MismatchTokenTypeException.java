package me.foroauth2.common.exception.security;

public class MismatchTokenTypeException extends RuntimeException{
    public MismatchTokenTypeException(String m){
        super(m);
    }
}
