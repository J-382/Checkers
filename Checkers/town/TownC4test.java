package town;



import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class TownC4test.
 *
 * @author  ..
 * @version (a version number or a date)
 */
public class TownC4test{
    private Town townC4;
    /**
     * Default constructor for test class TownC4test
     */
    public TownC4test(){}

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp(){}
    
    @Test
    public void segunMPdeberiaInvertirSusPosiciones(){
        Town townC4 = new Town(500, 500);
        int x = 20, y = 30;
        townC4.addLocation("reverse", "green", x, y);
        Location location = townC4.getLastLocation();
        int[] coord = location.getLocation();
        assertTrue(x == coord[1] && y == coord[0]);
    }
    
    @Test
    public void segunMPnoDeberiaAceptarUnaCalle(){
        Town townC4 = new Town(500, 500);
        int x = 100, y = 100;
        townC4.addLocation("isolated", "green", x, y);
        townC4.addLocation("red", x + 30, y + 30);
        townC4.addStreet("green", "red");
        assertFalse(townC4.ok());
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown(){}
}
