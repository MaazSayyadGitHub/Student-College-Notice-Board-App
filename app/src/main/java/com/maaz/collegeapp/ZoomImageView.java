package com.maaz.collegeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class ZoomImageView extends AppCompatActivity {

    private PhotoView photoView; // instead of ImageView used photoView.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image_view);

        photoView = findViewById(R.id.imageView);

        String image = getIntent().getStringExtra("image"); // get image

        Glide.with(this).load(image).into(photoView); // used glide for load image in zoom Activity.


    }
}