package com.example.stubar.utils.decode;

import android.os.Build;

import java.nio.charset.StandardCharsets;

public class Decode {

    public static String decodeUTF8(String name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new String(name.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        }
        return "";
    }
}
