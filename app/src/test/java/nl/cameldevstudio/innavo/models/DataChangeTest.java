package nl.cameldevstudio.innavo.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataChangeTest {
    private DataChange<Object> dataChange;
    private Object testObject;

    @Before
    public void setUp() throws Exception {
        testObject = new Object();
        dataChange = new DataChange<>(testObject, DataChangeType.ADDED);
    }

    @Test
    public void getData() {
        Object expected = testObject;
        Object result = dataChange.getData();
        assertEquals(expected, result);
    }

    @Test
    public void getType() {
        DataChangeType expected = DataChangeType.ADDED;
        DataChangeType result = dataChange.getType();
        assertEquals(expected, result);
    }
}