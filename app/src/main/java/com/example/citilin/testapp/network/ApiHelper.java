package com.example.citilin.testapp.network;

import com.example.citilin.testapp.BuildConfig;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by citilin on 14.08.2017.
 */

public class ApiHelper {

    public static String getMD5(String inputString){
        MessageDigest messageDigest;
        byte[] digest = new byte[0];
        inputString+= BuildConfig.MARVEL_PRIVATE_KEY + BuildConfig.MARVEL_PUBLIC_KEY;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(inputString.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;
    }

    public static String getTime(){
        Date date = new Date();
        return String.valueOf(date.getTime());
    }
}
