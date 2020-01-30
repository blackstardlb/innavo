package nl.cameldevstudio.innavo.di.modules;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nl.cameldevstudio.innavo.services.image.ImageReferenceService;
import nl.cameldevstudio.innavo.services.image.ImageReferenceServiceImpl;

/**
 * Provides Firebase services
 */
@Module
public class FirebaseModule {
    @Provides
    @Singleton
    public FirebaseStorage providesFirebaseStorage() {
        return FirebaseStorage.getInstance();
    }

    @Provides
    @Singleton
    public FirebaseAuth providesFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    public FirebaseFirestore providesFirebaseFirestore() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);
        return firestore;
    }

    @Provides
    @Singleton
    public ImageReferenceService providesImageReferenceService(FirebaseStorage firebaseStorage) {
        return new ImageReferenceServiceImpl(firebaseStorage);
    }
}
