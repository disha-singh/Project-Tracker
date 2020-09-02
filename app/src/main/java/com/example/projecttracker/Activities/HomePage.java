package com.example.projecttracker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.projecttracker.Fragments.HomeFragment;
import com.example.projecttracker.Fragments.MessageFragment;
import com.example.projecttracker.Fragments.NotificationsFragment;
import com.example.projecttracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;
    private Toolbar toolbar;
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment fragment = null;
                    switch (item.getItemId()) {

                        case R.id.bottom_profile:
                            fragment = new Fragment();
                            break;

                        case R.id.bottom_home:
                            fragment = new HomeFragment();
                            break;


                        case R.id.bottom_updates:
                            fragment = new NotificationsFragment();
                            break;


                        case R.id.bottom_chats:
                            fragment = new MessageFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

                    return true;
                }


            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        firebaseAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.topNavBar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logoutBtn: {
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(HomePage.this, LoginActivity.class));
    }
}


