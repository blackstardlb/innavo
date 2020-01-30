package nl.cameldevstudio.innavo.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InnavoLocationTest {
    private InnavoLocation innavoLocation;

    @Before
    public void setUp() throws Exception {
        innavoLocation = new InnavoLocation(16.63, 12.23);
    }

    @Test
    public void getLongitude() {
        double expected = 12.23;
        double result = innavoLocation.getLongitude();
        assertEquals(expected, result, 0);
    }

    @Test
    public void getLatitude() {
        double expected = 16.63;
        double result = innavoLocation.getLatitude();
        assertEquals(expected, result, 0);
    }
}