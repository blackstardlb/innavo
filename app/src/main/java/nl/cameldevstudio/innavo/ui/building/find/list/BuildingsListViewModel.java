package nl.cameldevstudio.innavo.ui.building.find.list;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import nl.cameldevstudio.innavo.models.Building;
import nl.cameldevstudio.innavo.services.building.BuildingRepository;
import nl.cameldevstudio.innavo.ui.base.BaseViewModel;

public class BuildingsListViewModel extends BaseViewModel {
    private String queryText = "";
    private BehaviorSubject<List<Building>> results = BehaviorSubject.create();
    private BuildingRepository buildingRepository;
    private Disposable query;

    @Inject
    public BuildingsListViewModel(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public void setQueryText(@Nonnull String queryText) {
        this.queryText = queryText;
        results.onNext(new ArrayList<>());
        if (!queryText.isEmpty()) {
            if (query != null) {
                query.dispose();
            }
            query = buildingRepository
                    .findBuildings(queryText)
                    .subscribe(buildings -> results.onNext(buildings));
            getCompositeDisposable().add(
                    query
            );
        }
    }

    Observable<List<Building>> queryResults() {
        return results;
    }

    public String getQueryText() {
        return queryText;
    }
}
