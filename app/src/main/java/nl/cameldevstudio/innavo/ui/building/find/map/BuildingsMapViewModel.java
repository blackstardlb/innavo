package nl.cameldevstudio.innavo.ui.building.find.map;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import nl.cameldevstudio.innavo.helpers.LocationConverter;
import nl.cameldevstudio.innavo.models.InnavoLocation;
import nl.cameldevstudio.innavo.services.building.BuildingQuery;
import nl.cameldevstudio.innavo.services.building.BuildingRepository;
import nl.cameldevstudio.innavo.ui.base.BaseViewModel;

/**
 * {@link android.arch.lifecycle.ViewModel} for {@link BuildingsMapFragment} state
 */
public class BuildingsMapViewModel extends BaseViewModel {
    private BuildingRepository buildingRepository;
    private BuildingQuery buildingQuery;
    private CameraPosition mapCameraPosition;

    @Inject
    BuildingsMapViewModel(@Nonnull BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Nonnull
    public BuildingQuery loadFor(@Nonnull InnavoLocation center, double radius) {
        buildingQuery = buildingRepository.queryBuildings(center, radius);
        return buildingQuery;
    }

    public void reloadFor(@Nonnull InnavoLocation center, double radius) {
        if (buildingQuery != null) {
            buildingQuery.setLocation(LocationConverter.toGeoPoint(center), radius);
        }
    }

    @Nullable
    public CameraPosition getMapCameraPosition() {
        return mapCameraPosition;
    }

    public void setMapCameraPosition(CameraPosition mapCameraPosition) {
        this.mapCameraPosition = mapCameraPosition;
    }
}
