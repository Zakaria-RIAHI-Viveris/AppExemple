package com.viveris.appexemple.network.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.viveris.appexemple.AppExempleApplication;
import com.viveris.appexemple.model.Users;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NetworkService extends IntentService {

    public static final String BUNDLE_RESULT_USERS = "BUNDLE_RESULT_USERS";
    private Users users;

    public NetworkService() {
        super("NetworkService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        fetchUserFromNetwork((AppExempleApplication) getApplication());
        returnResultToCaller();
    }

    private void fetchUserFromNetwork(AppExempleApplication application) {
        Gson gson = application.getGson();
        try {
            URL url = new URL(AppExempleApplication.BASE_URL + "users?order=desc&sort=reputation&site=stackoverflow");
            HttpsURLConnection myConnection = (HttpsURLConnection) url.openConnection();
            InputStream responseBody = myConnection.getInputStream();
            String body = IOUtils.toString(responseBody, "UTF-8");
            users = gson.fromJson(body, Users.class);
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), "IOException", e);
        }
    }

    private void returnResultToCaller() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(UsersBroadcastReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(BUNDLE_RESULT_USERS, users);
        sendBroadcast(broadcastIntent);
    }
}
