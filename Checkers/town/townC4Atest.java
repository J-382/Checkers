package town;
import Shapes.*;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class townC4Atest.
 *
 * @author  Angie Medina - Jose Perez
 * @version 05/10/20
 */
public class townC4Atest{
    /**
     * Default constructor for test class townC4Atest
     */
    public townC4Atest(){
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
    public void deberiaHaberUnaSeñalConColorOro(){
        Town townC4 = new Town(500, 500);
        Canvas canvas = Canvas.getCanvas();
        townC4.makeVisible();
        canvas.wait(500);
        townC4.addLocation("normal", "blue", 20, 20);
        canvas.wait(500);
        townC4.addLocation("normal", "red", 150, 150);
        canvas.wait(500);
        townC4.addLocation("normal", "green", 20, 150);
        canvas.wait(500);
        townC4.addStreet("prudent", "blue", "green");
        canvas.wait(500);
        townC4.addStreet("normal", "blue", "red");
        canvas.wait(500);
        townC4.addSign("normal", "blue", "green");
        canvas.wait(500);
        townC4.addSign("normal", "blue", "red");
        canvas.wait(1500);
        String[][] allSigns = townC4.allSigns();
    }
    
    @Test
    public void test1(){
        try{
            String[] locations = {"normal", "reverse", "isolated", "normal", "normal"};
            String[] streets = {"normal", "normal", "normal", "prudent", "prudent", "silent"};
            String[] signs = {"normal", "bouncy", "fixed"};
            Town townC4 = new Town(500, 500, locations, streets, signs);
        }catch(TownException e){}
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

