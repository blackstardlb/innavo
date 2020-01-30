package nl.cameldevstudio.innavo.ui.building.find.map;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import javax.annotation.Nonnull;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nl.cameldevstudio.innavo.R;
import nl.cameldevstudio.innavo.helpers.ImageUtils;
import nl.cameldevstudio.innavo.helpers.LocationConverter;
import nl.cameldevstudio.innavo.models.Building;
import nl.cameldevstudio.innavo.models.DataChangeType;
import nl.cameldevstudio.innavo.models.InnavoLocation;
import nl.cameldevstudio.innavo.services.building.BuildingQuery;
import nl.cameldevstudio.innavo.ui.base.BaseFragment;
import nl.cameldevstudio.innavo.ui.building.find.LocationViewModel;
import nl.cameldevstudio.innavo.ui.building.info.BuildingInfoActivity;

import static nl.cameldevstudio.innavo.ui.building.find.list.BuildingAdapter.BUILDING_INFO_KEY;

/**
 * Fragment to search for buildings on map
 */
public class BuildingsMapFragment extends BaseFragment implements OnMapReadyCallback {

    private BuildingsMapViewModel buildingsMapViewModel;
    private LocationViewModel locationViewModel;
    private GoogleMap mMap;

    private Marker lastClickedMarker;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildingsMapViewModel = getPrivateViewModel(BuildingsMapViewModel.class);
        locationViewModel = getSharedViewModel(LocationViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buildings_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);

            activity.getSupportFragmentManager().getFragments().size();
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }
        }
    }

    @Override
    public void onMapReady(@Nonnull GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLoadedCallback(() -> {
            if (!mMap.isMyLocationEnabled()) {
                LatLngBounds defaultCamera = new LatLngBounds(new LatLng(50.830127, 3.122764), new LatLng(53.410309, 7.39834));
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(defaultCamera, 0));
            }
        });

        BuildingQuery buildingQuery = buildingsMapViewModel.loadFor(new InnavoLocation(0, 0), 0);
        getCompositeDisposable().add(
                buildingQuery
                        .observe()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(buildingDataChange -> {
                            if (buildingDataChange.getType() == DataChangeType.ADDED) {
                                Building building = buildingDataChange.getData();
                                MarkerOptions markerOptions = new MarkerOptions()
                                        .position(LocationConverter.toLatLng(building.getInnavoLocation()))
                                        .title(building.getName());

                                if (getContext() != null) {
//                                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(iconCreator.getIconFor(building.getName())));
                                    markerOptions.icon(ImageUtils.getMarkerIconFromDrawable(getContext(), R.drawable.building, 48));
                                }
                                Marker marker = mMap.addMarker(markerOptions);
                                marker.setTag(building.getId());
                            }
                        })
        );
        Context context = getContext();
        if (context != null) {
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style));
        }

        mMap.setOnCameraIdleListener(() -> {
            LatLng topRight = mMap.getProjection().getVisibleRegion().farRight;

            LatLng center = mMap.getCameraPosition().target;
            double radius = distanceBetween(center, topRight);

            buildingsMapViewModel.reloadFor(LocationConverter.toInnavoLocation(center), radius);
        });

        getCompositeDisposable().add(
                locationViewModel
                        .getInnavoLocation()
                        .subscribe(
                                innavoLocation -> {
                                    CameraPosition mapCameraPosition = buildingsMapViewModel.getMapCameraPosition();
                                    if (mapCameraPosition == null) {
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LocationConverter.toLatLng(innavoLocation), 13f));
                                    } else {
                                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mapCameraPosition));
                                    }
                                    try {
                                        mMap.setMyLocationEnabled(true);
                                        mMap.getUiSettings().setMyLocationButtonEnabled(false);
                                    } catch (SecurityException ignored) {
                                    }
                                }
                        )
        );

        // TODO on marker click
        googleMap.setOnMarkerClickListener(marker -> {
            if (marker.equals(lastClickedMarker)) {
                startBuildingInfoActivity(marker);
            } else {
                lastClickedMarker = marker;
            }
            marker.showInfoWindow();
            return false;
        });

        googleMap.setOnInfoWindowClickListener(this::startBuildingInfoActivity);
    }

    private void startBuildingInfoActivity(Marker marker) {
        String buildingId = (String) marker.getTag();
        Intent intent = new Intent(this.getActivity(), BuildingInfoActivity.class);
        intent.putExtra(BUILDING_INFO_KEY, buildingId);
        startActivity(intent);
    }

    @Override
    public void onDetach() {
        if (mMap != null) {
            buildingsMapViewModel.setMapCameraPosition(mMap.getCameraPosition());
        }
        super.onDetach();
    }


    /**
     * Get the distance between two points
     *
     * @param first  first location
     * @param second second location
     * @return the distance between two location in km as a double
     */
    private double distanceBetween(LatLng first, LatLng second) {
        float[] result = new float[1];
        Location.distanceBetween(first.latitude, first.longitude, second.latitude, second.longitude, result);
        return result[0] / 1000;
    }

    protected class IconCreator {
        private final View view;
        private final Context context;

        @BindView(R.id.label)
        protected TextView textView;

        private IconGenerator iconGenerator;

        public IconCreator(@Nonnull Context context) {
            this.context = context;
            iconGenerator = new IconGenerator(context);
            view = getLayoutInflater().inflate(R.layout.map_building_icon_view, null, false);
            ButterKnife.bind(this, view);
        }

        public Bitmap getIconFor(String label) {
            textView.setText(label);
            iconGenerator.setBackground(null);
            iconGenerator.setContentView(view);
            return iconGenerator.makeIcon();
        }
    }
}
