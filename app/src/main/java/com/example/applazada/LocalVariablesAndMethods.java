package com.example.applazada;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LocalVariablesAndMethods {
    public static final String domain = "http://192.168.0.139:80";
    public static float CURRENCY = 0.000044f;
    public static final String DANGXULY = "Đang Xử Lý";
    public static final String DANGGIAO = "Đang Giao";
    public static final String DAHUY = "Đã Huỷ";
    public static final String DANHAN = "Đã Nhận";

    public static String MD5Hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
