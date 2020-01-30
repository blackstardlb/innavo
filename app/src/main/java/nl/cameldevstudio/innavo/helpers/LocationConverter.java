package nl.cameldevstudio.innavo.helpers;


import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import javax.annotation.Nonnull;

import nl.cameldevstudio.innavo.models.InnavoLocation;

/**
 * Helper class for converting between different location classes
 */
public class LocationConverter {
    /**
     * Convert {@link LatLng} to {@link InnavoLocation}
     *
     * @param latLng the {@link LatLng}
     * @return the {@link InnavoLocation}
     */
    @Nonnull
    public static InnavoLocation toInnavoLocation(@Nonnull LatLng latLng) {
        return new InnavoLocation(latLng.latitude, latLng.longitude);
    }

    /**
     * Convert {@link InnavoLocation} to {@link LatLng}
     *
     * @param innavoLocation the {@link InnavoLocation}
     * @return the {@link LatLng}
     */
    @Nonnull
    public static LatLng toLatLng(@Nonnull InnavoLocation innavoLocation) {
        return new LatLng(innavoLocation.getLatitude(), innavoLocation.getLongitude());
    }

    /**
     * Convert {@link InnavoLocation} to {@link GeoPoint}
     *
     * @param innavoLocation the {@link InnavoLocation}
     * @return the {@link GeoPoint}
     */
    @Nonnull
    public static GeoPoint toGeoPoint(@Nonnull InnavoLocation innavoLocation) {
        return new GeoPoint(innavoLocation.getLatitude(), innavoLocation.getLongitude());
    }

    /**
     * Convert {@link GeoPoint} to {@link InnavoLocation}
     *
     * @param geoPoint the {@link GeoPoint}
     * @return the {@link InnavoLocation}
     */
    @Nonnull
    public static InnavoLocation toInnavoLocation(@Nonnull GeoPoint geoPoint) {
        return new InnavoLocation(geoPoint.getLatitude(), geoPoint.getLongitude());
    }
}
