package town;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
/**
 * The test class TownTestC2.
 *
 * @author  Angie Medina - Jose Perez
 * @version 14/09/20
 */
public class TownTestC2
{
    private Town townC2;
    /**
     * Default constructor for test class TownTestC2
     */
    public TownTestC2()
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
    
    // Pruebas de la solucion
    @Test
    public void deberiaDarLoOpuestoElPrimerOutput(){
        String[] input = {"6 5", "1 2","1 3", "2 3","4 5","5 6"};
        Town townC2 = new Town(input);
        ArrayList<String> desireAnswer = new ArrayList<String> ();
        townC2.addSign("almond", "amaranth");
        townC2.addSign("almond", "ambar");
        townC2.addSign("amaranth", "ambar");
        townC2.addSign("ash", "avocado");
        townC2.addSign("azur","avocado");
        desireAnswer.add("almond amaranth");
        desireAnswer.add("almond ambar");
        desireAnswer.add("amaranth ambar");
        ArrayList<String> answer = new ArrayList<String> ();
        String[][] temp = townC2.wrongSignals();
        for (String[] value : townC2.wrongSignals()){
            if (value[0] != null && value[1] != null){
                answer.add(value[0] + " " + value[1]);
            }
        }
        assertEquals(desireAnswer.toString().replace("[","").replace("]",""), answer.toString().replace("[","").replace("]",""));
    }
    
    
    @Test
    public void deberiaDarLoOpuestoAlSegundoOutput(){
        String[] input = {"8 8", "1 2", "1 3", "2 3", "3 4", "1 5", "1 6", "6 7", "6 8"};
        Town townC2 = new Town(input);
        townC2.addSign("almond", "amaranth");
        townC2.addSign("almond", "ambar");
        townC2.addSign("amaranth", "ambar");
        townC2.addSign("ambar", "ash");
        townC2.addSign("almond", "avocado");
        townC2.addSign("almond", "azur");
        townC2.addSign("azur", "blue");
        townC2.addSign("azur", "blue light");
        ArrayList<String> desireAnswer = new ArrayList<String> ();
        desireAnswer.add("almond amaranth");
        desireAnswer.add("almond ambar");
        desireAnswer.add("amaranth ambar");
        desireAnswer.add("azur blue");
        desireAnswer.add("azur blue light");
        ArrayList<String> answer = new ArrayList<String> ();
        for (String[] value : townC2.wrongSignals()){
            if (value[0] != null && value[1] != null){
                answer.add(value[0] + " " + value[1]);
            }
        }
        assertEquals(desireAnswer.toString(), answer.toString());
    }
            
    // Pruebas del primer miniciclo: Create
    @Test
    public void deberiaCrearseVacia(){
        townC2 = new Town(500, 500);
        assertEquals(0, townC2.allLocations().length + townC2.allStreets().length + townC2.allSigns().length);
    }
    
    @Test
    public void deberiaCrearConUnNumeroEspecifico(){
        int nLocations = 3, nStreets = 3, nSigns = 0;
        int contador = nLocations + nStreets + nSigns;
        String[] input = {"3 3", "1 2", "1 3", "2 3"};
        townC2 = new Town(input);
        assertEquals(contador, townC2.allLocations().length + townC2.allStreets().length);
    }
    
    // Pruebas del segundo miniciclo: add/delete location
    @Test
    public void deberiaAgregarUnaLocacion(){
        townC2 = new Town(500, 500);
        int x = 40, y = 40;
        String color = "almond";
        int before = townC2.allLocations().length;
        townC2.addLocation(color, x, y);
        int after = townC2.allLocations().length;
        assertEquals(before+1, after);
    }
    
    @Test
    public void deberiaEliminarUnaLocacion(){
        townC2 = new Town(500, 500);
        int x = 40, y = 40;
        String color = "almond";
        townC2.addLocation(color, x, y);
        int before = townC2.allLocations().length;
        townC2.delLocation(color);
        int after = townC2.allLocations().length;
        assertEquals(before-1, after);
    }
    
    // Pruebas del tercer miniciclo: add/delete street
    @Test
    public void deberiaAgregarUnaCalle(){
        townC2 = new Town(500, 500);
        int x1 = 40, y1 = 40, x2 = 100, y2 = 100;
        String color1 = "almond", color2 = "amaranth"; 
        int before = townC2.allStreets().length;
        townC2.addLocation(color1, x1, y1); townC2.addLocation(color2, x2, y2);
        townC2.addStreet(color1, color2);
        int after = townC2.allStreets().length;
        assertEquals(before+1, after);
    }
    
    @Test
    public void deberiaEliminarUnaCalle(){
        townC2 = new Town(500, 500);
        int x = 40, y = 40;
        String color = "almond";
        townC2.addLocation(color, x, y);
        int before = townC2.allLocations().length;
        townC2.delLocation(color);
        int after = townC2.allLocations().length;
        assertEquals(before-1, after);
    }
    
    @Test
    public void deberiaEliminarUnaCalleSobrante(){
        townC2 = new Town(500, 500);
        townC2.addLocation("sand", 40, 40);
        townC2.addLocation("parrot", 90, 110);
        townC2.addLocation("wine", 140, 40);
        townC2.addStreet("sand", "parrot");
        townC2.addStreet("sand", "wine");
        townC2.addStreet("parrot", "wine");
        int before = townC2.allStreets().length;
        townC2.delStreet();
        int after = townC2.allStreets().length;
        boolean flag = true;
        for (String[] pair : townC2.allStreets()){
            if (pair[0].equals("sand") && pair[1].equals("wine")){
                flag = false;
            }
        }
        assertTrue(flag && (before - 1) == after);
    }
        
    // Pruebas del cuarto miniciclo: add/delete sign
    @Test
    public void deberiaAgregarUnaSeñal(){
        String[] input = {"3 3", "1 2", "1 3", "2 3"};
        townC2 = new Town(input);
        int before = townC2.allSigns().length;
        townC2.addSign("almond", "amaranth");
        int after = townC2.allSigns().length;
        assertEquals(before+1, after);
    }
    
    @Test
    public void deberiaEliminarUnaSeñal(){
        townC2 = new Town(500, 500);
        int x = 40, y = 40;
        String color = "almond";
        townC2.addLocation(color, x, y);
        int before = townC2.allLocations().length;
        townC2.delLocation(color);
        int after = townC2.allLocations().length;
        assertEquals(before - 1, after);
    }
    
    @Test
    public void deberiaEliminarUnaSeñalSobrante(){
        townC2 = new Town(500, 500);
        townC2.addLocation("sand", 40, 40);
        townC2.addLocation("parrot", 90, 110);
        townC2.addLocation("wine", 140, 40);
        townC2.addStreet("sand", "parrot");
        townC2.addStreet("sand", "wine");
        townC2.addStreet("parrot", "wine");
        townC2.addSign("sand", "wine");
        int before = townC2.allSigns().length;
        townC2.delSign();
        int after = townC2.allSigns().length;
        boolean flag = true;
        for (String[] pair : townC2.allSigns()){
            if (pair[0].equals("sand") && pair[1].equals("wine")){
                flag = false;
            }
        }
        assertTrue(flag && (before - 1) == after);
    }
    // Pruebas del quinto miniciclo: undo/redo
    @Test
    public void noDeberiaTenerElementos(){
        Town town = new Town(500,500);
        town.addLocation("blue",100,100);
        town.addLocation("red",100,200);
        town.addStreet("blue","red");
        town.addLocation("green",200,200);
        town.addStreet("blue","green");
        town.addSign("blue","green"); 
        for(int i = 0; i < 6; i++)town.undo();
        boolean locations = town.allLocations().length==0;
        boolean streets = town.allStreets().length==0;
        boolean signs = town.allSigns().length==0;
        boolean assertBool = locations && streets && signs;
        assertTrue(assertBool);
    }
    
    @Test
    public void DeberiaTenerLosMismosElementos(){
        Town town = new Town(500,500);
        town.addLocation("blue",100,100);
        town.addLocation("red",100,200);
        town.addStreet("blue","red");
        town.addLocation("green",200,200);
        town.addStreet("blue","green");
        String[][] preUndoLocations = town.allLocations(), preUndoStreets = town.allStreets(), preUndoSigns = town.allSigns();
        for(int i = 0; i < 5; i++)town.undo();
        for(int i = 0; i < 5; i++)town.redo();
        boolean locations = preUndoLocations.length == town.allLocations().length;
        boolean streets = preUndoStreets.length == town.allStreets().length;
        boolean signs = preUndoSigns.length == town.allSigns().length;        
        boolean assertBool = locations && streets && signs;
        assertTrue(assertBool);
    }
    // Pruebas del sexto miniciclo: consulta
    @Test
    public void noDeberiaRealizarLaUltimaAccion(){
        townC2 = new Town(500, 500);
        townC2.delLocation("sand");
        assertFalse(townC2.ok());
    }
    
    @Test
    public void deberiaRealizarLaUltimaAccion(){
        townC2 = new Town(500, 500);
        townC2.addLocation("sand", 40, 40);
        assertTrue(townC2.ok());
    }
    
    @Test
    public void deberiaTenerUnNumeroEspecificoDeLocaciones(){
        String[] input = {"3 3", "1 2", "1 3", "2 3"};
        townC2 = new Town(input);
        int cont = Integer.parseInt(input[0].split(" ")[0]);
        assertEquals(cont, townC2.allLocations().length);
    }
    
    @Test
    public void noDeberiaSerSensibleAMayusculasLocaciones(){
        townC2 = new Town(500, 500);
        townC2.addLocation("SAND", 40, 40);
        assertEquals("sand", townC2.allLocations()[0][0]);
    }
    
    @Test
    public void deberiaEstarOrdenadoYEnMinusculasLocaciones(){
        townC2 = new Town(500, 500);
        townC2.addLocation("sand", 40, 40);
        townC2.addLocation("crimson", 100, 100);
        townC2.addLocation("cyan", 200, 200);
        ArrayList<String> desireAnswer = new ArrayList<String>();
        desireAnswer.add("crimson 100 100");
        desireAnswer.add("cyan 200 200");
        desireAnswer.add("sand 40 40");
        ArrayList<String> answer = new ArrayList<String> ();
        for (String[] value : townC2.allLocations()) answer.add(value[0] + " " + value[1] + " " +value[2]);
        assertEquals(desireAnswer, answer);
    }
    
    @Test
    public void deberiaTenerUnNumeroEspecificoDeCalles(){
        String[] input = {"3 3", "1 2", "1 3", "2 3"};
        townC2 = new Town(input);
        int cont = Integer.parseInt(input[0].split(" ")[1]);
        assertEquals(cont, townC2.allStreets().length);
    }
    
    @Test
    public void noDeberiaSerSensibleAMayusculasCalles(){
        townC2 = new Town(500, 500);
        townC2.addLocation("sand", 40, 40);
        townC2.addLocation("crimson", 100, 100);
        townC2.addLocation("cyan", 200, 200);
        townC2.addStreet("SAND", "crimson");
        townC2.addStreet("CRimson", "cyan");
        ArrayList<String> desireAnswer = new ArrayList<String>();
        desireAnswer.add("crimson cyan");
        desireAnswer.add("crimson sand");
        ArrayList<String> answer = new ArrayList<String>();
        for (String[] value : townC2.allStreets()) answer.add(value[0] + " " + value[1]);
        assertEquals(desireAnswer, answer);
    }
    
    @Test
    public void deberiaEstarOrdenadoYEnMinusculasCalles(){
        String[] input = {"3 3", "1 2", "1 3", "2 3"};
        townC2 = new Town(input);
        ArrayList<String> desireAnswer = new ArrayList<String>();
        desireAnswer.add("almond amaranth");
        desireAnswer.add("almond ambar");
        desireAnswer.add("amaranth ambar");
        ArrayList<String> answer = new ArrayList<String> ();
        for (String[] value : townC2.allStreets()) answer.add(value[0] + " " + value[1]);
        assertEquals(desireAnswer, answer);
    }
    
    @Test
    public void deberiaEstarOrdenadoYEnMinusculasSignos(){
        townC2 = new Town(500, 500);
        townC2.addLocation("sand", 40, 40);
        townC2.addLocation("crimson", 100, 100);
        townC2.addLocation("cyan", 200, 200);
        townC2.addStreet("SAND", "crimson");
        townC2.addStreet("CRimson", "cyan");
        townC2.addSign("CRIMSON", "cyan");
        townC2.addSign("CRIMSOn", "SAnd");
        ArrayList<String> desireAnswer = new ArrayList<String>();
        desireAnswer.add("crimson cyan");
        desireAnswer.add("crimson sand");
        ArrayList<String> answer = new ArrayList<String>();
        for (String[] value : townC2.allSigns()) answer.add(value[0] + " " + value[1]);
        assertEquals(desireAnswer, answer);
    }
    
    // Pruebas del septimo miniciclo: make visible/invisible
    @Test
    public void noDeberiaSerVisible(){
        townC2 = new Town(500, 500);
        assertFalse(townC2.isVisible());
    }
    
    @Test
    public void deberiaSerVisible(){
        townC2 = new Town(500, 500);
        townC2.makeVisible();
        assertTrue(townC2.isVisible());
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
