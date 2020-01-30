package nl.cameldevstudio.innavo.ui.building.info;

import javax.inject.Inject;

import io.reactivex.Maybe;
import nl.cameldevstudio.innavo.models.Building;
import nl.cameldevstudio.innavo.services.building.BuildingRepository;
import nl.cameldevstudio.innavo.services.image.ImageReferenceService;
import nl.cameldevstudio.innavo.ui.base.BaseViewModel;

public class BuildingInfoViewModel extends BaseViewModel {
    private BuildingRepository buildingRepository;
    private ImageReferenceService imageReferenceService;

    @Inject
    public BuildingInfoViewModel(BuildingRepository buildingRepository, ImageReferenceService imageReferenceService) {
        this.buildingRepository = buildingRepository;
        this.imageReferenceService = imageReferenceService;
    }

    public Maybe<Building> getBuilding(String id) {
        return buildingRepository.getBuilding(id);
    }

    public ImageReferenceService getImageReferenceService() {
        return imageReferenceService;
    }
}
