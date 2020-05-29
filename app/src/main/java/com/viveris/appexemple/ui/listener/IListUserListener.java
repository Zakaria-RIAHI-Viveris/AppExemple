package com.viveris.appexemple.ui.listener;

import com.viveris.appexemple.model.User;
import com.viveris.appexemple.model.Users;

public interface IListUserListener {

    void onUserClicked(User user);
    void displayFailure();
    void displayResult(Users body);

}
