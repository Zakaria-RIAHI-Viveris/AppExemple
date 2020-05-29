package com.viveris.appexemple.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viveris.appexemple.R;
import com.viveris.appexemple.model.BadgeCounts;
import com.viveris.appexemple.model.User;
import com.viveris.appexemple.ui.listener.IListUserListener;

import java.util.List;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder>{

    private List<User> users;
    private Context context;
    private IListUserListener listener;

    public UsersRecyclerAdapter(List<User> users, Context context, IListUserListener listener) {
        this.users = users;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(getItem(position), listener);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    private User getItem(int position) {
        return users.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView alias;
        private TextView goldNumber;
        private TextView silverNumber;
        private TextView bronzeNumber;
        private View item;

        ViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.view_item);
            alias = itemView.findViewById(R.id.text_view_user_alias);
            goldNumber = itemView.findViewById(R.id.text_view_gold_number);
            silverNumber = itemView.findViewById(R.id.text_view_silver_number);
            bronzeNumber = itemView.findViewById(R.id.text_view_bronze_number);
        }

        void setData(final User user, final IListUserListener listener) {
            if (user != null && listener != null) {
                alias.setText(user.getDisplayName());
                BadgeCounts badgeCounts = user.getBadgeCounts();
                goldNumber.setText(String.valueOf(badgeCounts.getGold()));
                silverNumber.setText(String.valueOf(badgeCounts.getSilver()));
                bronzeNumber.setText(String.valueOf(badgeCounts.getBronze()));
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onUserClicked(user);
                    }
                });
            }
        }
    }
}
