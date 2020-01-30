package nl.cameldevstudio.innavo.services.building;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import nl.cameldevstudio.innavo.models.Building;
import nl.cameldevstudio.innavo.models.InnavoLocation;

/**
 * The building repository
 */
public interface BuildingRepository {
    /**
     * Get {@link BuildingQuery} for a given center and radius
     *
     * @param center the center as a {@link InnavoLocation}
     * @param radius the radius in km
     * @return a {@link BuildingQuery}
     */
    BuildingQuery queryBuildings(InnavoLocation center, double radius);


    Observable<List<Building>> findBuildings(String query);

    Maybe<Building> getBuilding(String id);

    /**
     * Add test buildings to repository
     *
     * @return {@link Completable}
     */
    Completable addTestBuildings();
}
