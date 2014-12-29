
import java.util.HashSet;
import java.util.Set;
import javax.swing.JLabel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Minnal
 */
public class Cell extends JLabel {
    
    Set domains;
    int row;
    int col;
    String val;
    Set neighbors;
    int[]rowcol = new int[2];
    
    public Cell(int rowNum, int colNum, String text )
            
    {
        neighbors= new HashSet();
        domains=new HashSet();
        domains.add(1);
        domains.add(2);
        domains.add(3);
        domains.add(4);
        domains.add(5);
        domains.add(6);
        domains.add(7);
        domains.add(8);
        domains.add(9);
        
        row=rowNum;
        col= colNum;
        val=text;
        rowcol[0]=row;
        rowcol[1]=col;
        this.setText(val);
    }
    
    public Cell (int rowNum, int colNum)
    {
         neighbors= new HashSet();
        domains=new HashSet();
        domains.add(1);
        domains.add(2);
        domains.add(3);
        domains.add(4);
        domains.add(5);
        domains.add(6);
        domains.add(7);
        domains.add(8);
        domains.add(9);
        
        row=rowNum;
        col= colNum;
        String text= Integer.toString(0);
        val=text;
        this.setText(val);
        
        
        
        
    }
    public void setValue(String text)
    {
        this.val=text;
        this.setText(val);
    }
}
