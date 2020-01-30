package nl.cameldevstudio.innavo.ui.building.find;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import nl.cameldevstudio.innavo.models.InnavoLocation;
import nl.cameldevstudio.innavo.ui.base.BaseViewModel;

/**
 * {@link android.arch.lifecycle.ViewModel} for monitoring current location
 */
public class LocationViewModel extends BaseViewModel {
    private BehaviorSubject<InnavoLocation> innavoLocationBehaviorSubject = BehaviorSubject.create();

    @Inject
    public LocationViewModel() {
    }

    public void setInnavoLocation(@Nonnull InnavoLocation innavoLocation) {
        innavoLocationBehaviorSubject.onNext(innavoLocation);
    }

    @Nonnull
    public Observable<InnavoLocation> getInnavoLocation() {
        return innavoLocationBehaviorSubject;
    }
}
