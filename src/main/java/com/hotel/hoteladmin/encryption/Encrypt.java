package com.hotel.hoteladmin.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
    private String algorithm;

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String encrypt(String password){
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder newPasswordBuilder  = new StringBuilder();
            for (byte aByte : bytes) {
                newPasswordBuilder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return newPasswordBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

