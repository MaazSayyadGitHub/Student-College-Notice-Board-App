package com.maaz.collegeapp.UI.about;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.maaz.collegeapp.MainActivity;
import com.maaz.collegeapp.R;

import java.util.ArrayList;
import java.util.List;


public class AboutFragment extends Fragment {

    private ViewPager viewPager;
    private branchAdapter adapter;
    private List<BranchModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        // this is for ActionBar Name.
        ((MainActivity) getActivity())
                .setActionBarTitle("About College");

        list = new ArrayList<>(); // initialize List here

        // adding ViewPager Data Manually

        list.add(new BranchModel(R.drawable.ic_computer, "Computer Science", "Computer Science and Engineering started in year 2015,Computer Science and Engineering started in year 2015,Computer Science and Engineering started in year 2015 ...."));
        list.add(new BranchModel(R.drawable.ic_mechanical, "Mechanical Production", "Mechanical Production and Engineering started in year 2015. Computer Science and Engineering started in year 2015,Computer Science and Engineering started in year 2015..."));
        list.add(new BranchModel(R.drawable.ic_mechanical, "Physics & Chem", "Physics Production and Engineering started in year 2015. Computer Science and Engineering started in year 2015,Computer Science and Engineering started in year 2015..."));

        adapter = new branchAdapter(getContext(), list);

        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        ImageView imageView = view.findViewById(R.id.aboutImage);

        Glide.with(getContext())
                .load("https://firebasestorage.googleapis.com/v0/b/my-college-app-7cf4e.appspot.com/o/sliderImages%2Fcollege1.jpg?alt=media&token=21ce21fe-b60e-431b-8b85-5174cf7159e1")
                .into(imageView);

        return view;
    }

}