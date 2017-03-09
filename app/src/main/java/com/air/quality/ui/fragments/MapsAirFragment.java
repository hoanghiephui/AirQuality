package com.air.quality.ui.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.air.quality.AirQualityApplication;
import com.air.quality.OnMapAndViewReadyListener;
import com.air.quality.R;
import com.air.quality.RxBus;
import com.air.quality.api.models.MapAirQualityData;
import com.air.quality.events.EventBus;
import com.air.quality.injector.components.DaggerIMapsAirComponent;
import com.air.quality.injector.components.IApplicationComponent;
import com.air.quality.injector.components.IMapsAirComponent;
import com.air.quality.injector.modules.MapsAirQualityModule;
import com.air.quality.mvp.contracts.IMapsAirQualityContract;
import com.air.quality.ui.activitys.MainActivity;
import com.air.quality.utils.PermissionUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.air.quality.IConstants.TOKEN;
import static com.air.quality.utils.AirQualityUtils.valueQuality;

/**
 * Created by hoanghiep on 3/1/17.
 */

public class MapsAirFragment extends BaseFragment implements GoogleMap.OnMarkerClickListener,
  GoogleMap.OnMapClickListener, OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener, IMapsAirQualityContract.View ,
  GoogleMap.OnCameraIdleListener, GoogleMap.OnMapLoadedCallback,
  ActivityCompat.OnRequestPermissionsResultCallback{
  private static final String TAG = MapsAirFragment.class.getSimpleName();
  private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
  //latitude, longitude
  @BindView(R.id.progressBar)
  ProgressBar progressBar;

  @Inject
  IMapsAirQualityContract.Presenter presenter;

  private GoogleMap mMap = null;
  private boolean mPermissionDenied = false;

  /**
   * Keeps track of the selected marker.
   */
  private Marker mSelectedMarker;

  @Override
  protected void injectDependences() {
    IApplicationComponent applicationComponent = ((AirQualityApplication) getActivity().getApplication()).getApplicationComponent();
    IMapsAirComponent mapsAirComponent = DaggerIMapsAirComponent.builder()
      .iApplicationComponent(applicationComponent)
      .mapsAirQualityModule(new MapsAirQualityModule())
      .build();
    mapsAirComponent.inject(this);
  }

  @Override
  protected void attachView() {
    presenter.attachView(this);
  }

  @Override
  protected int getFragmentLayout() {
    return R.layout.fragment_feedcity;
  }

  @Override
  protected void bindEventHandlers(View view) {
    subscribeMetaChangedEvent();
    FragmentManager fm = getActivity().getSupportFragmentManager();/// getChildFragmentManager();
    SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
    if (supportMapFragment == null) {
      supportMapFragment = SupportMapFragment.newInstance();
      fm.beginTransaction().replace(R.id.content, supportMapFragment).commit();
    }
    new OnMapAndViewReadyListener(supportMapFragment, this);
    presenter.getMapsAirQuality("21.062539,105.7833057,22.062539,105.7833057", TOKEN);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

      return;
    }
    mMap.setMyLocationEnabled(true);

    // Hide the zoom controls.
    mMap.getUiSettings().setMapToolbarEnabled(true);
    mMap.getUiSettings().setZoomControlsEnabled(true);
    mMap.getUiSettings().setCompassEnabled(true);
    mMap.getUiSettings().setMyLocationButtonEnabled(false);
    mMap.getUiSettings().setAllGesturesEnabled(true);

    // Add lots of markers to the map.

    // Set listener for marker click event.  See the bottom of this class for its behavior.
    mMap.setOnMarkerClickListener(this);

    // Set listener for map click event.  See the bottom of this class for its behavior.
    mMap.setOnMapClickListener(this);
    mMap.setOnCameraIdleListener(this);
    mMap.setOnMapLoadedCallback(this);

    // Override the default content description on the view, for accessibility mode.
    // Ideally this string would be localized.
    googleMap.setContentDescription("Demo showing how to close the info window when the currently"
      + " selected marker is re-tapped.");


  }

  private void addMarkersToMap(List<MapAirQualityData.Maps> maps) {
    LatLngBounds bounds = null;
    for (MapAirQualityData.Maps map : maps) {
      LatLng latLng = new LatLng(map.getLat(), map.getLon());
      bounds = new LatLngBounds.Builder()
        .include(latLng)
        .build();
      mMap.addMarker(new MarkerOptions()
        .position(latLng)
        .title("AQI: " + map.getAqi())
        .snippet(valueStatus(Integer.valueOf(!map.getAqi().equals("-") ? map.getAqi() : "0"))))
        .setIcon(bitmapFromResource(Integer.valueOf(!map.getAqi().equals("-") ? map.getAqi() : "0")));
    }
    if (getActivity() != null && isAdded()) {
      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(((AirQualityApplication) getActivity().getApplication()).getLatLng(), 6));
    }
  }

  @Override
  public void onMapClick(final LatLng point) {
    // Any showing info window closes when the map is clicked.
    // Clear the currently selected marker.
    mSelectedMarker = null;
  }

  @Override
  public boolean onMarkerClick(final Marker marker) {
    // The user has re-tapped on the marker which was already showing an info window.
    if (marker.equals(mSelectedMarker)) {
      // The showing info window has already been closed - that's the first thing to happen
      // when any marker is clicked.
      // Return true to indicate we have consumed the event and that we do not want the
      // the default behavior to occur (which is for the camera to move such that the
      // marker is centered and for the marker's info window to open, if it has one).
      mSelectedMarker = null;
      return true;
    }

    mSelectedMarker = marker;

    // Return false to indicate that we have not consumed the event and that we wish
    // for the default behavior to occur.
    return false;
  }

  @Override
  public void showProgress() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideProgress() {
    progressBar.setVisibility(View.GONE);
  }

  @Override
  public void showListMaps(MapAirQualityData mapAirQualityData) {
    Log.d(TAG, "showListMaps: " + mapAirQualityData.getStatus());
    if (mapAirQualityData.getData().size() > 0) {
      addMarkersToMap(mapAirQualityData.getData());
    }
  }

  private String valueStatus(int value) {
    if (getActivity() != null && isAdded()) {
      int type = valueQuality(value);
      if (type == 0) {
        return getString(R.string.air_good);
      } else if (type == 1) {
        return getString(R.string.air_moderate);
      } else if (type == 2) {
        return getString(R.string.air_unhealthy_sensitive);
      } else if (type == 3) {
        return getString(R.string.air_unhealthy);
      } else if (type == 4) {
        return getString(R.string.air_very_unhealthy);
      } else {
        return getString(R.string.air_hazardous);
      }
    } else {
      return "";
    }
  }

  private BitmapDescriptor bitmapFromResource(int id) {
    int image = 0;
    switch (valueQuality(id)) {
      case 0:
        image = R.drawable.ic_marker_good;
        break;
      case 1:
        image = R.drawable.ic_marker_moderate;
        break;
      case 2:
        image = R.drawable.ic_marker_sensitive;
        break;
      case 3:
        image = R.drawable.ic_marker_unhealthy;
        break;
      case 4:
        image = R.drawable.ic_marker_very_unhealthy;
        break;
      case 5:
        image = R.drawable.ic_marker_hazar;
        break;
      default:
        break;
    }
    return BitmapDescriptorFactory.fromResource(image);
  }

  private void enableMyLocation() {
    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
      != PackageManager.PERMISSION_GRANTED) {
      // Permission to access the location is missing.
      PermissionUtils.requestPermission((MainActivity) getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
        Manifest.permission.ACCESS_FINE_LOCATION, true);
    } else if (mMap != null) {
      // Access to the location has been granted to the app.
      mMap.setMyLocationEnabled(true);
    }
  }

  @Override
  public void onCameraIdle() {
    String latlog = mMap.getCameraPosition().toString();
    String subLatlog = latlog.substring(latlog.indexOf("(") + 1, latlog.indexOf(")"));
    Log.d(TAG, "onCameraIdle: " + subLatlog);
    Log.d(TAG, "onCameraIdle: " + latlog);
    if (!subLatlog.equals("0.0,0.0")) {
      presenter.getMapsAirQuality(subLatlog.concat(",").concat(subLatlog), TOKEN);
    }
  }

  @Override
  public void onMapLoaded() {
    enableMyLocation();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
    if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
      return;
    }

    if (PermissionUtils.isPermissionGranted(permissions, grantResults,
      Manifest.permission.ACCESS_FINE_LOCATION)) {
      // Enable the my location layer if the permission has been granted.
      enableMyLocation();
    } else {
      // Display the missing permission error dialog when the fragments resume.
      mPermissionDenied = true;
    }
  }

  @Override
  public void onResume() {
    if (mPermissionDenied) {
      // Permission was not granted, display error dialog.
      showMissingPermissionError();
      mPermissionDenied = false;
    }
    super.onResume();
  }

  /**
   * Displays a dialog with error message explaining that the location permission is missing.
   */
  private void showMissingPermissionError() {
    PermissionUtils.PermissionDeniedDialog
      .newInstance(true).show(getFragmentManager(), "dialog");
  }

  private void subscribeMetaChangedEvent() {
    Subscription subscription = RxBus.getInstance()
      .toObservable(EventBus.Locations.class)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .distinctUntilChanged()
      .subscribe(new Action1<EventBus.Locations>() {
        @Override
        public void call(EventBus.Locations event) {
          LatLng latLng = new LatLng(event.getLatitude(), event.getLongitude());
          mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 4));
        }
      }, new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {

        }
      });
    RxBus.getInstance().addSubscription(this, subscription);
  }
}
