package town;



import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javax.swing.JOptionPane;

/**
 * The test class ContestTownATest.
 *
 * @author  Angie Medina - Jose Perez
 * @version 24/10/20
 */
public class ContestTownATest
{
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
    public void pruebaSimulador(){
        TownContest TownTestSlow = new TownContest();
        String[] input = new String[]{"6 5","1 2","1 3","2 3","4 5","5 6"}; //Primer Caso
        TownTestSlow.simulate(input, true);
        int dialogResult = JOptionPane.showConfirmDialog (null, "Is the result satisfying?","",JOptionPane.YES_NO_OPTION);
        assertTrue(dialogResult==JOptionPane.YES_OPTION);
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
