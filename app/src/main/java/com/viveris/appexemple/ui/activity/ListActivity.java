package com.viveris.appexemple.ui.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.viveris.appexemple.AppExempleApplication;
import com.viveris.appexemple.R;
import com.viveris.appexemple.local.SharedPreferenceManager;
import com.viveris.appexemple.model.User;
import com.viveris.appexemple.model.Users;
import com.viveris.appexemple.network.INetworkManager;
import com.viveris.appexemple.network.retrofit.RetrofitNetworkManager;
import com.viveris.appexemple.network.service.NetworkService;
import com.viveris.appexemple.network.service.UsersBroadcastReceiver;
import com.viveris.appexemple.ui.adapter.UsersRecyclerAdapter;
import com.viveris.appexemple.ui.listener.IListUserListener;
import com.viveris.appexemple.ui.view.ProgressBarManager;

public class ListActivity extends AppCompatActivity implements IListUserListener {

    private RecyclerView usersListView;
    private FrameLayout progressBarHolder;
    private ProgressBarManager progressBarManager;
    private EditText etMyUserAlias;
    private INetworkManager networkManager;
    private UsersBroadcastReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        usersListView = findViewById(R.id.list_view_users);
        progressBarHolder = findViewById(R.id.progressBarHolder);
        etMyUserAlias = findViewById(R.id.et_my_user_alias);
        Button btnSaveAlias = findViewById(R.id.btn_save_alias);
        btnSaveAlias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMyUserAlias();
            }
        });
        progressBarManager = new ProgressBarManager();
        networkManager = new RetrofitNetworkManager(this);
        initializeData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(UsersBroadcastReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiver, filter);
    }

    @Override
    public void onUserClicked(User user) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra(DetailActivity.BUNDLE_EXTRA_USER, user);
        startActivity(detailIntent);
    }

    @Override
    public void displayFailure() {
        progressBarManager.onLoaderStateChange(false, progressBarHolder);
        Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayResult(Users body) {
        progressBarManager.onLoaderStateChange(false, progressBarHolder);
        UsersRecyclerAdapter adapter = new UsersRecyclerAdapter(body.getUserList(), getApplicationContext(), this);
//        UsersAdapter adapter = new UsersAdapter(body.getUserList(), getApplicationContext(), this);
        usersListView.setHasFixedSize(true);
        usersListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        usersListView.setAdapter(adapter);
    }

    private void initializeData() {
        String myAlias = SharedPreferenceManager.getSharedAppSharedPreference(getApplication()).getString(SharedPreferenceManager.PREF_KEY_ALIAS, "");
        if (!TextUtils.isEmpty(myAlias)) {
            etMyUserAlias.setText(myAlias);
        }
        progressBarManager.onLoaderStateChange(true, progressBarHolder);
        fetchUsers();
//        fetchUsersWithService();
    }

    private void saveMyUserAlias() {
        String myUserAlias = etMyUserAlias.getText().toString();
        if (!TextUtils.isEmpty(myUserAlias)) {
            SharedPreferences.Editor editor = SharedPreferenceManager.getSharedAppSharedPreference(getApplication()).edit();
            editor.putString(SharedPreferenceManager.PREF_KEY_ALIAS, myUserAlias).apply();
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchUsers() {
        AppExempleApplication application = (AppExempleApplication) getApplication();
        networkManager.fetchUserFromNetwork(application);
    }

    private void fetchUsersWithService() {
        // on initialise notre broadcast
        receiver = new UsersBroadcastReceiver(this);
        // on lance le service
        Intent msgIntent = new Intent(this, NetworkService.class);
        startService(msgIntent);
    }
}
