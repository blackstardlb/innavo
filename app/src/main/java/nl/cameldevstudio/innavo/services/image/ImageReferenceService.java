package nl.cameldevstudio.innavo.services.image;

import com.google.firebase.storage.StorageReference;

public interface ImageReferenceService {
    StorageReference getReference(String path);

    StorageReference getBuildingImageReference(String imageName);
}
