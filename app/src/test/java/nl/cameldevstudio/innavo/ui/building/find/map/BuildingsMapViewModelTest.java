package nl.cameldevstudio.innavo.ui.building.find.map;

import com.google.firebase.firestore.GeoPoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;
import java8.util.J8Arrays;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import nl.cameldevstudio.innavo.helpers.LocationConverter;
import nl.cameldevstudio.innavo.models.Building;
import nl.cameldevstudio.innavo.models.DataChange;
import nl.cameldevstudio.innavo.models.DataChangeType;
import nl.cameldevstudio.innavo.models.InnavoLocation;
import nl.cameldevstudio.innavo.services.building.BuildingQuery;
import nl.cameldevstudio.innavo.services.building.BuildingRepository;
import nl.cameldevstudio.innavo.services.building.TestBuildings;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BuildingsMapViewModelTest {
    private BuildingRepository buildingRepository = mock(BuildingRepository.class);
    private BuildingQuery buildingQuery = mock(BuildingQuery.class);
    private BuildingsMapViewModel mapViewModel = new BuildingsMapViewModel(buildingRepository);

    private List<DataChange<Building>> changes = StreamSupport.stream(TestBuildings.get()).map(it -> new DataChange<>(it, DataChangeType.ADDED)).collect(Collectors.toList());

    @Before
    public void setUp() {
        when(buildingRepository.queryBuildings(any(), anyDouble())).thenReturn(buildingQuery);
    }

    @Test
    public void loadFor() {
        when(buildingQuery.observe()).thenReturn(Observable.fromIterable(changes));
        mapViewModel.loadFor(new InnavoLocation(12, 12), 3)
                .observe()
                .test()
                .assertValueCount(2)
                .dispose();
    }

    @Test
    public void reloadFor() {
        Subject<DataChange<Building>> subject = ReplaySubject.create();
        when(buildingQuery.observe()).thenReturn(subject);

        GeoPoint point = new GeoPoint(12, 13);
        double newRadius = 3.0;

        doAnswer(invocation -> {
            StreamSupport.stream(changes).forEach(subject::onNext);
            return null;
        }).when(buildingQuery).setLocation(point, newRadius);

        TestObserver<DataChange<Building>> test = mapViewModel.loadFor(new InnavoLocation(1, 1), 2)
                .observe()
                .test();
        test.assertValueCount(0);
        mapViewModel.reloadFor(LocationConverter.toInnavoLocation(point), newRadius);
        test.assertValueCount(2);
        test.dispose();
    }
}