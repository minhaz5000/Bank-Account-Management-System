package com.fma.bank_account_management_system;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Encryption {

//    public static String generateSalt(int length) {
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[length];
//        random.nextBytes(salt);
//        return salt.toString();
//    }
    public static SecretKey getKeyFromPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey originalKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return originalKey;
    }

        public static String convertSecretKeyToString(SecretKey secretKey) throws NoSuchAlgorithmException {
            byte[] rawData = secretKey.getEncoded();
            String encodedKey = Base64.getEncoder().encodeToString(rawData);
            return encodedKey;
    }
    public static SecretKey convertStringToSecretKeyto(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        return originalKey;
    }
    public static String encodeKey(String s) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKey encodedKey = getKeyFromPassword(s, "@$#baelDunG@#^$*");
        String encodedString = convertSecretKeyToString(encodedKey);
        return encodedString;

    }
//    public static String decodeKey(String encodedString) throws NoSuchAlgorithmException {
//        SecretKey decodeKey = convertStringToSecretKeyto(encodedString);
//        return convertSecretKeyToString(decodeKey);
//    }

}
