package com.crud.all.utils;

import java.util.regex.Pattern;

public class EmailValidator {
    // Expressão regular simples para validação de e-mail
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public static boolean isValid(String email) {
        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }
}
