package com.myprojects.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Validation {

    static String BaseSalt="!l0v3^^y!^d!@";

    public static String getHash(String dataToBeCrypt)
    {
        String newHash=null;
        try{
            MessageDigest md= MessageDigest.getInstance("SHA-512");
            md.update(BaseSalt.getBytes());
            byte[] bytes = md.digest(dataToBeCrypt.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            newHash = sb.toString();
        }catch (NoSuchAlgorithmException nx)
        {
            nx.printStackTrace();
        }
        return newHash;
    }
}
