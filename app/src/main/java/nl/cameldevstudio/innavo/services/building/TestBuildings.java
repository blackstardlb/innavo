package nl.cameldevstudio.innavo.services.building;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import nl.cameldevstudio.innavo.models.Building;
import nl.cameldevstudio.innavo.models.InnavoLocation;

public class TestBuildings {
    private static List<Building> testBuildings = Arrays.asList(
            new Building("NgkmLSFjUBISihrL6LRf", "Sportcampus Zuiderpark",
                    "Mr. P. Droogleever Fortuynweg",
                    "22",
                    "Den Haag",
                    "zuiderpark.jpg",
                    new InnavoLocation(52.0556851, 4.289584999999988)),
            new Building("ZjCvxCOl7BtcogbFsTxP", "Haagse Hogeschool Locatie Zoetermeer",
                    "Bleiswijkseweg",
                    "37A",
                    "Zoetermeer",
                    "haagse.jpg",
                    new InnavoLocation(52.046501, 4.5146274999999605))
    );

    /**
     * Get test buildings
     *
     * @return an unmodifiable {@link List} of {@link Building}
     */
    @Nonnull
    public static List<Building> get() {
        return Collections.unmodifiableList(testBuildings);
    }
}
