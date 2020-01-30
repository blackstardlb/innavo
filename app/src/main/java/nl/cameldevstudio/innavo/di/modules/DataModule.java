package nl.cameldevstudio.innavo.di.modules;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nl.cameldevstudio.innavo.services.building.BuildingRepository;
import nl.cameldevstudio.innavo.services.building.BuildingRepositoryImpl;
import nl.cameldevstudio.innavo.services.login.LoginService;
import nl.cameldevstudio.innavo.services.login.LoginServiceImpl;

/**
 * Provides data dependencies
 */
@Module
public class DataModule {
    @Provides
    @Singleton
    BuildingRepository providesBuildingRepository(FirebaseFirestore firebaseFirestore) {
        return new BuildingRepositoryImpl(firebaseFirestore);
    }

    @Provides
    @Singleton
    LoginService providesLoginService(FirebaseAuth firebaseAuth) {
        return new LoginServiceImpl(firebaseAuth);
    }
}
