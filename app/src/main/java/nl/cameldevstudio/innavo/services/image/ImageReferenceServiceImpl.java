package nl.cameldevstudio.innavo.services.image;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ImageReferenceServiceImpl implements ImageReferenceService {
    private FirebaseStorage firebaseStorage;

    public ImageReferenceServiceImpl(FirebaseStorage firebaseStorage) {
        this.firebaseStorage = firebaseStorage;
    }

    @Override
    public StorageReference getReference(String path) {
        return firebaseStorage.getReference(path);
    }

    @Override
    public StorageReference getBuildingImageReference(String imageName) {
        return getReference("images/buildings/" + imageName);
    }
}
