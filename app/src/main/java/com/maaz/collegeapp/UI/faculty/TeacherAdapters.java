package com.maaz.collegeapp.UI.faculty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.maaz.collegeapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeacherAdapters extends RecyclerView.Adapter<TeacherAdapters.TeacherViewHolder> {

    private List<TeacherData> list;
    private Context context;


    public TeacherAdapters(List<TeacherData> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.teacher_item_layout, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {

        TeacherData item = list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());

        try {
            Picasso.get().load(item.getImage()).placeholder(R.drawable.man).into(holder.imageView);  // placeholder for default image
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class TeacherViewHolder extends RecyclerView.ViewHolder{

        TextView name, email, post;
//        Button Teacher;
        ImageView imageView;

        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.teacherName);
            email = itemView.findViewById(R.id.teacherEmail);
            post = itemView.findViewById(R.id.teacherPost);
//            Teacher = itemView.findViewById(R.id.teacherUpdate);
            imageView = itemView.findViewById(R.id.teacherImg);
        }
    }
}
