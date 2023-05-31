package com.wordle.wordlemania.Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils { // class untuk bikin password dengan hash dan salt (enkripsi gitu)
    private static final int SALT_LENGTH = 16;

    public static String generateHashPass(String password, String salt) { // method untuk generated password yg sdh
                                                                          // ditambahkan hash dan salt
        if (password.isEmpty()) {
            return password;
        }

        try {
            String saltedPassword = salt + password;
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to hash password", e);
        }
    }

    public static String generateSalt() throws NoSuchAlgorithmException { // method untuk generated saltByte
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

}