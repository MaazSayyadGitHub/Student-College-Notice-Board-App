package com.maaz.collegeapp.UI.gallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.maaz.collegeapp.R;
import com.maaz.collegeapp.ZoomImageView;

import java.util.List;

public class galleryAdapter extends RecyclerView.Adapter<galleryAdapter.GalleryViewAdapter> {

    private Context context;
    private List<String> imageList;

    public galleryAdapter(Context context, List<String> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public GalleryViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_image, parent, false);
        return new GalleryViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewAdapter holder, int position) {

        Glide.with(context).load(imageList.get(position)).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // we just push image to ZoomActivity and zoom activity do all features.
                Intent intent = new Intent(context, ZoomImageView.class);
                intent.putExtra("image", imageList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class GalleryViewAdapter extends RecyclerView.ViewHolder {

        ImageView imageView;

        public GalleryViewAdapter(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
