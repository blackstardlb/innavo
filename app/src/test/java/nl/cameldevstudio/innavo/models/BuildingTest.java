package nl.cameldevstudio.innavo.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BuildingTest {

    private Building testBuilding;

    @Before
    public void setUp() throws Exception {
        testBuilding = new Building("id", "name", "street", "housenumber", "city","image", new InnavoLocation(0, 0));
    }

    @Test
    public void getId() {
        String expected = "id";
        String result = testBuilding.getId();
        assertEquals(expected, result);
    }

    @Test
    public void getName() {
        String expected = "name";
        String result = testBuilding.getName();
        assertEquals(expected, result);
    }

    @Test
    public void getAddress() {
        String expected = "address";
        String result = testBuilding.getAddress();
        assertEquals(expected, result);
    }

    @Test
    public void getImage() {
        String expected = "image";
        String result = testBuilding.getImage();
        assertEquals(expected, result);
    }

    @Test
    public void getNullImage() {
        testBuilding = new Building("", "",  "","","",null, new InnavoLocation(0, 0));
        String result = testBuilding.getImage();
        assertNull(result);
    }

    @Test
    public void getInnavoLocation() {
        InnavoLocation expected = new InnavoLocation(0, 0);
        InnavoLocation result = testBuilding.getInnavoLocation();
        assertEquals(expected, result);
    }
}