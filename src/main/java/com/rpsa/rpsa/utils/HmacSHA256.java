package com.rpsa.rpsa.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

public class HmacSHA256 {
    static final long FIVE_MINUTE_ZONE = 60 * 5;

    static public String generate(final String secretKey, final String message) throws Exception {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hmacSha256 = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hmacSha256);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new Exception(e);
        }
    }

    static public String generateCurrentSignature(final String secretKey, final String message) throws Exception {
        long utz = Instant.now().getEpochSecond();
        final long currentTime = utz / FIVE_MINUTE_ZONE;
        final String newKey = currentTime + ":" + secretKey;
        return generate(newKey, message);
    }
}
