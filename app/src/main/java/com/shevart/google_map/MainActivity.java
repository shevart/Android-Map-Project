package com.shevart.google_map;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shevart.google_map.location.UserLocation;
import com.shevart.google_map.location.UserLocationManager;
import com.shevart.google_map.ui.base.AbsActivity;
import com.shevart.google_map.util.PermissionsUtils;
import com.shevart.google_map.util.SystemUtils;
import com.shevart.google_map.util.UiNotificationsUtils;
import com.shevart.google_map.util.UiUtil;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AbsActivity implements OnMapReadyCallback,
        UserLocationManager.LocationEventsListener {
    private static final int LOCATION_PERMISSION_CODE = 111;

    private UserLocation userLocationManager;

    private EditText etRouteStart;
    private EditText etRouteEnd;
    private Button btCreateRoute;
    private ImageButton btMyLocation;

    private View.OnClickListener controlPanelButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ivRouteStart:
                    selectFromHistoryRoutePointStart();
                    break;
                case R.id.ivRouteEnd:
                    selectFromHistoryRoutePointEnd();
                    break;
                case R.id.btCreateRoute:
                    createRoute();
                    break;
                case R.id.btMyLocation:
                    myGPSPositionClick();
                    break;
                default:
                    throw new IllegalArgumentException("Check it!");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideActionBar();
        initViews();

        userLocationManager = new UserLocationManager(getApplicationContext());
        userLocationManager.addLocationEventsListener(this);
        startUserLocationTracking();

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initViews() {
        etRouteStart = findViewById(R.id.etRouteStart);
        etRouteEnd = findViewById(R.id.etRouteEnd);
        UiUtil.disableKeyboardOpening(etRouteStart);
        UiUtil.disableKeyboardOpening(etRouteEnd);
        btCreateRoute = findViewById(R.id.btCreateRoute);
        btCreateRoute.setEnabled(false);
        btCreateRoute.setOnClickListener(controlPanelButtonClickListener);
        btMyLocation = findViewById(R.id.btMyLocation);
        btMyLocation.setOnClickListener(controlPanelButtonClickListener);
        findViewById(R.id.ivRouteStart).setOnClickListener(controlPanelButtonClickListener);
        findViewById(R.id.ivRouteEnd).setOnClickListener(controlPanelButtonClickListener);
    }

    private void startUserLocationTracking() {
        if (PermissionsUtils.UserLocationPermission.isNeedRequest(this)) {
            PermissionsUtils.UserLocationPermission.request(this, LOCATION_PERMISSION_CODE);
            return;
        }

        userLocationManager.onUserLocationPermissionAccepted();
        if (!userLocationManager.isGPSEnabled()) {
            askUserAboutGPS();
        }
        userLocationManager.requestLastUserLocation(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE:
                boolean allowed = PermissionsUtils.UserLocationPermission.isAllowed(permissions, grantResults);
                UiNotificationsUtils.showDevMessage(MainActivity.this, "GPS is " +
                        (allowed ? "allowed" : "denied"));
                if (allowed)
                    startUserLocationTracking();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void selectFromHistoryRoutePointStart() {
        UiNotificationsUtils.showDevMessage(this, "selectFromHistoryRoutePointStart()");
    }

    private void selectFromHistoryRoutePointEnd() {
        UiNotificationsUtils.showDevMessage(this, "selectFromHistoryRoutePointEnd()");
    }

    private void createRoute() {
        UiNotificationsUtils.showDevMessage(this, "createRoute()");
    }

    private void myGPSPositionClick() {
        UiNotificationsUtils.showDevMessage(this, "myGPSPositionClick()");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    @Override
    public void onUserLocationChanged(@NonNull LatLng myNewLocation) {
        UiNotificationsUtils.showDevMessage(this, "onUserLocationChanged() + "
                + myNewLocation.toString());
    }

    @Override
    public void onGPSSignalAppeared() {
        UiNotificationsUtils.showDevMessage(this, "onGPSSignalAppeared()");
    }

    @Override
    public void onGPSSignalDisappeared() {
        UiNotificationsUtils.showDevMessage(this, "onGPSSignalDisappeared()");
    }

    private void askUserAboutGPS() {
        AlertDialog builder = new AlertDialog.Builder(this)
                .setTitle(R.string.attention)
                .setMessage(R.string.ask_gps_turn_on_msg)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        turnOnGPS();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(true)
                .show();
    }

    private void turnOnGPS() {
        SystemUtils.GPS.turnGPSOn(this);
    }
}
