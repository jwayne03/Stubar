package com.example.stubar.utils.decode;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class Decode {

    /**
     * Static method to decode to UTF-8
     *
     * @param name String
     * @return String
     */
    public static String decodeUTF8(String name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new String(name.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        }
        return "";
    }

    /**
     * Static method to parse the bitmap (Image) to String
     *
     * @param userImage1 String
     * @return String
     */
    public static String bitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
}