package nl.cameldevstudio.innavo.services.building.geohelpers;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;

import org.imperiumlabs.geofirestore.GeoQuery;
import org.imperiumlabs.geofirestore.GeoQueryDataEventListener;

import javax.annotation.Nonnull;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;
import nl.cameldevstudio.innavo.models.DataChangeType;

public class GeoFirestoreQueryWrapper {
    private GeoQuery geoQuery;
    private ReplaySubject<GeoFirestoreDataChange> replaySubject;
    private GeoQueryDataEventListener listener;

    public GeoFirestoreQueryWrapper(@Nonnull GeoQuery geoQuery) {
        this.geoQuery = geoQuery;
    }

    @Nonnull
    public Observable<GeoFirestoreDataChange> observe() {
        if (replaySubject == null) {
            replaySubject = ReplaySubject.create();
            listener = new GeoQueryDataEventListener() {
                @Override
                public void onDocumentEntered(DocumentSnapshot documentSnapshot, GeoPoint geoPoint) {
                    replaySubject.onNext(new GeoFirestoreDataChange(documentSnapshot, DataChangeType.ADDED, geoPoint));
                }

                @Override
                public void onDocumentExited(DocumentSnapshot documentSnapshot) {
                    replaySubject.onNext(new GeoFirestoreDataChange(documentSnapshot, DataChangeType.REMOVED, null));
                }

                @Override
                public void onDocumentMoved(DocumentSnapshot documentSnapshot, GeoPoint geoPoint) {
                    replaySubject.onNext(new GeoFirestoreDataChange(documentSnapshot, DataChangeType.UPDATED, geoPoint));
                }

                @Override
                public void onDocumentChanged(DocumentSnapshot documentSnapshot, GeoPoint geoPoint) {
                    replaySubject.onNext(new GeoFirestoreDataChange(documentSnapshot, DataChangeType.UPDATED, geoPoint));
                }

                @Override
                public void onGeoQueryReady() {

                }

                @Override
                public void onGeoQueryError(Exception e) {
                    replaySubject.onError(e);
                }
            };
            geoQuery.addGeoQueryDataEventListener(listener);
        }
        return replaySubject.doOnDispose(() -> geoQuery.removeGeoQueryEventListener(listener));
    }

    public void setCenter(GeoPoint center) {
        geoQuery.setCenter(center);
    }

    public void setRadius(double radius) {
        geoQuery.setRadius(radius);
    }

    public void setLocation(GeoPoint center, double radius) {
        geoQuery.setLocation(center, radius);
    }
}
