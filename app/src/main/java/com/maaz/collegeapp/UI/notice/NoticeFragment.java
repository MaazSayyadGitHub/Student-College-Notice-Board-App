package com.maaz.collegeapp.UI.notice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maaz.collegeapp.MainActivity;
import com.maaz.collegeapp.R;

import java.util.ArrayList;


public class NoticeFragment extends Fragment {

    private RecyclerView deleteNoticeRecyclerView;
    private ProgressBar progressBar;
    private ArrayList<NoticeData> list;
    private NoticeAdapter adapter;

    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        // this is for ActionBar Name.
        ((MainActivity) getActivity())
                .setActionBarTitle("College Notice");

        deleteNoticeRecyclerView = view.findViewById(R.id.deleteNoticeRecyclerView);
        progressBar =view.findViewById(R.id.progressBar);

        reference = FirebaseDatabase.getInstance().getReference().child("Notice");

        deleteNoticeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        deleteNoticeRecyclerView.setHasFixedSize(true);

        getNotice();

        return view;
    }


    private void getNotice() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();  // add data into list.
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    NoticeData data = snapshot1.getValue(NoticeData.class);
                    list.add(0,data);  // Whatever New Data will come in Notice Activity will be on (First) Top.
                }

                adapter = new NoticeAdapter(getContext(), list);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                deleteNoticeRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}