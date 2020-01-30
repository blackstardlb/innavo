package nl.cameldevstudio.innavo.ui.building.find;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.cameldevstudio.innavo.R;
import nl.cameldevstudio.innavo.models.InnavoLocation;
import nl.cameldevstudio.innavo.ui.base.BaseActivity;
import nl.cameldevstudio.innavo.ui.building.find.list.BuildingsListViewModel;
import nl.cameldevstudio.innavo.ui.building.find.map.BuildingsMapFragment;

public class FindBuildingActivity extends BaseActivity {
    private LocationPermissionManager locationPermissionManager = new LocationPermissionManager(this, this::onLocationPermissionResult);

    private LocationViewModel locationViewModel;
    private LocationManager locationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_building);
        ButterKnife.bind(this);
        locationViewModel = getViewModel(LocationViewModel.class);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationPermissionManager.ensureHavePermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationPermissionManager.onRequestPermissionsResult(requestCode, grantResults);
    }

    private void onLocationPermissionResult(Boolean isPermissionGranted) throws SecurityException {
        if (isPermissionGranted) {
            String provider = locationManager.getBestProvider(new Criteria(), false);
            Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
            if (lastKnownLocation != null) {
                InnavoLocation innavoLocation = new InnavoLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                locationViewModel.setInnavoLocation(innavoLocation);
            }
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.map_frame, new BuildingsMapFragment()).commit();
    }
}
