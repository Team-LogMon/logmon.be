package com.cau.gdg.logmon.auth;

public final class AuthenticationContextHolder {

    private AuthenticationContextHolder() {
        throw new AssertionError("Cannot instantiate this class");
    }

    private static final ThreadLocal<AuthenticationContext> contextHolder = new ThreadLocal<>();

    public static void setContext(AuthenticationContext context) {
        contextHolder.set(context);
    }

    public static AuthenticationContext getContext() {
        return contextHolder.get();
    }

    public static void clearContext() {
        contextHolder.remove();
    }
}
