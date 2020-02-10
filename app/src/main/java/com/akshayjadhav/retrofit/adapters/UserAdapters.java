package com.akshayjadhav.retrofit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akshayjadhav.retrofit.R;
import com.akshayjadhav.retrofit.models.User;

import java.util.List;

public class UserAdapters extends RecyclerView.Adapter<UserAdapters.UserViewHolder> {

    private Context mContxt;
    private List<User> userList;

    public UserAdapters(Context mContxt, List<User> userList) {
        this.mContxt = mContxt;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContxt).inflate(R.layout.recyclerview_users,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        User user = userList.get(position);
        holder.tvEmail.setText(user.getEmail());
        holder.tvName.setText(user.getName());
        holder.tvSchool.setText(user.getSchool());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        TextView tvEmail , tvName, tvSchool;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvName = itemView.findViewById(R.id.tvName);
            tvSchool = itemView.findViewById(R.id.tvSchool);
        }
    }
}
