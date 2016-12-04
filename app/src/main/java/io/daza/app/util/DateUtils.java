package io.daza.app.util;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.daza.app.R;

import static android.text.format.DateUtils.MINUTE_IN_MILLIS;

public class DateUtils {

    public static String toTimeAgo(Context context, String dateStr) {
        final String dateFormatPattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern);
        // 转换字符串为Date
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            return dateStr;
        }
        return toTimeAgo(context, date.getTime());
    }

    public static String toTimeAgo(Context context, long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }
        Calendar ago = Calendar.getInstance();
        ago.setTimeInMillis(time);
        long duration = Math.abs(System.currentTimeMillis() - ago.getTimeInMillis());
        if (duration < MINUTE_IN_MILLIS) {
            return context.getString(R.string.just_now);
        }
        return String.valueOf(android.text.format.DateUtils.getRelativeTimeSpanString(ago.getTimeInMillis()));
    }
}
