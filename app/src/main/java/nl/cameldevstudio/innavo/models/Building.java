package nl.cameldevstudio.innavo.models;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Building model
 */
public class Building {
    private String id;
    private String name;
    private String street;
    private String houseNumber;
    private String city;
    private String image;
    private InnavoLocation innavoLocation;

    public Building(@Nonnull String id, @Nonnull String name, @Nonnull String street, @Nonnull String housenumber, @Nonnull String city, @Nullable String image, @Nonnull InnavoLocation innavoLocation) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.houseNumber = housenumber;
        this.city = city;
        this.image = image;
        this.innavoLocation = innavoLocation;
    }

    /**
     * Get the buildings id.
     *
     * @return the buildings id.
     */
    @Nonnull
    public String getId() {
        return id;
    }

    /**
     * Get the buildings name.
     *
     * @return the buildings name.
     */
    @Nonnull
    public String getName() {
        return name;
    }

    /**
     * Get the buildings address.
     *
     * @return the buildings address.
     */
    @Nonnull
    public String getAddress() {
        return street + " " + houseNumber + ", " + city;
    }

    /**
     * Get the buildings street.
     *
     * @return the buildings street.
     */
    @Nonnull
    public String getStreet() {
        return street;
    }

    /**
     * Get the buildings street.
     *
     * @return the buildings street.
     */
    @Nonnull
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * Get the buildings street.
     *
     * @return the buildings street.
     */
    @Nonnull
    public String getCity() {
        return city;
    }

    /**
     * Get the file name of the buildings image.
     *
     * @return the file name of the buildings image.
     */
    @Nullable
    public String getImage() {
        return image;
    }

    /**
     * Get the buildings location.
     *
     * @return the buildings location.
     */
    @Nonnull
    public InnavoLocation getInnavoLocation() {
        return innavoLocation;
    }
}
