package nl.cameldevstudio.innavo.services.building.geohelpers;

import com.google.firebase.firestore.GeoPoint;

import org.imperiumlabs.geofirestore.GeoFirestore;
import org.imperiumlabs.geofirestore.GeoQuery;

import javax.annotation.Nonnull;

import io.reactivex.Completable;
import io.reactivex.subjects.CompletableSubject;

public class GeoFirestoreWrapper {
    private GeoFirestore geoFirestore;

    public GeoFirestoreWrapper(@Nonnull GeoFirestore geoFirestore) {
        this.geoFirestore = geoFirestore;
    }

    @Nonnull
    public GeoFirestoreQueryWrapper queryAtLocation(@Nonnull GeoPoint center, double radius) {
        GeoQuery geoQuery = geoFirestore.queryAtLocation(center, radius);
        return new GeoFirestoreQueryWrapper(geoQuery);
    }

    @Nonnull
    public Completable setLocation(@Nonnull String documentId, @Nonnull GeoPoint location) {
        CompletableSubject subject = CompletableSubject.create();
        geoFirestore.setLocation(documentId, location, exception -> {
            if (exception == null) {
                subject.onComplete();
            } else {
                subject.onError(exception);
            }
        });
        return subject;
    }
}

