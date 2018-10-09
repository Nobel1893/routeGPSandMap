package route.com.gps;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Mohamed Nabil Mohamed (Nobel) on 10/5/2018.
 * byte code SA
 * m.nabil.fci2015@gmail.com
 */
public class LocationProvider implements LocationListener{

    double latitude;
    double longitude;
    Location location;
    boolean canGetLocation;
    Context mContext;
    LocationManager locationManager;
    final int MIN_DISTANCE_BETWEEN_UPDATES =10;
    final long MIN_TIME_BETWEEN_UPDATES = 5*60*1000;
    public LocationProvider(Context context){
        this.mContext=context;
        latitude=0.0;
        longitude=0.0;
        location=null;
        getDeviceLocation();
    }


    public void getDeviceLocation(){
        locationManager= (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);

       boolean GPSEnabled= locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
       boolean NTEnabled= locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

       if(!GPSEnabled&&!NTEnabled){
           canGetLocation=false;
           return;
       }


       String provider=null;
       if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
           provider = LocationManager.NETWORK_PROVIDER;

       if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
           provider=LocationManager.GPS_PROVIDER;

       try {
           locationManager.requestLocationUpdates(provider,
                   MIN_TIME_BETWEEN_UPDATES,MIN_DISTANCE_BETWEEN_UPDATES,
                   this);

           if(locationManager!=null){
               canGetLocation=true;
               Log.e("location manager","not null");
               location=
                       locationManager.getLastKnownLocation(provider);

               if(location!=null){
                   Log.e("location","not null");
                   latitude=location.getLatitude();
                   longitude=location.getLongitude();
               }

           }

       }catch (SecurityException ex){
            Log.e("location Manager"," permission required");
       }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location=location;
        latitude=location.getLatitude();
        longitude=location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.e("provider changed",provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.e("provider enabled",provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.e("provider disabled",provider);

    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isCanGetLocation() {
        return canGetLocation;
    }
}
