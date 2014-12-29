
import javax.swing.JFrame;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Minnal, Barsha
 */
public class Main  {
    
    
    public static void main (String[] args) throws Exception
    {
        JFrame jf= new JFrame("Grid");
        jf.add(new InteractivePanel());
        jf.setVisible(true);
        jf.setSize(900, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}
