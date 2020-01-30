package nl.cameldevstudio.innavo.services.building.geohelpers;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import nl.cameldevstudio.innavo.models.DataChange;
import nl.cameldevstudio.innavo.models.DataChangeType;

public class GeoFirestoreDataChange extends DataChange<DocumentSnapshot> {
    private GeoPoint geoPoint;

    public GeoFirestoreDataChange(@Nonnull DocumentSnapshot data, @Nonnull DataChangeType type, @Nullable GeoPoint geoPoint) {
        super(data, type);
        this.geoPoint = geoPoint;
    }

    @Nullable
    public GeoPoint getGeoPoint() {
        return geoPoint;
    }
}
