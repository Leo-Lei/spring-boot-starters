package com.leibangzhu.starters.common.util;

import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.Base64;

public class Base64Util {

    /**
     * 字符串转BASE64
     *
     * @param str 要转码的字符串，不能是被json序列化后的字符串
     * @return
     */
    public static String encode(String str) {
        return new String(Base64.getEncoder().encode(str.getBytes()));
    }

    /**
     * Object对象采用Base64加密
     *
     * @param o 被加密的对象
     * @return
     */
    public static String encode(Object o) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.flush();
                oos.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new String(Base64.getEncoder().encode(bos.toByteArray()));
    }

    /**
     * 解密
     *
     * @param str
     * @return
     */
    public static String decodeToString(String str) {
        if ("".equals(str) || null == str) {
            return "";
        }
        return new String(Base64.getDecoder().decode(str));
    }

    /**
     * 字符串转BASE64
     *
     * @param str BASE64字符串
     * @return
     */
    public static Object decodeToObject(String str) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(str));
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object o = null;
        try {
            o = ois.readObject();
        } catch (EOFException e) {
            System.err.print("read finished");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        bis.close();
        ois.close();
        return o;
    }
}
