package nl.cameldevstudio.innavo.services.building.geohelpers;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;

import javax.annotation.Nonnull;

import io.reactivex.Observable;
import nl.cameldevstudio.innavo.helpers.LocationConverter;
import nl.cameldevstudio.innavo.models.Building;
import nl.cameldevstudio.innavo.models.DataChange;
import nl.cameldevstudio.innavo.models.InnavoLocation;
import nl.cameldevstudio.innavo.services.building.BuildingQuery;

public class GeoFirestoreBuildingQuery implements BuildingQuery {
    private GeoFirestoreQueryWrapper geoFirestoreQueryWrapper;


    public GeoFirestoreBuildingQuery(@Nonnull GeoFirestoreQueryWrapper geoFirestoreQueryWrapper) {
        this.geoFirestoreQueryWrapper = geoFirestoreQueryWrapper;
    }

    @Override
    @Nonnull
    public Observable<DataChange<Building>> observe() {
        return geoFirestoreQueryWrapper.observe().map(dataChange -> new DataChange<>(toBuilding(dataChange), dataChange.getType()));
    }

    @Override
    public void setCenter(@Nonnull GeoPoint center) {
        geoFirestoreQueryWrapper.setCenter(center);
    }

    @Override
    public void setRadius(double radius) {
        geoFirestoreQueryWrapper.setRadius(radius);
    }

    @Override
    public void setLocation(@Nonnull GeoPoint center, double radius) {
        geoFirestoreQueryWrapper.setLocation(center, radius);
    }

    /**
     * Convert a {@link GeoFirestoreDataChange} to a {@link Building}
     *
     * @param dataChange a {@link GeoFirestoreDataChange}
     * @return a {@link Building}
     */
    @Nonnull
    private Building toBuilding(@Nonnull GeoFirestoreDataChange dataChange) {
        DocumentSnapshot documentSnapshot = dataChange.getData();
        String id = documentSnapshot.getId();
        String name = documentSnapshot.getString("name");
        String street = documentSnapshot.getString("street");
        String housenumber = documentSnapshot.getString("housenumber");
        String city = documentSnapshot.getString("city");
        String image = documentSnapshot.getString("image");
        GeoPoint geoPoint = dataChange.getGeoPoint();
        InnavoLocation location = null;
        if (geoPoint != null) {
            location = LocationConverter.toInnavoLocation(geoPoint);
        }
        return new Building(id, name, street, housenumber, city, image, location);
    }
}
