package com.chehui.maiche.utils;

import java.security.MessageDigest;

public class MD5
{

    private final static String[] hexInts = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a",
            "b", "c", "d", "e", "f"};

    public static String byteArrayToHexString(byte[] b)
    {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < b.length; i++)
        {
            buffer.append(byteToHexString(b[i]));
        }

        return buffer.toString();
    }

    private static String byteToHexString(byte b)
    {
        int n = b;

        if (n < 0)
        {
            n = 256 + n;
        }

        int d1 = n / 16;

        int d2 = n % 16;

        return hexInts[d1] + hexInts[d2];
    }

    public static byte[] compile(String origin)
    {
        String resultString = null;
        byte[] result = null;
        try
        {

            resultString = origin;

            MessageDigest md = MessageDigest.getInstance("MD5");
            result = md.digest(resultString.getBytes());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return result;
    }

}