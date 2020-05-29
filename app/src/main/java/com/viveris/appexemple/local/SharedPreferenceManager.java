package com.viveris.appexemple.local;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public final class SharedPreferenceManager {

    public static final String PREF_KEY_ALIAS = "PREF_KEY_ALIAS";

    public static SharedPreferences getSharedAppSharedPreference(Application application) {
        return application.getSharedPreferences("AppExemplePref", Context.MODE_PRIVATE);
    }
}
