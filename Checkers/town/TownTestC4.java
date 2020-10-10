package town;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class TownTestC4.
 *
 * @author  Angie Medina - Jose Perez
 * @version 04/10/20
 */
public class TownTestC4{
    /**
     * Default constructor for test class TownTestC4
     */
    private Town townC4;
    
    public TownTestC4(){
    }
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp(){
    }
    
    @Test
    public void segunMPdeberiaVoltearLaUbicacion(){
        townC4 = new Town(500, 500);
        townC4.addLocation("reverse","blue", 100, 200);
        String[] desiredAns = new String[]{"blue","200","100"};
        assertEquals(desiredAns,townC4.allLocations()[0]);
    }
    
    @Test
    public void segunMPnoDeberiaAgregarCalles(){
        townC4 = new Town(500, 500);
        townC4.addLocation("isolated","blue",100,200);
        townC4.addLocation("normal","red",100,100);
        townC4.addLocation("normal","green",200,200);
        townC4.addStreet("blue","red");
        townC4.addStreet("blue","green");
        assertEquals(0,townC4.allStreets().length);
    }
    
    @Test
    public void segunMPnoDeberiaAgregarSeñales(){
        townC4 = new Town(500, 500);
        townC4.addLocation("normal","blue",100,200);
        townC4.addLocation("normal","red",100,100);
        townC4.addStreet("silent","blue","red");
        townC4.addSign("blue","red");
        assertEquals(0,townC4.allSigns().length);
    }
    
    @Test
    public void segunMPdeberiaAgregarLaOtraSeñal(){
        townC4 = new Town(500, 500);
        townC4.addLocation("normal","blue",100,200);
        townC4.addLocation("normal","red",100,100);
        townC4.addStreet("prudent","blue","red");
        townC4.addSign("blue","red");
        assertEquals(2,townC4.allSigns().length);
    }
    
    @Test
    public void segunMPdeberiaMoverseALaOtraLocalidad(){
        townC4 = new Town(500, 500);
        townC4.addLocation("normal","blue",100,200);
        townC4.addLocation("normal","red",100,100);
        townC4.addStreet("normal","blue","red");
        townC4.addSign("bouncy","blue","red");
        townC4.delSign("blue","red");
        String[] desiredAns = new String[]{"red","blue"};
        assertEquals(desiredAns,townC4.allSigns()[0]);
    }
    
    @Test
    public void segunMPnoDeberiaDejarseEliminar(){
        townC4 = new Town(500, 500);
        townC4.addLocation("normal","blue",100,200);
        townC4.addLocation("normal","red",100,100);
        townC4.addStreet("normal","blue","red");
        townC4.addSign("fixed","blue","red");
        String[][] desiredAns = townC4.allSigns();
        townC4.delSign("blue","red");
        assertEquals(desiredAns,townC4.allSigns());
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown(){
    }
}

