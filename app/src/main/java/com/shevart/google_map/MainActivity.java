package com.shevart.google_map;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.shevart.google_map.ui.base.AbsActivity;
import com.shevart.google_map.util.PermissionsUtils;
import com.shevart.google_map.util.UiNotificationsUtils;
import com.shevart.google_map.util.UiUtil;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AbsActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_CODE = 21;

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
        requestLocationPermission();

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

    private void requestLocationPermission() {
        if (PermissionsUtils.UserLocationPermission.isNeedRequest(this)) {
            PermissionsUtils.UserLocationPermission.request(this, LOCATION_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE:
                boolean allowed = PermissionsUtils.UserLocationPermission.isAllowed(permissions, grantResults);
                UiNotificationsUtils.showDevMessage(MainActivity.this, "GPS is " +
                        (allowed ? "allowed" : "denied"));
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void selectFromHistoryRoutePointStart() {

    }

    private void selectFromHistoryRoutePointEnd() {

    }

    private void createRoute() {

    }

    private void myGPSPositionClick() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


}
