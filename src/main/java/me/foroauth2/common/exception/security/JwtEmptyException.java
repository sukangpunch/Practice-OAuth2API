package me.foroauth2.common.exception.security;

public class JwtEmptyException extends RuntimeException{
    public JwtEmptyException(String m) {
        super(m);
    }
}
