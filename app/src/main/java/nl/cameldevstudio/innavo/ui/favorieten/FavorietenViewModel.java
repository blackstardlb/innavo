package nl.cameldevstudio.innavo.ui.favorieten;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import nl.cameldevstudio.innavo.services.image.ImageReferenceService;
import nl.cameldevstudio.innavo.ui.base.BaseViewModel;

public class FavorietenViewModel extends BaseViewModel {
    private ImageReferenceService imageReferenceService;

    private String query;

    @Inject
    public FavorietenViewModel(ImageReferenceService imageReferenceService) {
        this.imageReferenceService = imageReferenceService;
    }

    public ImageReferenceService getImageReferenceService() {
        return imageReferenceService;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(@Nonnull String query) {
        this.query = query;
    }
}
