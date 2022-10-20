package com.maaz.collegeapp.UI.faculty;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maaz.collegeapp.MainActivity;
import com.maaz.collegeapp.R;

import java.util.ArrayList;
import java.util.List;


public class FacultyFragment extends Fragment {


    private RecyclerView csDepartment, mechanicalDepartment, physicsDepartment, chemistryDepartment;
    private LinearLayout csNoData, mechanicalNoData, physicsNoData, chemistryNoData;

    private List<TeacherData> list1, list2, list3, list4;
    private DatabaseReference reference, DbRef;

    private TeacherAdapters adapters;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faculty, container, false);

        // this is for ActionBar Name.
        ((MainActivity) getActivity())
                .setActionBarTitle("College Faculty");

        csDepartment = view.findViewById(R.id.CsDepartment);
        mechanicalDepartment = view.findViewById(R.id.mechanicalDepartment);
        physicsDepartment = view.findViewById(R.id.physicsDepartment);
        chemistryDepartment = view.findViewById(R.id.chemistryDepartment);

        csNoData = view.findViewById(R.id.csNoData);
        mechanicalNoData = view.findViewById(R.id.mechanicalNoData);
        physicsNoData = view.findViewById(R.id.physicsNoData);
        chemistryNoData = view.findViewById(R.id.chemistryNoData);

        reference = FirebaseDatabase.getInstance().getReference().child("Teacher");

        csDepartment();
        mechanicalDepartment();
        physicsDepartment();
        chemistryDepartment();


        return view;
    }

    private void csDepartment() {
        //DbRef is another variable which contain reference variables child. which is (Computer Science).
        DbRef = reference.child("Computer Science");
        DbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                if (!snapshot.exists()){    // if data is not exists here then visibility csNoData will display
                    csNoData.setVisibility(View.VISIBLE);
                    csDepartment.setVisibility(View.GONE);   // and csDepartment will not display
                } else {
                    csNoData.setVisibility(View.GONE);         // if data exists then csDepartment will display
                    csDepartment.setVisibility(View.VISIBLE);   // and csNoData Visibility Gone.
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        list1.add(data);
                    }
                    csDepartment.setHasFixedSize(true);     // adapter set
                    csDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapters = new TeacherAdapters(list1, getContext());  // this extra category for access
                    csDepartment.setAdapter(adapters);                                                      // category in update teacher data.
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mechanicalDepartment() {
        //DbRef is another variable which contain reference variables child.  which is (Mechanical).
        DbRef = reference.child("Mechanical");
        DbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();
                if (!snapshot.exists()){    // if data is not exists here then visibility csNoData will display
                    mechanicalNoData.setVisibility(View.VISIBLE);
                    mechanicalDepartment.setVisibility(View.GONE);   // and csDepartment will not display
                } else {
                    mechanicalNoData.setVisibility(View.GONE);         // if data exists then csDepartment will display
                    mechanicalDepartment.setVisibility(View.VISIBLE);   // and csNoData Visibility Gone.
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        list2.add(data);
                    }
                    mechanicalDepartment.setHasFixedSize(true);     // adapter set
                    mechanicalDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapters = new TeacherAdapters(list2, getContext());
                    mechanicalDepartment.setAdapter(adapters);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void physicsDepartment() {
        DbRef = reference.child("Physics");
        DbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3 = new ArrayList<>();
                if (!snapshot.exists()){    // if data is not exists here then visibility csNoData will display
                    physicsNoData.setVisibility(View.VISIBLE);
                    physicsDepartment.setVisibility(View.GONE);   // and csDepartment will not display
                } else {
                    physicsNoData.setVisibility(View.GONE);         // if data exists then csDepartment will display
                    physicsDepartment.setVisibility(View.VISIBLE);   // and csNoData Visibility Gone.
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        list3.add(data);
                    }
                    physicsDepartment.setHasFixedSize(true);     // adapter set
                    physicsDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapters = new TeacherAdapters(list3, getContext());
                    physicsDepartment.setAdapter(adapters);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void chemistryDepartment() {
        DbRef = reference.child("Chemistry");
        DbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4 = new ArrayList<>();
                if (!snapshot.exists()){    // if data is not exists here then visibility csNoData will display
                    chemistryNoData.setVisibility(View.VISIBLE);
                    chemistryDepartment.setVisibility(View.GONE);   // and csDepartment will not display
                } else {
                    chemistryNoData.setVisibility(View.GONE);         // if data exists then csDepartment will display
                    chemistryDepartment.setVisibility(View.VISIBLE);   // and csNoData Visibility Gone.
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        list4.add(data);
                    }
                    chemistryDepartment.setHasFixedSize(true);     // adapter set
                    chemistryDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapters = new TeacherAdapters(list4, getContext());
                    chemistryDepartment.setAdapter(adapters);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}