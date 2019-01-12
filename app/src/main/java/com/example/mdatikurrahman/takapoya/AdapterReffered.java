package com.example.mdatikurrahman.takapoya;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterReffered extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private View view;
    List<Reffered_list> friendList;
    private Context mcontext;

    public AdapterReffered(Context mCtx, List<Reffered_list> match_lists) {
        this.mcontext = mCtx;
        this.friendList = match_lists;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.referred_rwo_design, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    String testing= String.valueOf(friendList.get(position).getI());

        ((UserViewHolder) holder).redId.setText(""+friendList.get(position).getI());
        ((UserViewHolder) holder).refName.setText(friendList.get(position).getNameStr());
    }

    @Override
    public int getItemCount() {
        return friendList == null ? 0 : friendList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView  redId,refName;

        public UserViewHolder(View itemView) {
            super(itemView);


            redId = itemView.findViewById(R.id.refId);
            refName = itemView.findViewById(R.id.refName);



        }
    }

}
