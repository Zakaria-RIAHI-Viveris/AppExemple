package com.viveris.appexemple.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Utils {

    public static String dateToString(Integer dateSec) {
        String res = null;
        if (dateSec != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateSec * 1000L);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            res = dateFormat.format(calendar.getTime());
        }
        return res;
    }
}
