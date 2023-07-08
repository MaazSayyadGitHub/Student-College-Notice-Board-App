package com.maaz.collegeapp.UI.notice;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.maaz.collegeapp.R;
import com.maaz.collegeapp.ZoomImageView;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {

    private Context context;
    private ArrayList<NoticeData> list;

    public NoticeAdapter(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newsfeed_itemlayout, parent, false);
        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, int position) {

        NoticeData item = list.get(position);

        holder.NoticeTitle.setText(item.getTitle());
        holder.date.setText(item.getDate());
        holder.time.setText(item.getTime());

        try {
            if (item.getImage() != null)
                Glide.with(context).load(item.getImage()).into(holder.NoticeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.NoticeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ZoomImageView.class);
                intent.putExtra("image", item.getImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {

        private TextView NoticeTitle, date, time;
        private ImageView NoticeImage;

        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);

            NoticeTitle = itemView.findViewById(R.id.NoticeTitle);
            NoticeImage = itemView.findViewById(R.id.NoticeImage);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }
}
