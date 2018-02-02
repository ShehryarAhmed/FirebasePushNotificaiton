package com.technexia.user.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by User on 2/1/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<User>  userList;
    Context mContext;

    public RecyclerAdapter(Context context,List<User> users) {
        userList = users;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_users_list,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText(userList.get(position).getName());
        CircleImageView imageView = holder.imageView;
        Glide.with(mContext).load(userList.get(position).getImageUri()).into(imageView);

        final String userId = userList.get(position).userId;

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(mContext,SendActivity.class);
                sendIntent.putExtra("user_id",userId);
                sendIntent.putExtra("user_name",userList.get(position).getName());
                mContext.startActivity(sendIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View mView;
        private CircleImageView imageView;
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            imageView = (CircleImageView) mView.findViewById(R.id.single_users_image);
            name = (TextView) mView.findViewById(R.id.single_users_name);
        }


    }

}

