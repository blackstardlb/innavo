package nl.cameldevstudio.innavo.ui.building.find;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
import java8.util.J8Arrays;
import nl.cameldevstudio.innavo.models.InnavoLocation;

public class LocationViewModelTest {
    private LocationViewModel locationViewModel;

    @Before
    public void setUp() throws Exception {
        locationViewModel = new LocationViewModel();
    }

    @Test
    public void getInnavoLocation() {
        InnavoLocation[] innavoLocations = {
                new InnavoLocation(12.23, 14.34),
                new InnavoLocation(5.43, 32.12)
        };

        TestObserver<InnavoLocation> test = locationViewModel.getInnavoLocation().test();

        J8Arrays.stream(innavoLocations).forEach(locationViewModel::setInnavoLocation);

        test.assertValues(innavoLocations).dispose();
    }
}