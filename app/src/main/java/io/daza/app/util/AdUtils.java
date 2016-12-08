package io.daza.app.util;

import android.content.Context;
import android.provider.Settings;

public class AdUtils {
    private static String sTestDeviceId;

    public static String getTestDeviceId(Context context) {
        if (sTestDeviceId == null) {
            String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            sTestDeviceId = MD5(androidId).toUpperCase();
        }
        return sTestDeviceId;
    }

    private static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
