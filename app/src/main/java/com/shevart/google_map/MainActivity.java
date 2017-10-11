package com.shevart.google_map;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.shevart.google_map.data.net.NetManager;
import com.shevart.google_map.location.UserLocation;
import com.shevart.google_map.location.UserLocationManager;
import com.shevart.google_map.ui.base.AbsActivity;
import com.shevart.google_map.ui.google_map.GoogleMapViewHelper;
import com.shevart.google_map.util.LogUtil;
import com.shevart.google_map.util.PermissionsUtils;
import com.shevart.google_map.util.SystemUtils;
import com.shevart.google_map.util.UiNotificationsUtils;
import com.shevart.google_map.util.UiUtil;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AbsActivity implements OnMapReadyCallback,
        UserLocationManager.LocationEventsListener {
    private static final int LOCATION_PERMISSION_CODE = 111;

    private UserLocation userLocationManager;
    private GoogleMapViewHelper googleMapViewHelper;

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
        googleMapViewHelper = new GoogleMapViewHelper(getApplicationContext());
        startUserLocationTracking();

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // TODO: 11.10.17 remove after test
        NetManager netManager = new NetManager();
        netManager.test();
    }

    @Override
    protected void onStart() {
        super.onStart();
        userLocationManager.addLocationEventsListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        userLocationManager.removeLocationEventsListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SystemUtils.GPS.isGPSEnabled(this)) {
            btMyLocation.setVisibility(View.VISIBLE);
            userLocationManager.requestLastUserLocation(this);
        }
    }

    private void initViews() {
        etRouteStart = findViewById(R.id.etRouteStart);
        etRouteEnd = findViewById(R.id.etRouteEnd);
        UiUtil.disableKeyboardOpening(etRouteStart);
        UiUtil.disableKeyboardOpening(etRouteEnd);
        btCreateRoute = findViewById(R.id.btCreateRoute);
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
        LogUtil.e("myGPSPositionClick()");
        googleMapViewHelper.showUserLocation();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapViewHelper.onMapReady(googleMap);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                onMapPointSelected(latLng);
            }
        });
    }

    private void onMapPointSelected(@NonNull LatLng latLng) {

    }

    @Override
    public void onUserLocationChanged(@NonNull LatLng myNewLocation) {
        LogUtil.e("onUserLocationChanged() + " + myNewLocation.toString());
        googleMapViewHelper.updateUserLocation(myNewLocation);
    }

    @Override
    public void onGPSSignalAppeared() {
        LogUtil.e("onGPSSignalAppeared()");
        btMyLocation.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGPSSignalDisappeared() {
        LogUtil.e("onGPSSignalDisappeared()");
        btMyLocation.setVisibility(View.GONE);
    }

    private void askUserAboutGPS() {
        new AlertDialog.Builder(this, R.style.DialogTheme)
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
        UiNotificationsUtils.showEmptyToast(this, getString(R.string.turn_on_gps_on_settings));
    }
}
