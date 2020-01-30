package nl.cameldevstudio.innavo.models;

import javax.annotation.Nonnull;

/**
 * This class represents a change in data.
 *
 * @param <T> The type of data that is changes.
 */
public class DataChange<T> {
    private T data;
    private DataChangeType type;

    public DataChange(@Nonnull T data, @Nonnull DataChangeType type) {
        this.data = data;
        this.type = type;
    }

    /**
     * Get the changed data.
     *
     * @return the changed data
     */
    @Nonnull
    public T getData() {
        return data;
    }

    /**
     * Get the change type.
     *
     * @return the change type.
     */
    @Nonnull
    public DataChangeType getType() {
        return type;
    }
}
