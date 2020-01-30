package nl.cameldevstudio.innavo.ui.building.find;

import android.Manifest;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tbruyelle.rxpermissions2.RxPermissions;

import nl.cameldevstudio.innavo.R;
import nl.cameldevstudio.innavo.models.InnavoLocation;
import nl.cameldevstudio.innavo.ui.base.BaseFragment;
import nl.cameldevstudio.innavo.ui.building.find.map.BuildingsMapFragment;

public class FindBuildingFragment extends BaseFragment {
    private RxPermissions rxPermissions;
    private LocationViewModel locationViewModel;
    private LocationManager locationManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rxPermissions = new RxPermissions(this);
        locationViewModel = getSharedViewModel(LocationViewModel.class);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_find_building, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getCompositeDisposable().add(
                rxPermissions
                        .request(Manifest.permission.ACCESS_FINE_LOCATION)
                        .subscribe(this::onLocationPermissionResult)
        );
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.map_frame, new BuildingsMapFragment()).commit();
        }
    }
}
