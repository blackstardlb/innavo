package nl.cameldevstudio.innavo.services.building;

import com.google.firebase.firestore.GeoPoint;

import io.reactivex.Observable;
import nl.cameldevstudio.innavo.models.Building;
import nl.cameldevstudio.innavo.models.DataChange;

/**
 * Interface for monitoring buildings at a given center for a given radius
 */
public interface BuildingQuery {
    /**
     * Get the {@link Observable} for monitoring changes to monitored buildings
     *
     * @return {@link Observable} for monitoring changes to  monitored buildings
     */
    Observable<DataChange<Building>> observe();

    /**
     * Change the center to observe
     *
     * @param center the new center
     */
    void setCenter(GeoPoint center);

    /**
     * Change the radius to observe
     *
     * @param radius the new radius
     */
    void setRadius(double radius);

    /**
     * Change the location to observe
     *
     * @param center the new center
     * @param radius the new radius in km
     */
    void setLocation(GeoPoint center, double radius);
}
