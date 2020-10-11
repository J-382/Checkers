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
    
    // Pruebas del primer mini-ciclo: Create
    @Test
    public void deberiaCrearTodoDadosTipos(){
       String[] locations = {"normal", "reverse", "isolated", "normal", "normal"};
       String[] streets = {"normal", "normal", "normal", "silent"};
       String[] signs = {"normal", "normal", "fixed"};
       int desireCont = locations.length + streets.length + signs.length;
       try{
           townC4 = new Town(500, 500, locations, streets, signs);
           int cont = townC4.allLocations().length + townC4.allStreets().length +townC4.allSigns().length;
           assertEquals(desireCont, cont);
       }catch(TownException e){
           fail("No lanzo excepción");
       }
    }
    
    @Test
    public void deberiaLanzarExcepcionTipoLocacion(){
        String[] locations = {"normal", "normals"};
        String[] streets = {};
        String[] signs = {};
        try{
            townC4 = new Town(500, 500, locations, streets, signs);
            fail("No lanzo excepción");
        }catch(TownException e){
            assertEquals(TownException.WRONG_LOCATION_TYPE, e.getMessage());
        }
    }
    
    @Test
    public void deberiaLanzarExcepcionTipoCalle(){
        String[] locations = {"normal", "normal"};
        String[] streets = {"silentt"};
        String[] signs = {};
        try{
            townC4 = new Town(500, 500, locations, streets, signs);
            fail("No lanzo excepción");
        }catch(TownException e){
            assertEquals(TownException.WRONG_STREET_TYPE, e.getMessage());
        }
    }
    
    @Test
    public void deberiaLanzarExcepcionTipoSeñal(){
        String[] locations = {"normal", "normal"};
        String[] streets = {"normal"};
        String[] signs = {"fixeddd"};
        try{
            townC4 = new Town(500, 500, locations, streets, signs);
            fail("No lanzo excepción");
        }catch(TownException e){
            assertEquals(TownException.WRONG_SIGN_TYPE, e.getMessage());
        }
    }
    
    @Test
    public void deberiaLanzarExcepcionMasCalles(){
        String[] locations = {"normal", "normal"};
        String[] streets = {"normal", "silent", "prudent"};
        String[] signs = {};
        try{
            townC4 = new Town(500, 500, locations, streets, signs);
            fail("No lanzo excepción");
        }catch(TownException e){
            assertEquals(TownException.INVALID_NUMBER_STREETS, e.getMessage());
        }
    }
    
    @Test
    public void deberiaLanzarExcepcionMasCalles2(){
        String[] locations = {"isolated", "normal"};
        String[] streets = {"normal"};
        String[] signs = {};
        try{
            townC4 = new Town(500, 500, locations, streets, signs);
            fail("No lanzo excepción");
        }catch(TownException e){
            assertEquals(TownException.INVALID_NUMBER_STREETS, e.getMessage());
        }
    }
    
    @Test
    public void deberiaLanzarExcepcionMasSeñales(){
        String[] locations = {"normal", "normal"};
        String[] streets = {"normal"};
        String[] signs = {"normal", "normal", "normal"};
        try{
            townC4 = new Town(500, 500, locations, streets, signs);
            fail("No lanzo excepción");
        }catch(TownException e){
            assertEquals(TownException.INVALID_NUMBER_DEADENDS, e.getMessage());
        }
    }
    
    @Test
    public void deberiaLanzarExcepcionMasSeñales2(){
        String[] locations = {"reverse", "normal", "isolated", "normal", "normal"};
        String[] streets = {"silent", "prudent"};
        String[] signs = {"normal", "normal"};
        try{
            townC4 = new Town(500, 500, locations, streets, signs);
            fail("No lanzo excepción");
        }catch(TownException e){
            assertEquals(TownException.INVALID_NUMBER_DEADENDS, e.getMessage());
        }
    }
    
    // Pruebas del segundo mini-ciclo: add / delete location
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
    // Pruebas del tercer mini-ciclo: add / delete street
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
    // Pruebas del cuarto mini-ciclo: add / delete sign
    
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

