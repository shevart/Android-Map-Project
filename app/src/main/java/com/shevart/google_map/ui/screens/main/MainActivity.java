package com.shevart.google_map.ui.screens.main;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;
import com.shevart.google_map.R;
import com.shevart.google_map.location.UserLocation;
import com.shevart.google_map.location.UserLocationManager;
import com.shevart.google_map.models.TripPoint;
import com.shevart.google_map.ui.base.AbsMVPActivity;
import com.shevart.google_map.ui.google_map.GoogleMapViewHelper;
import com.shevart.google_map.util.BundleUtils;
import com.shevart.google_map.util.Launcher;
import com.shevart.google_map.util.LogUtil;
import com.shevart.google_map.util.PermissionsUtils;
import com.shevart.google_map.util.SystemUtils;
import com.shevart.google_map.util.UiNotificationsUtils;
import com.shevart.google_map.util.UiUtil;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AbsMVPActivity<MainScreenContract.Presenter, MainScreenContract.View>
        implements MainScreenContract.View, OnMapReadyCallback, UserLocationManager.LocationEventsListener {
    private static final int LOCATION_PERMISSION_CODE = 111;

    private UserLocation userLocationManager;
    private GoogleMapViewHelper googleMapViewHelper;

    private EditText etRouteStart;
    private EditText etRouteEnd;
    private Button btCreateRoute;
    private ImageButton btMyLocation;
    private View progressView;

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
                case R.id.btShowMyRoute:
                    showMyRouteClick();
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        userLocationManager.addLocationEventsListener(this);
        if (SystemUtils.GPS.isGPSEnabled(this)) {
            btMyLocation.setImageResource(R.drawable.wrapper_ic_gps_not_fixed_white);
            userLocationManager.requestLastUserLocation(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        userLocationManager.removeLocationEventsListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE:
                boolean allowed = PermissionsUtils.UserLocationPermission.isAllowed(permissions, grantResults);
                LogUtil.e("GPS is " + (allowed ? "allowed" : "denied"));
                if (allowed)
                    startUserLocationTracking();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Launcher.SELECT_START_TRIP_POINT_FROM_HISTORY_CODE:
                    getPresenter().onStartTripPointSelectedFromHistory(BundleUtils.getTripPoint(data));
                    break;
                case Launcher.SELECT_END_TRIP_POINT_FROM_HISTORY_CODE:
                    getPresenter().onEndTripPointSelectedFromHistory(BundleUtils.getTripPoint(data));
                    break;
            }
        }
    }

    @Override
    protected MainScreenContract.Presenter obtainPresenter() {
        return new MainPresenter(getApp().getNet(), getApp().getDB());
    }

    @Override
    protected MainScreenContract.View obtainView() {
        return this;
    }

    @Override
    public void onUserLocationChanged(@NonNull LatLng myNewLocation) {
        googleMapViewHelper.updateUserLocation(myNewLocation);
        btMyLocation.setImageResource(R.drawable.wrapper_ic_gps_fixed_white);
        LogUtil.e("onUserLocationChanged() + " + myNewLocation.toString());
    }

    @Override
    public void onGPSSignalAppeared() {
        btMyLocation.setImageResource(R.drawable.wrapper_ic_gps_fixed_white);
        LogUtil.e("onGPSSignalAppeared()");
    }

    @Override
    public void onGPSSignalDisappeared() {
        if (SystemUtils.GPS.isGPSEnabled(this)) {
            btMyLocation.setImageResource(R.drawable.wrapper_ic_gps_not_fixed_white);
        } else {
            btMyLocation.setImageResource(R.drawable.wrapper_ic_gps_off_white);
        }
        googleMapViewHelper.hideUserLocation();
        LogUtil.e("onGPSSignalDisappeared()");
    }

    @Override
    public void showProgress() {
        progressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressView.setVisibility(View.GONE);
    }

    @Override
    public void onStartTripRouteSelected(@NonNull TripPoint tripPoint) {
        etRouteStart.setText(tripPoint.getAddress());
        googleMapViewHelper.displayTripPoints(getPresenter().getStartTripPoint(),
                getPresenter().getEndTripPoint());
    }

    @Override
    public void onEndTripRouteSelected(@NonNull TripPoint tripPoint) {
        etRouteEnd.setText(tripPoint.getAddress());
        googleMapViewHelper.displayTripPoints(getPresenter().getStartTripPoint(),
                getPresenter().getEndTripPoint());
    }

    @Override
    public void showErrorPlaceMessage() {
        UiNotificationsUtils.showShortToast(this, getString(R.string.error_pick_address_by_geopoint));
    }

    @Override
    public void drawRoute(@NonNull PolylineOptions polylineOptions) {
        googleMapViewHelper.drawRouteBetweenTwoTripPoints(polylineOptions);
    }

    @Override
    public void showErrorDrawRoute() {
        UiNotificationsUtils.showShortToast(this, getString(R.string.error_alert_route_failed));
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
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (googleMapViewHelper.isUserLocation(marker)
                        && userLocationManager.getLastLocation() != null) {
                    onMapPointSelected(userLocationManager.getLastLocation());
                    return true;
                }
                return false;
            }
        });
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
        findViewById(R.id.btShowMyRoute).setOnClickListener(controlPanelButtonClickListener);
        findViewById(R.id.ivRouteStart).setOnClickListener(controlPanelButtonClickListener);
        findViewById(R.id.ivRouteEnd).setOnClickListener(controlPanelButtonClickListener);
        progressView = findViewById(R.id.progressView);
    }

    private void startUserLocationTracking() {
        if (PermissionsUtils.UserLocationPermission.isNeedRequest(this)) {
            PermissionsUtils.UserLocationPermission.request(this, LOCATION_PERMISSION_CODE);
            return;
        }

        userLocationManager.onUserLocationPermissionAccepted();
        if (!SystemUtils.GPS.isGPSEnabled(this)) {
            askUserAboutGPS();
        }
        userLocationManager.requestLastUserLocation(this);
    }

    private void selectFromHistoryRoutePointStart() {
        Launcher.ActivityComponents.startTripPointSelectFromHistory(this);
    }

    private void selectFromHistoryRoutePointEnd() {
        Launcher.ActivityComponents.endTripPointSelectFromHistory(this);
    }

    private void createRoute() {
        if (getPresenter().getStartTripPoint() == null) {
            UiNotificationsUtils.showShortToast(this, getString(R.string.error_alert_set_start_trip_point));
            return;
        }

        if (getPresenter().getEndTripPoint() == null) {
            UiNotificationsUtils.showShortToast(this, getString(R.string.error_alert_set_end_trip_point));
            return;
        }

        getPresenter().drawRoute();
    }

    private void myGPSPositionClick() {
        if (SystemUtils.GPS.isGPSEnabled(this)) {
            googleMapViewHelper.showUserLocation();
        } else {
            askUserAboutGPS();
        }
    }


    private void onMapPointSelected(@NonNull LatLng latLng) {
        if (etRouteStart.hasFocus()) {
            getPresenter().onStartTripPointCoordinatesSelected(latLng);
        } else {
            getPresenter().onEndTripPointCoordinatesSelected(latLng);
        }
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
        UiNotificationsUtils.showShortToast(this, getString(R.string.turn_on_gps_on_settings));
    }

    private void showMyRouteClick() {
        if (getPresenter().getStartTripPoint() == null
                && getPresenter().getEndTripPoint() == null) {
            UiNotificationsUtils.showShortToast(this, getString(R.string.error_alert_set_end_trip_point));
            return;
        }
        googleMapViewHelper.moveToTripPoints(
                getPresenter().getStartTripPoint(),
                getPresenter().getEndTripPoint());
    }
}
