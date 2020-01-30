package nl.cameldevstudio.innavo.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provides Application context
 */
@Module
public class ApplicationModule {
    private Context context;

    public ApplicationModule(@NonNull Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return context;
    }
}