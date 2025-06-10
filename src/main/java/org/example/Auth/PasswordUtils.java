package org.example.Auth;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

public class PasswordUtils {
    private static final int SALT_LENGTH = 16;
    private static final int HASH_ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;


    public static byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    public static byte[] hash(char[] password, byte[] salt) {
        try {
            KeySpec spec = new PBEKeySpec(password, salt, HASH_ITERATIONS, KEY_LENGTH);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return f.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException("Error while hashing a password: " + e.getMessage(), e);
        }
    }

    public static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }
}
