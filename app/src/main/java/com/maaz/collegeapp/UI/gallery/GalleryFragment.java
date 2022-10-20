package com.maaz.collegeapp.UI.gallery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maaz.collegeapp.MainActivity;
import com.maaz.collegeapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GalleryFragment extends Fragment {


    private RecyclerView convoRecyclerView , otherRecyclerView;
    galleryAdapter adapter;

    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        // this is for ActionBar Name.
        ((MainActivity) getActivity())
                .setActionBarTitle("College Gallery");

        convoRecyclerView = view.findViewById(R.id.convocationRecyclerView);
        otherRecyclerView = view.findViewById(R.id.othersRecyclerView);

        reference = FirebaseDatabase.getInstance().getReference().child("gallery");

        getConvoImages();  // convocation Category images.

        getOtherImages(); // Other Category images.

        return view;
    }

    private void getConvoImages() {

        reference.child("Convocation").addValueEventListener(new ValueEventListener() {

            List<String> convoImagelist = new ArrayList<>();   // all data will come into this.
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){   /// then it will be access by for loop like this..

                    String data = (String) snapshot.getValue();      /// then collect all data in data variable.
                    convoImagelist.add(data);                         // and then add.
                }

                adapter = new galleryAdapter(getContext(), convoImagelist);
                convoRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                convoRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOtherImages() {

        reference.child("Other Events").addValueEventListener(new ValueEventListener() {

            List<String> otherImagelist = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    String data = (String) snapshot.getValue();
                    otherImagelist.add(data);
                }

                adapter = new galleryAdapter(getContext(), otherImagelist);
                otherRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                otherRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}