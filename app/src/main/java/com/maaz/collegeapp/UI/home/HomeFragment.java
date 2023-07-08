package com.maaz.collegeapp.UI.home;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.maaz.collegeapp.MainActivity;
import com.maaz.collegeapp.R;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;


public class HomeFragment extends Fragment {


    private SliderLayout sliderLayout;
    private ImageView map;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // this is for ActionBar Name.
        ((MainActivity) getActivity())
                .setActionBarTitle("College App");

        map  = view.findViewById(R.id.map);
        sliderLayout = view.findViewById(R.id.slider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FANTRANSFORMATION);
        sliderLayout.setScrollTimeInSec(2);

        setSliderimages();

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });


        return view;
    }

    private void openMap(){

        Uri uri = Uri.parse("geo:0, 0?q=PIRENS Institute of Business Management and Administration");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }


    private void setSliderimages() {
        for (int i= 0; i<5; i++){
            DefaultSliderView sliderView = new DefaultSliderView(getContext());
            switch (i){
                case 0:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/my-college-app-7cf4e.appspot.com/o/sliderImages%2Fcollege1.jpg?alt=media&token=21ce21fe-b60e-431b-8b85-5174cf7159e1");
                    break;
                case 1:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/my-college-app-7cf4e.appspot.com/o/sliderImages%2Fcollege2.jpg?alt=media&token=4f547522-5afc-4c3b-8528-13677e290735");
                    break;
                case 2:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/my-college-app-7cf4e.appspot.com/o/sliderImages%2Fcollege3.jpeg?alt=media&token=2673c1da-f860-44ac-a3f6-70cd28c54275");
                    break;
                case 3:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/my-college-app-7cf4e.appspot.com/o/sliderImages%2Fcollege4.jpg?alt=media&token=fba545cb-cf2c-4a35-be19-53919789ef57");
                    break;
                case 4:
                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/my-college-app-7cf4e.appspot.com/o/sliderImages%2Fcollege5.jpg?alt=media&token=f0a5ef0d-e4c6-4295-879e-89a32e62eaab");
                    break;
            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderLayout.addSliderView(sliderView);
        }
    }
}