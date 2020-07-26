package com.example.myapplication;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;


import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
            , LocationListener {
    private LocationManager locMgr;
    private GoogleMap mMap;
    String bestProv;
    float zoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        // Add a marker in Sydney and move the camera
       LatLng school=new LatLng(22.734410,120.283831);
       zoom=17;
       mMap.addMarker(new MarkerOptions().position(school).title("高雄大學"));
       mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(school,zoom));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        requestPermission();
    }
    protected void onResume() {
        super.onResume();
        locMgr=(LocationManager)getSystemService(LOCATION_SERVICE);
        Criteria criteria=new Criteria();
        bestProv = locMgr.getBestProvider(criteria, true);
        if(locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)||
                locMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            if(ActivityCompat.checkSelfPermission(this, Manifest
            .permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    locMgr.requestLocationUpdates(bestProv,1000,1,this);
                    //bestprov取得定位,1000為最短更新時間,1為最短通知距離,this呼叫此程式
            }
        }else{
            Toast.makeText(this,"請開啟定位服務",Toast.LENGTH_LONG).show();
        }
    }
    protected void onPause(){
        super.onPause();
        if(ActivityCompat.checkSelfPermission(this, Manifest
                .permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
              locMgr.removeUpdates(this); //停止定位服務
        }
    }
    private void requestPermission(){
       int hasPermission=ActivityCompat.checkSelfPermission(this, Manifest
       .permission.ACCESS_FINE_LOCATION);
       if(hasPermission!=PackageManager.PERMISSION_GRANTED){
           ActivityCompat.requestPermissions(this,new String[]{Manifest.permission
           .ACCESS_FINE_LOCATION},1);
           return;
       }
     setLocation();
    }

    public void onRequestPermissionResult(int requestCode,String[] permissions,int[] grantResults){
        if(requestCode==1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                setLocation();
            }else{
                Toast.makeText(this,"未取得授權",Toast.LENGTH_LONG).show();
                finish();
            }
        }else{
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }
    private void setLocation() throws SecurityException{
        mMap.setMyLocationEnabled(true);  //顯示
    }
    @Override
    public void onLocationChanged(Location location) {
        //手機定位地點改變執行\\
        zoom=17;
        LatLng point=new LatLng(location.getLatitude(),location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point,zoom));
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //當定位被狀態被改變時，重新取得一個最佳定位的方式
    }

    @Override
    public void onProviderEnabled(String provider) {
        //用來處理當GPS或網路定位功能開啟時會以LocationManager物件的requestLocationUpdates()啟動定位
    }

    @Override
    public void onProviderDisabled(String provider) {
        //用來處理GPS或網路定位功能關閉時，會以LocationManager物件的removeUpdates()來停止定位
    }
}
