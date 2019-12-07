package com.rohan.trackmyrideversion0.fragments;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rohan.trackmyrideversion0.R;
import com.rohan.trackmyrideversion0.TrackerService;

import static android.content.Context.LOCATION_SERVICE;

public class TrackingFragment extends Fragment {
    private static final String TAG = TrackingFragment.class.getSimpleName();

    private TextView mTvBroadcastingStatus;
    private Button mBtnToggle;
    private int PERMISSIONS_REQUEST = 1;

    public TrackingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracking, container, false);
        mTvBroadcastingStatus = view.findViewById(R.id.tv_broadcasting_status);
        mBtnToggle = view.findViewById(R.id.btn_broadcasting_status_toggle);
        if (isMyServiceRunning(TrackerService.class)) {
            loadState(true);
            getContext().registerReceiver(stopReceiver, new IntentFilter("stop"));
        } else {
            loadState(false);
        }
        mBtnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleState();
            }
        });
        return view;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void toggleState() {
        if (isMyServiceRunning(TrackerService.class)) {
            loadState(false);
            getContext().unregisterReceiver(stopReceiver);
            getContext().stopService(new Intent(getContext(), TrackerService.class));
        } else {
            if (startTrackingService()) {
                loadState(true);
            }
        }
    }

    private boolean startTrackingService() {
        LocationManager lm = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getContext(), "Please enable location service to start broadcasting location", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            int permission = ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION);
            if (permission == PackageManager.PERMISSION_GRANTED) {
                getContext().startService(new Intent(getContext(), TrackerService.class));
                getContext().registerReceiver(stopReceiver, new IntentFilter("stop"));
                return true;
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST);
                return false;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.length >= 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            toggleState();
        } else {
            Toast.makeText(getContext(), "Location permission needed for broadcasting location", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadState(boolean isBroadcasting) {
        if (isBroadcasting) {
            mTvBroadcastingStatus.setText(R.string.broadcasting_true);
            mBtnToggle.setText(R.string.stop_broadcasting_location);
        } else {
            mTvBroadcastingStatus.setText(R.string.broadcasting_false);
            mBtnToggle.setText(R.string.start_broadcasting_location);
        }
    }

    protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "received stop broadcast");
            toggleState();
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            getContext().unregisterReceiver(stopReceiver);
        } catch (Exception ignored) {

        }
    }
}
