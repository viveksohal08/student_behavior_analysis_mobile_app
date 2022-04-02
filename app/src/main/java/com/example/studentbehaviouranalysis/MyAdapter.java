package com.example.studentbehaviouranalysis;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vivek on 03-04-2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;


    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ListItem listItem = listItems.get(position);
        holder.tvEmotion.setText(listItem.getEmotion());
        holder.ivFace.setImageBitmap(listItem.getImage());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvEmotion;
        public ImageView ivFace;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            tvEmotion = (TextView) itemView.findViewById(R.id.tvEmotion);
            ivFace = (ImageView) itemView.findViewById(R.id.ivFace);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
