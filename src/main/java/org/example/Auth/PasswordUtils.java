package org.example.Auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {


    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String hash(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean slowEquals(String hash1, String hash2) {
        return passwordEncoder.matches(hash1, hash2);
    }
}
