
package town;


import shapes.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * The test class TownATest.
 *
 * @author  Angie Medina - Jose Perez
 * @version 24/10/20
 */
public class TownATest
{
    private Town townTest;
    public TownATest()
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
    public void pruebaUndoRedo(){
        String[] input = new String[]{"6 5", "1 2","1 3", "2 3","4 5","5 6"};
        townTest = new Town(input,true);
        townTest.addSign("ash","avocado");
        townTest.addSign("avocado","azur");
        Object[] options = {"Undo","Redo","Done"};
        int result = JOptionPane.showOptionDialog(null,"","",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        while(result != JOptionPane.CANCEL_OPTION){
            if(result == JOptionPane.YES_OPTION) townTest.undo();
            else if(result == JOptionPane.NO_OPTION) townTest.redo();
            result = JOptionPane.showOptionDialog(null,"","",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        }
        int dialogResult = JOptionPane.showConfirmDialog (null, "Is the result satisfying?","",JOptionPane.YES_NO_OPTION);
        assertTrue(dialogResult == JOptionPane.YES_OPTION);
    }
    
    @Test
    public void pruebaCallesSobrantes(){
        townTest = new Town(1000,1000);
        String[] colors = new String[]{"blue","bronze","cinnamon","cyan","lavender","sand","magenta neon","mustard","pink","parrot","watermelon"};
        townTest.addLocation(colors[0],500,300);
        townTest.addLocation(colors[1],200,200);
        townTest.addLocation(colors[2],550,100);
        townTest.addLocation(colors[3],700,200);
        townTest.addLocation(colors[4],550,550);
        townTest.addLocation(colors[5],400,750);
        townTest.addLocation(colors[6],750,550);
        townTest.addLocation(colors[7],600,750);
        townTest.addLocation(colors[8],250,500);
        townTest.addLocation(colors[9],150,750);
        townTest.addLocation(colors[10],100,600);
        townTest.addStreet(colors[0],colors[1]);
        townTest.addStreet(colors[1],colors[2]);
        townTest.addStreet(colors[2],colors[3]);
        townTest.addStreet(colors[3],colors[0]);
        townTest.addStreet(colors[0],colors[4]);
        townTest.addStreet(colors[4],colors[5]);
        townTest.addStreet(colors[4],colors[6]);
        townTest.addStreet(colors[4],colors[7]);
        townTest.addStreet(colors[5],colors[8]);
        townTest.addStreet(colors[5],colors[9]);
        townTest.addStreet(colors[7],colors[6]);
        townTest.addStreet(colors[6],colors[3]);
        townTest.addStreet(colors[10],colors[8]);
        townTest.makeVisible();
        int dialogResult = JOptionPane.showConfirmDialog (null, "Is the result satisfying?","",JOptionPane.YES_NO_OPTION);
        assertTrue(dialogResult == JOptionPane.YES_OPTION);
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        townTest.makeInvisible();
    }
}
