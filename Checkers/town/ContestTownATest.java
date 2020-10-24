package town;



import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class ContestTownATest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ContestTownATest
{
    /**
     * Default constructor for test class ContestTownATest
     */
    private TownContest TownTest;
    public ContestTownATest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        
    }

    @Test
    public void segunMPdeberiaSerMayorElTiempo(){
        TownContest TownTestSlow = new TownContest();
        String[] input = new String[]{"6 5","1 2","1 3","2 3","4 5","5 6"};
        long startTime = System.currentTimeMillis();
        TownTestSlow.simulate(input, true);
        long endTime = System.currentTimeMillis();
        long slowTimeElapsed = endTime - startTime;
        TownTest = new TownContest();
        startTime = System.currentTimeMillis();
        TownTest.simulate(input, false);
        endTime = System.currentTimeMillis();
        long TimeElapsed = endTime - startTime;
        assertTrue(slowTimeElapsed > TimeElapsed);
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}
