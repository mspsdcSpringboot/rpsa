package com.rpsa.rpsa.config;



import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SHA256PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    public String getHash(String password) {
        byte buff[] = password.getBytes();
        byte[] digest = null;
        String hexString = "";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.reset();
            md.update(buff);
            digest = md.digest();

            for (int i = 0; i < digest.length; i++) {
                hexString += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1);
            }
        } catch (NoSuchAlgorithmException e) {
        } finally {
            md = null;
            digest = null;
            buff = null;
        }
        return hexString;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
//        System.out.println("Inside custom password encoder");
//        System.out.println("Raw: " + rawPassword);
//        System.out.println("Encode: " + encodedPassword);
//        System.out.println("Encoded: " + getHash(rawPassword.toString()));
//        System.out.println(getHash(rawPassword.toString()).equals(encodedPassword));
        return getHash(rawPassword.toString()).equals(encodedPassword);
    }


}
