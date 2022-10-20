package com.maaz.collegeapp.EBooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maaz.collegeapp.R;

import java.util.ArrayList;
import java.util.List;

public class EBookActivity extends AppCompatActivity {

    private RecyclerView EBookRecyclerView;
    private DatabaseReference reference;
    private List<EBookData> list;
    private EBookAdapter adapter;

    private ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayout shimmerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_book);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // it is for back arrow in Activity.
        getSupportActionBar().setTitle("EBooks");

        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        shimmerLayout = findViewById(R.id.shimmerLayout);

        EBookRecyclerView = findViewById(R.id.EBookRecyclerView);
        reference = FirebaseDatabase.getInstance().getReference().child("pdf");

        getData();


    }

    private void getData() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // it is new ArrayList define.
                list = new ArrayList<>();
                // it will take one by one and display by the help of for loop.
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    // snapshot match with EBookData attributes.
                    EBookData data = snapshot1.getValue(EBookData.class);
                    list.add(data);  // and then add to list.
                }

                // it is take the context and list from adapter class into adapter variable.
                adapter = new EBookAdapter(EBookActivity.this, list);
                // set LayoutManager
                EBookRecyclerView.setLayoutManager(new LinearLayoutManager(EBookActivity.this));
                EBookRecyclerView.setAdapter(adapter);   // and set on recyclerView.
                //set Shimmer Layout
                shimmerFrameLayout.stopShimmer();
                shimmerLayout.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EBookActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {   // it is not mandatory
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    protected void onResume() {    // it is not mandatory
        shimmerFrameLayout.startShimmer();
        super.onResume();
    }
}