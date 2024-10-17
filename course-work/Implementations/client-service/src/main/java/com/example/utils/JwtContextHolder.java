package com.example.utils;

public class JwtContextHolder {
    private static final ThreadLocal<String> currentJwt = new ThreadLocal<>();

    public static void setJwtToken(String token) {
        currentJwt.set(token);
    }

    public static String getJwtToken() {
        return currentJwt.get();
    }

    public static void clear() {
        currentJwt.remove();
    }
}