package Shapes;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.Toolkit;
import java.awt.Dimension;
/**
 * Write a description of class RGB here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RGB
{
    // instance variables - replace the example below with your own
    public static final String[] colorsList = {"almond","sand","chesnut","bronze","cinnamon","tawny","rufous","red","crimson","wine","carmine","persian",
        "watermelon","orange khaki","salmon","orange vivid","ambar","yellow","gold","napoleto","mustard","green","green mid","sinople","avocado",
        "malachite","parrot","cyan","turquoise","blue light","blue","azur","zero","amaranth","lavender","purple","purpure","malva","violet",
        "magenta","pink","fuchsia","crisvilu","magenta neon","steel","ash","grey","cold","linen","white"};
    private boolean transparencyMode;
    /**
     * Provides a conversor of colors, given a color name it will return its equivalent to RGB format
     */
    public RGB(){
        transparencyMode = false;
    }
    
    /**
     * Returns the RGB format for the given color.
     * @param color A string with the color's name
     * @returns String with RGB format for the given color
       */
    public String getRGB(String color){
        String rgb="0 0 0";
        if(color.contains("-")) transparencyMode = true;
        color = color.replace("-","");
        switch(color.toLowerCase()){
            //Brown variations
            case "almond":
               rgb = "239 222 205"; 
               break;
            case "sand":
               rgb = "236 226 198"; 
               break;
            case "chesnut":
               rgb = "128 0 0"; 
               break;
            case "bronze":
               rgb = "205 127 50"; 
               break;
            case "cinnamon":
               rgb = "153 107 66"; 
               break;
            case "tawny":
               rgb = "188 134 72"; 
               break;
            case "rufous":
               rgb = "203 109 81"; 
               break;
               
            //Red variations
            case "red":
               rgb = "255 0 0"; 
               break;
            case "crimson":
               rgb = "229 26 76"; 
               break;
            case "wine":
               rgb = "144 0 32"; 
               break;
            case "carmine":
               rgb = "195 11 78"; 
               break;
            case "persian":
               rgb = "203 29 17"; 
               break;
            case "watermelon":
               rgb = "192 62 62"; 
               break;
           
            //Orange variations
            case "orange khaki":
               rgb = "243 141 60"; 
               break;
            case "salmon":
               rgb = "250 128 114"; 
               break;
            case "orange vivid":
               rgb = "250 128 114"; 
               break;
            case "ambar":
               rgb = "226 137 58"; 
               break;
               
            //Yellow variations
            case "yellow":
                rgb = "255 255 0";
                break;
            case "gold":
                rgb = "255 215 0";
                break;
            case "napoleto":
                rgb = "255 215 0";
                break;
            case "mustard":
                rgb = "255 219 88";
                break;
            
            //Green variations
            case "green":
                rgb = "0 255 0";
                break;
            case "green mid":
                rgb = "0 128 0";
                break;
            case "sinople":
                rgb = "0 181 100";
                break;
            case "avocado":
                rgb = "51 255 151";
                break;
            case "malachite":
                rgb = "11 218 81";
                break;
            case "parrot":
                rgb = "82 184 48";
                break;
            
            //Cyan variations
            case "cyan":
                rgb = "0 255 255";
                break;
            case "turquoise":
                rgb = "64 224 208";
                break;
            case "blue light":
                rgb = "135 206 250";
                break;
                
            //Blue variations
            case "blue":
                rgb = "0 0 255";
                break;
            case "azur":
                rgb = "0 153 255";
                break;
            case "zero":
                rgb = "0 72 186";
                break;
            
            //Purple variations
            case "amaranth":
                rgb = "159 43 104";
                break;
            case "lavender":
                rgb = "181 126 220";
                break;
            case "purple":
                rgb = "87 35 100";
                break;
            case "purpure":
                rgb = "106 13 173";
                break;
            case "malva":
                rgb = "220 208 255";
                break;
            case "violet":
                rgb = "127 0 255";
                break;
                
            //Pink variations
            case "magenta":
                rgb = "255 0 255";
                break;
            case "pink":
                rgb = "253 108 158";
                break;
            case "fuchsia":
                rgb = "255 0 128";
                break;
            case "crisvilu":
                rgb = "252 208 180";
                break;
            case "magenta neon":
                rgb = "255 95 162";
                break;

            //Grey variations
            case "steel":
                rgb = "214 214 214";
                break;
            case "ash":
                rgb = "176 181 188";
                break;
            case "grey":
                rgb = "128 128 128";
                break;
            case "cold":
                rgb = "76 96 99";
                break;
            case "linen":
                rgb = "215 208 183";
                break;    
            case "antique":
                rgb = "250 235 215";
                break;
            case "white":
                rgb = "255 255 255";
                break;
            case "black":
                rgb = "0 0 0";
                break;
                
            default:
                rgb = "No matches";
                break;
        }
        if(transparencyMode) rgb += " 100";
        transparencyMode = false;
        return rgb;
    }

    /**
     * Shows a window with a list of available colors.  
     */
    public static void showColors(){
        JList lista = new JList(colorsList);
        JScrollPane scrollPane = new JScrollPane(lista);
        scrollPane.setPreferredSize( new Dimension(200, 200));
        JOptionPane.showMessageDialog(null, scrollPane, "List of Colors", JOptionPane.NO_OPTION);
    }
    
    public void setTransparencyMode(boolean mode){
        transparencyMode = mode;
    }
}
