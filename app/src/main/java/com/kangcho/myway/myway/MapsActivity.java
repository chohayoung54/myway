package com.kangcho.myway.myway;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder coder;
    SupportMapFragment mapFragment;
    int changed_check=0;
    double my_latitude=0;
    double my_longitude=0;
    ArrayList<LatLng> arrayPoints;
    final int MY_PERMISSIONS_REQUEST_FL=998;
    final int MY_PERMISSIONS_REQUEST_CL=999;
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FL:

                if ( grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    setMyPosition();
                    // startActivity(a.getIntent());
                } else {
                    Toast.makeText(getApplicationContext(), "주소록 접근이 거절되었습니다. 추가 승인이 필요합니다.", Toast.LENGTH_SHORT).show();
                }

                break;

/*
            case MY_PERMISSIONS_REQUEST_FL:
                if ( grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    setMyPosition();
                    //fragment_second.mak=true;
                    // startActivity(a.getIntent());
                } else {
                    Toast.makeText(getApplicationContext(), "카메라 접근이 거절되었습니다. 추가 승인이 필요합니다.", Toast.LENGTH_SHORT).show();
                }
                break;
                */
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FL);

        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_CL);

        }
coder=new Geocoder(this);
//mMap=((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            setMyPosition();

        }

    }

public void setMyPosition() {
    LocationManager manager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
    LocationListener locationlistener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (changed_check == 0) {
                my_latitude = location.getLatitude();
                my_longitude = location.getLongitude();
                LatLng startingPoint = new LatLng(my_latitude, my_longitude);
                Log.i("my position :", my_latitude + "," + my_longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startingPoint, 12));
                Toast.makeText(getApplicationContext(), "this location search is completed.", Toast.LENGTH_SHORT).show();
                mMap.addCircle(new CircleOptions().center(new LatLng(my_latitude, my_longitude)).radius(7).strokeColor(Color.RED).fillColor(Color.BLACK));
                changed_check = 1;
                arrayPoints = new ArrayList<LatLng>();
            } else {

                
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.i("My position1 :", my_latitude + "," + my_longitude);
        }

        @Override
        public void onProviderEnabled(String s) {
            Log.i("My position1 :", my_latitude + "," + my_longitude);
        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
    ActivityCompat.requestPermissions(this,
            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
            MY_PERMISSIONS_REQUEST_FL);

}
    if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_CL);

    }
manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationlistener);

    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationlistener);

}
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void onResume(){
        super.onResume();

        /*
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FL);

        }*/
       // mMap.setMyLocationEnabled(true);

    }

}
