package route.com.gps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import route.com.gps.Base.MyBaseActivity;

public class MainActivity extends MyBaseActivity implements OnMapReadyCallback {

    TextView textView;
    MapView mapView;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView= findViewById(R.id.text_view);

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        if(checkLocationPermission()){
            //Permission granted
            // do your work
            LocationPermessionIsGranted();

        }else {
            //not granted
            //request runtime permission
            RequestLocationPermission();
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);

        if(checkLocationPermission())
            map.setMyLocationEnabled(true);

        if(locationProvider!=null&&locationProvider.canGetLocation)
        map.moveCamera(
                CameraUpdateFactory.newLatLng(new LatLng(locationProvider.getLatitude(),
                        locationProvider.getLongitude()) ));

    }

    public boolean checkLocationPermission(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }
    public final int REQUEST_LOCATION_PERMISSION_CALL_BACk = 503;
    public final int REQUEST_CAMERA_PERMISSION_CALL_BACk = 504;
    public void RequestLocationPermission(){

        // Here, thisActivity is the current activity
        if (!checkLocationPermission()) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                ShowMessage("warning",
                        "GPS wants to use your location to get the best offers around you"
                        , new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ActivityCompat.requestPermissions(activity,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                                Manifest.permission.ACCESS_FINE_LOCATION},
                                        REQUEST_LOCATION_PERMISSION_CALL_BACk);

                            }
                        });
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_PERMISSION_CALL_BACk);

            }
        } else {
            // Permission has already been granted

            LocationPermessionIsGranted();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION_CALL_BACk: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    LocationPermessionIsGranted();

                } else {
                    Toast.makeText(activity, "user Denied permission", Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    LocationProvider locationProvider;
    public void LocationPermessionIsGranted(){
        locationProvider= new LocationProvider(activity);
        Toast.makeText(activity, "user allowed permission", Toast.LENGTH_SHORT).show();
        textView.setText(locationProvider.getLatitude()+" "+locationProvider.getLongitude());
    }

}
