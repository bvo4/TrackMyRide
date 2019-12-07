package com.rohan.trackmyrideversion0;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rohan.trackmyrideversion0.fragments.DisplayFragment;
import com.rohan.trackmyrideversion0.fragments.TrackingFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_broadcast_location:
                    loadTrackingFragment();
                    return true;
                case R.id.navigation_track_location:
                    loadDisplayFragment();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadTrackingFragment();
    }

    private void loadTrackingFragment() {
        setTitle(R.string.title_send_location);
        TrackingFragment trackingFragment = new TrackingFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, trackingFragment)
                .commit();
    }

    private void loadDisplayFragment() {
        setTitle(R.string.title_track_location);
        DisplayFragment displayFragment = new DisplayFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, displayFragment)
                .commit();
    }
}
