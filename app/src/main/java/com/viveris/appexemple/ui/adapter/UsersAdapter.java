package com.viveris.appexemple.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viveris.appexemple.R;
import com.viveris.appexemple.model.BadgeCounts;
import com.viveris.appexemple.model.User;
import com.viveris.appexemple.ui.listener.IListUserListener;

import java.util.List;

public class UsersAdapter extends BaseAdapter {

    private List<User> users;
    private Context context;
    private IListUserListener listener;

    public UsersAdapter(List<User> users, Context context, IListUserListener listener) {
        this.users = users;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        UserViewHolder convertedView;
        if (view == null) {
            convertedView = new UserViewHolder(context);
        } else {
            convertedView = (UserViewHolder) view;
        }
        convertedView.setInfos(getItem(i), listener);
        return convertedView;
    }

    private class UserViewHolder extends LinearLayout {

        private TextView alias;
        private TextView goldNumber;
        private TextView silverNumber;
        private TextView bronzeNumber;
        private View item;

        public UserViewHolder(Context context) {
            super(context);
            initView();
        }

        private void initView() {
            LayoutInflater.from(getContext()).inflate(R.layout.item_user, this);
            item = findViewById(R.id.view_item);
            alias = findViewById(R.id.text_view_user_alias);
            goldNumber = findViewById(R.id.text_view_gold_number);
            silverNumber = findViewById(R.id.text_view_silver_number);
            bronzeNumber = findViewById(R.id.text_view_bronze_number);
        }

        public void setInfos(final User user, final IListUserListener listener) {
            if (user != null && listener != null) {
                alias.setText(user.getDisplayName());
                BadgeCounts badgeCounts = user.getBadgeCounts();
                goldNumber.setText(String.valueOf(badgeCounts.getGold()));
                silverNumber.setText(String.valueOf(badgeCounts.getSilver()));
                bronzeNumber.setText(String.valueOf(badgeCounts.getBronze()));
                item.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onUserClicked(user);
                    }
                });
            }
        }


    }
}
