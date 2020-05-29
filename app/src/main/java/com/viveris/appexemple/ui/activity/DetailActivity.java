package com.viveris.appexemple.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.viveris.appexemple.R;
import com.viveris.appexemple.model.BadgeCounts;
import com.viveris.appexemple.model.User;
import com.viveris.appexemple.utils.Utils;

public class DetailActivity extends AppCompatActivity {

    public static final String BUNDLE_EXTRA_USER = "BUNDLE_EXTRA_USER";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            User user = (User) extras.getSerializable(BUNDLE_EXTRA_USER);
            if (user != null) {
                initializeView(user);
            }
        }
    }

    private void initializeView(final User user) {
        TextView aliasTv = findViewById(R.id.text_view_user_alias);
        TextView locationTv = findViewById(R.id.text_view_user_location);
        TextView creationDateTv = findViewById(R.id.text_view_user_creation_date);
        Button websiteBtn = findViewById(R.id.text_view_user_website);
        TextView goldTv = findViewById(R.id.text_view_gold_badge);
        TextView silverTv = findViewById(R.id.text_view_silver_badge);
        final TextView bronzeTv = findViewById(R.id.text_view_bronze_badge);

        aliasTv.setText(user.getDisplayName());
        locationTv.setText(user.getLocation());
        creationDateTv.setText(getString(R.string.creation_date, Utils.dateToString(user.getCreationDate())));
        BadgeCounts badgeCounts = user.getBadgeCounts();
        goldTv.setText(String.valueOf(badgeCounts.getGold()));
        silverTv.setText(String.valueOf(badgeCounts.getSilver()));
        bronzeTv.setText(String.valueOf(badgeCounts.getBronze()));
        websiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBrowser(user);
            }
        });
    }

    private void openBrowser(User user) {
        Uri uri = Uri.parse(user.getWebsiteUrl());
        Intent browserIntent = new Intent();
        browserIntent.setAction(Intent.ACTION_VIEW);
        browserIntent.setData(uri);
        startActivity(browserIntent);
    }
}
