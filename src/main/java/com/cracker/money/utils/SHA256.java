package com.cracker.money.utils;

import java.security.MessageDigest;

public class SHA256 {
    public SHA256() {
    }

    public static String encrypt(String planText) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(planText.getBytes());
            byte[] byteData = md.digest();
            StringBuilder sb = new StringBuilder();

            for (byte byteDatum : byteData) {
                sb.append(Integer.toString((byteDatum & 255) + 256, 16).substring(1));
            }

            StringBuilder hexString = new StringBuilder();

            for(int i = 0; i < byteData.length; ++i) {
                String hex = Integer.toHexString(255 & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception var7) {
            var7.printStackTrace();
            throw new RuntimeException();
        }
    }
}
