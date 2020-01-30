package nl.cameldevstudio.innavo;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import javax.annotation.Nonnull;

import nl.cameldevstudio.innavo.di.DaggerInnavoComponent;
import nl.cameldevstudio.innavo.di.InnavoComponent;
import nl.cameldevstudio.innavo.di.modules.ApplicationModule;

/**
 * The {@link Application} class
 */
public class InnavoApplication extends Application {
    private static InnavoComponent innavoComponent;

    @Nonnull
    public static InnavoComponent getInnavoComponent() {
        return innavoComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        innavoComponent = buildComponent();
    }

    private InnavoComponent buildComponent() {
        return DaggerInnavoComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}
