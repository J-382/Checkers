package town;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

/**
 * The test class TownContestTest.
 *
 * @author  ...
 * @version 25/09/20
 */
public class TownContestTest
{
    private TownContest TownTest;
    private ArrayList<String> desireAnswer;
    private ArrayList<String> answer;
    /**
     * Default constructor for test class TownContestTest
     */
    public TownContestTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp(){
        desireAnswer = new ArrayList<>();
        answer = new ArrayList<>();
    }

    @Test
    public void segunMPdeberiaDarElPrimerOutput(){
        TownTest = new TownContest();
        String[] input = new String[]{"6 5","1 2","1 3","2 3","4 5","5 6"};
        String[] solveAnswer = TownTest.solve(input);
        answer.clear();
        for (String s : solveAnswer) answer.add(s);
        desireAnswer.clear();
        desireAnswer.add("2");
        desireAnswer.add("4 5");
        desireAnswer.add("6 5");
        assertEquals(desireAnswer.toString(), answer.toString());
    }
    
    @Test
    public void segunMPdeberiaDarElSegundoOutput(){
        TownTest = new TownContest();
        String[] input = new String[]{"8 8", "1 2", "1 3", "2 3", "3 4", "1 5", "1 6", "6 7", "6 8"};
        String[] solveAnswer = TownTest.solve(input);
        answer.clear();
        for (String s : solveAnswer) answer.add(s);
        desireAnswer.clear();
        desireAnswer.add("3");
        desireAnswer.add("1 5");
        desireAnswer.add("1 6");
        desireAnswer.add("3 4");
        assertEquals(desireAnswer.toString(), answer.toString());
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


