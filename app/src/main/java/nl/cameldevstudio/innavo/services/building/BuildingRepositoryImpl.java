package nl.cameldevstudio.innavo.services.building;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.imperiumlabs.geofirestore.GeoFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import durdinapps.rxfirebase2.RxFirestore;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import nl.cameldevstudio.innavo.helpers.FirebaseUtils;
import nl.cameldevstudio.innavo.helpers.LocationConverter;
import nl.cameldevstudio.innavo.models.Building;
import nl.cameldevstudio.innavo.models.InnavoLocation;
import nl.cameldevstudio.innavo.services.building.geohelpers.GeoFirestoreBuildingQuery;
import nl.cameldevstudio.innavo.services.building.geohelpers.GeoFirestoreQueryWrapper;
import nl.cameldevstudio.innavo.services.building.geohelpers.GeoFirestoreWrapper;

public class BuildingRepositoryImpl implements BuildingRepository {
    private final GeoFirestoreWrapper geoFirestoreWrapper;
    private final CollectionReference buildings;

    public BuildingRepositoryImpl(@Nonnull FirebaseFirestore firebaseFirestore) {
        buildings = firebaseFirestore.collection("buildings");
        geoFirestoreWrapper = new GeoFirestoreWrapper(new GeoFirestore(buildings));
    }

    @Override
    @Nonnull
    public BuildingQuery queryBuildings(InnavoLocation center, double radius) {
        GeoFirestoreQueryWrapper geoFirestoreQueryWrapper = geoFirestoreWrapper.queryAtLocation(LocationConverter.toGeoPoint(center), radius);
        return new GeoFirestoreBuildingQuery(geoFirestoreQueryWrapper);
    }

    @Override
    public Observable<List<Building>> findBuildings(String query) {
        /*
        Due to limitations in firestore we can only search on names that start with the query and this is case sensitive
        for better search look at algolia with firebase cloud functions (Requires plan upgrade)
         */
        //noinspection Convert2MethodRef
        return RxFirestore
                .getCollection(FirebaseUtils.startsWith("name", query, buildings))
                .map(QuerySnapshot::getDocuments)
                .toSingle(new ArrayList<>())
                .toObservable()
                .map(documents -> StreamSupport.stream(documents).map(this::toBuilding).filter(building -> building != null).collect(Collectors.toList()));
    }

    @Override
    public Maybe<Building> getBuilding(String id) {
        return RxFirestore.getDocument(buildings.document(id)).map(this::toBuilding);
    }

    @Override
    @Nonnull
    public Completable addTestBuildings() {
        List<Building> testBuildings = TestBuildings.get();
        List<Completable> collect = StreamSupport
                .stream(testBuildings)
                .map(building -> RxFirestore.addDocument(this.buildings, toMap(building))
                        .flatMapCompletable(documentReference ->
                                geoFirestoreWrapper.setLocation(documentReference.getId(),
                                        LocationConverter.toGeoPoint(building.getInnavoLocation())
                                )
                        )
                )
                .collect(Collectors.toList());
        return Completable.merge(collect);
    }

    /**
     * Convert {@link Building} to map of objects
     *
     * @param building the {@link Building}
     * @return map of objects representing building fields
     */
    @Nonnull
    private Map<String, Object> toMap(Building building) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", building.getName());
        map.put("address", building.getAddress());
        map.put("street", building.getStreet());
        map.put("housenumber", building.getHouseNumber());
        map.put("city", building.getCity());
        map.put("image", building.getImage());
        return map;
    }

    private Building toBuilding(@Nonnull DocumentSnapshot documentSnapshot) {
        String id = documentSnapshot.getId();
        String name = documentSnapshot.getString("name");
        String street = documentSnapshot.getString("street");
        String houseNumber = documentSnapshot.getString("housenumber");
        String city = documentSnapshot.getString("city");
        String image = documentSnapshot.getString("image");

        InnavoLocation location = null;
        @SuppressWarnings("unchecked") List<Double> coordinates = (List<Double>) documentSnapshot.get("l");
        if (coordinates != null && coordinates.size() == 2) {
            location = new InnavoLocation(coordinates.get(0), coordinates.get(1));
        }
//        if (coordinates != null) {
//            List<Double> parsedCoordinates = StreamSupport.stream(coordinates).mapToDouble(Double::parseDouble).boxed().collect(Collectors.toList());
//
//        }

        if (street != null && houseNumber != null && city != null && name != null && location != null) {
            return new Building(id, name, street, houseNumber, city, image, location);
        } else {
            return null;
        }
    }
}
