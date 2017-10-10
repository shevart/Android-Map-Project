package com.shevart.google_map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private EditText etRouteStart;
    private EditText etRouteEnd;
    private Button btCreateRoute;

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
                default:
                    throw new IllegalArgumentException("Check it!");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        etRouteStart = findViewById(R.id.etRouteStart);
        etRouteEnd = findViewById(R.id.etRouteEnd);
        btCreateRoute = findViewById(R.id.btCreateRoute);
        btCreateRoute.setEnabled(false);
        btCreateRoute.setOnClickListener(controlPanelButtonClickListener);
        findViewById(R.id.ivRouteStart).setOnClickListener(controlPanelButtonClickListener);
        findViewById(R.id.ivRouteEnd).setOnClickListener(controlPanelButtonClickListener);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void selectFromHistoryRoutePointStart() {

    }

    private void selectFromHistoryRoutePointEnd() {

    }

    private void createRoute() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
