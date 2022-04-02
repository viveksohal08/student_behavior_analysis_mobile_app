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

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {

    private List<ListItem2> listItems;
    private Context context;


    public MyAdapter2(List<ListItem2> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ListItem2 listItem = listItems.get(position);
        holder.tvFaceID.setText("Roll Number: " + listItem.getFaceID());
        holder.tvEmotion.setText(listItem.getEmotion());
        holder.ivFace.setImageBitmap(listItem.getImage());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvFaceID, tvEmotion;
        public ImageView ivFace;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            tvFaceID = (TextView) itemView.findViewById(R.id.tvFaceID);
            tvEmotion = (TextView) itemView.findViewById(R.id.tvEmotion);
            ivFace = (ImageView) itemView.findViewById(R.id.ivFace);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
