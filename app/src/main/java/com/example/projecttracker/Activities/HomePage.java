package com.example.projecttracker.Activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.projecttracker.Fragments.HomeFragment;
import com.example.projecttracker.Fragments.MessageFragment;
import com.example.projecttracker.Fragments.NotificationsFragment;
import com.example.projecttracker.Fragments.ProfileFragment;
import com.example.projecttracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
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

    public class TopToolBar extends AppCompatActivity {

        private Toolbar toolbar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home_page);
            toolbar = findViewById(R.id.topNav);

            setSupportActionBar(toolbar);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.top_nav_bar, menu);
            return true;
        }

        public void Logout(MenuItem item) {
            mAuth.signOut();

        }
    }

}
