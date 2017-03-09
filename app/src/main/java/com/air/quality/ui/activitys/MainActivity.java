package com.air.quality.ui.activitys;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.air.quality.AirQualityApplication;
import com.air.quality.R;
import com.air.quality.RxBus;
import com.air.quality.events.EventBus;
import com.air.quality.managers.PermissionManager;
import com.air.quality.permission.PermissionCallback;
import com.air.quality.services.AirQualityService;
import com.air.quality.ui.fragments.MainFragment;
import com.air.quality.ui.fragments.MapsAirFragment;
import com.akhgupta.easylocation.EasyLocationRequest;
import com.akhgupta.easylocation.EasyLocationRequestBuilder;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;

import static com.air.quality.utils.AirQualityUtils.isMarshmallow;

public class MainActivity extends BaseActivity {
  private static final String TAG = MainActivity.class.getSimpleName();
  @BindView(R.id.container)
  ViewGroup container;


  private final PermissionCallback permissionReadstorageCallback = new PermissionCallback() {
    @Override
    public void permissionGranted() {
      onLoadData();
    }

    @Override
    public void permissionRefused() {
      finish();
    }
  };

  @Override
  protected void injectDependences() {
  }

  @Override
  protected void attachView() {
  }

  @Override
  protected int getLayout() {
    return R.layout.activity_main2;
  }

  @Override
  protected void initContent(Bundle bundle) {
    naviHome.run();
    if (isMarshmallow()) {
      checkPermissionAndThenLoad();
    } else {
      onLoadData();
    }
    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onDestroy() {
    stopLocationUpdates();
    super.onDestroy();
  }

  private void checkPermissionAndThenLoad() {
    //check for permission
    if (PermissionManager.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
      onLoadData();
    } else {
      if (PermissionManager.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
        Snackbar.make(container, "Air Quality will need to read location to display data.",
          Snackbar.LENGTH_INDEFINITE)
          .setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              PermissionManager.askForPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION, permissionReadstorageCallback);
            }
          }).show();
      } else {
        PermissionManager.askForPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, permissionReadstorageCallback);
      }
    }
  }

  private void onLoadData() {
    LocationRequest locationRequest = new LocationRequest()
      .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
      .setInterval(180000)
      .setFastestInterval(180000);
    EasyLocationRequest easyLocationRequest = new EasyLocationRequestBuilder()
      .setLocationRequest(locationRequest)
      .setFallBackToLastLocationTime(3000)
      .build();
    requestSingleLocationFix(easyLocationRequest);
    startService(new Intent(this, AirQualityService.class));
  }


  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
    = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
        case R.id.navigation_home:
          showNavi(naviHome);
          return true;
        case R.id.navigation_dashboard:
          showNavi(naviMaps);
          return true;
        case R.id.navigation_notifications:
          return true;
      }
      return false;
    }

  };

  private void showNavi(final Runnable runnable) {
    if (runnable != null) {
      new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
          runnable.run();
        }
      }, 350);
    }
  }

  private Runnable naviHome = new Runnable() {
    @Override
    public void run() {
      Fragment fragment = new MainFragment();
      FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
      fragmentTransaction.replace(R.id.content, fragment).commitAllowingStateLoss();
    }
  };

  private Runnable naviMaps = new Runnable() {
    @Override
    public void run() {
      Fragment fragment = new MapsAirFragment();
      FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
      fragmentTransaction.replace(R.id.content, fragment).commitAllowingStateLoss();
    }
  };

  //location
  @Override
  public void onLocationPermissionGranted() {

  }

  @Override
  public void onLocationPermissionDenied() {

  }

  @Override
  public void onLocationReceived(Location location) {
    EventBus.Locations locations = new EventBus.Locations(location.getLatitude(), location.getLongitude());
    RxBus.getInstance().post(locations);
    ((AirQualityApplication)this.getApplication()).setLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
    Toast.makeText(this, location.getProvider() + "," + location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_SHORT).show();
    Log.d(TAG, "onLocationReceived: " + location.getLatitude() + "," + location.getLongitude());
  }

  @Override
  public void onLocationProviderEnabled() {

  }

  @Override
  public void onLocationProviderDisabled() {

  }
}
