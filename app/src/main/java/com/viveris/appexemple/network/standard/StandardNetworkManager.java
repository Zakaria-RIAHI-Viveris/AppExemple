package com.viveris.appexemple.network.standard;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.viveris.appexemple.AppExempleApplication;
import com.viveris.appexemple.model.Users;
import com.viveris.appexemple.network.INetworkManager;
import com.viveris.appexemple.ui.listener.IListUserListener;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class StandardNetworkManager implements INetworkManager {

    private IListUserListener listener;

    public StandardNetworkManager(IListUserListener listener) {
        this.listener = listener;
    }

    public void fetchUserFromNetwork(AppExempleApplication application) {
        Gson gson = application.getGson();
        new FetchUserTask(gson, listener).execute();
    }

    private static class FetchUserTask extends AsyncTask<Void, Void, Users> {

        private Gson gson;
        private IListUserListener listener;

        FetchUserTask(Gson gson, IListUserListener listener) {
            this.gson = gson;
            this.listener = listener;
        }

        @Override
        protected Users doInBackground(Void... voids) {
            Users users = null;
            try {
                URL url = new URL(AppExempleApplication.BASE_URL + "users?order=desc&sort=reputation&site=stackoverflow");
                HttpsURLConnection myConnection = (HttpsURLConnection) url.openConnection();
                InputStream responseBody = myConnection.getInputStream();
                String body = IOUtils.toString(responseBody, "UTF-8");
                users = gson.fromJson(body, Users.class);
            } catch (IOException e) {
                Log.e(this.getClass().getSimpleName(), "IOException", e);
            }
            return users;
        }

        @Override
        protected void onPostExecute(Users users) {
            super.onPostExecute(users);
            if (users != null) {
                listener.displayResult(users);
            } else {
                listener.displayFailure();
            }
        }
    }
}
