package com.viveris.appexemple.network.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.viveris.appexemple.model.Users;
import com.viveris.appexemple.ui.listener.IListUserListener;

public class UsersBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_RESP = "com.viveris.appexemple.intent.action.LIST_TO_DISPLAY";
    private IListUserListener listener;

    public UsersBroadcastReceiver(IListUserListener iListUserListener) {
        this.listener = iListUserListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Users users = (Users) intent.getSerializableExtra(NetworkService.BUNDLE_RESULT_USERS);
        if (users != null) {
            listener.displayResult(users);
        } else {
            listener.displayFailure();
        }
    }
}
