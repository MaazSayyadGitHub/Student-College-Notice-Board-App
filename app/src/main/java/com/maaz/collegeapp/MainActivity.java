package com.maaz.collegeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.maaz.collegeapp.Auth.LoginActivity;
import com.maaz.collegeapp.EBooks.EBookActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    /* Pushing Or Enabling Project with Github :-

     first Goto VCS enable git.
     then Goto Project section
     and then go to Git
     then do first add+ to VCS
     then after commit directory
     then after that do push
     for push add repository url
     and then push it.

     */

    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    // SharedPreference For Color Themes
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private int checkedItem;
    private String selected;
    private final String CHECKED_ITEM = "checked_item"; // to access SP.

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_View);

        // Navigation Drawer Toggle
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle); // set that toggle to drawer layout
        toggle.syncState(); // it will check that if (nav view is open then it will show close btn other wise open btn)

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set back btn

        // navigation drawer
        navigationView.setNavigationItemSelectedListener(this); // we already implemented NavigationView.OnNavigationItemSelectedListener to this class

        // bottom navigation
        navController = Navigation.findNavController(this, R.id.frame_layout);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // initialize SP.
        sharedPreferences = this.getSharedPreferences("Themes", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Setting Theme Here
        switch (getCheckedItem()){
            case 0:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case 2:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }

    }

    // this is for option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);

        return true;
    }

    // this is for toggle click... toggle is three line on navigation Drawer.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // navigation toggle code
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        // logout code
        if (item.getItemId() == R.id.logout){
            auth.signOut();
            OpenLoginActivity();
        }

        return true;
    }

    private void OpenLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_dev:
                Toast.makeText(this, "Developer", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_video:
                Toast.makeText(this, "Video Lectures", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_rate:
                Toast.makeText(this, "Rate Us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_ebook:
                // it will take to EBook Activity.
                startActivity(new Intent(this, EBookActivity.class));
                break;
            case R.id.navigation_theme:
                showDialog(); // call for Change Theme
                break;
            case R.id.navigation_website:
                Toast.makeText(this, "Website", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void showDialog() {

        String[] themes = this.getResources().getStringArray(R.array.Theme); // getting types of theme array from string.

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Themes");
        builder.setSingleChoiceItems(R.array.Theme, getCheckedItem(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // i is the value of what we have checked/selected from dialog.
                // put that into.
                selected = themes[i];
                checkedItem = i;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (selected == null) {
                    // we will put default on it. if not selected
                    selected = themes[i];
                    checkedItem = i;
                }

                // Setting Theme Here
                switch (selected){
                    case "System Default":
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        break;
                    case "Dark":
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                    case "Light":
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                }
                setCheckedItem(checkedItem); // set this in SharedPreference
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        // display dialog.
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    // set and get Checked Items For Themes
    private int getCheckedItem(){
        return sharedPreferences.getInt(CHECKED_ITEM, 0);
    }
    private void setCheckedItem(int i){
        editor.putInt(CHECKED_ITEM, i);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        // it is for , if navigation drawer is open then onBacPress first Navigation Drawer will Close
        // then App will Close
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed(); // super is used for methods Functionality.
        }

    }

    //it is for fragment action bar. we will get this getSupportActionBar and access in fragment.
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


}