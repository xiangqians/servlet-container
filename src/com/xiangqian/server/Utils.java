package com.xiangqian.server;


import java.io.*;

/**
 * @author xiangqian
 * @date 00:32 2019/11/21
 */
public class Utils {

    private static final int CAPACITY = 1024;
    private static final String UTF_8 = "UTF-8";

    public static String trim(Object obj) {
        return obj == null ? "" : obj.toString().trim();
    }

    public static void copyInBinary(InputStream is, OutputStream os, boolean isCloseIS, boolean isCloseOS) throws IOException {
        try {
            byte[] buff = new byte[CAPACITY];
            int readLen = -1;
            while ((readLen = is.read(buff, 0, CAPACITY)) != -1) {
                os.write(buff, 0, readLen);
            }
            os.flush();

        } finally {
            quietlyClosed(isCloseIS ? is : null, isCloseOS ? os : null);
        }
    }

    public static void copyInText(InputStream is, OutputStream os, boolean isCloseIS, boolean isCloseOS) throws IOException {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(is, UTF_8));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, UTF_8));

            char[] buff = new char[CAPACITY];
            int readLen = -1;
            while ((readLen = bufferedReader.read(buff, 0, CAPACITY)) != -1) {
                bufferedWriter.write(buff, 0, readLen);
            }
            bufferedWriter.flush();

        } finally {
            quietlyClosed(isCloseIS ? bufferedReader : null, isCloseOS ? bufferedWriter : null);
        }
    }

    public static void quietlyClosed(AutoCloseable... autoCloseables) {
        if (autoCloseables == null) {
            return;
        }

        for (AutoCloseable autoCloseable : autoCloseables) {
            if (autoCloseable != null) {
                try {
                    autoCloseable.close();
                } catch (Exception e) {
                }
            }
        }
    }

}