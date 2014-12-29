
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Minnal
 */
public class InteractivePanel extends JPanel {
    
    Grid sudokuGrid;
    String selected= "Arc Consistency+ BackTrack";
    public InteractivePanel() throws Exception
    {
        this.setPreferredSize(new Dimension(900, 600));
         sudokuGrid= new Grid();
         this.add(sudokuGrid, BorderLayout.PAGE_START);
         
         JPanel jp= new JPanel();
         jp.add(randomPlacementBttn());
         jp.add(unsolveBttn());
         jp.add(solveRandomGrid());
         jp.add(AStarSearchButton());
         jp.add(UniqueSolution());
         this.add(jp, BorderLayout.PAGE_END);
         
    }
    private JButton AStarSearchButton()
    {
    	JButton astar = new JButton("Backtracking and A*");
    	astar.addActionListener(new ActionListener() {
            @Override
           
            public void actionPerformed(ActionEvent e) {
             
                sudokuGrid.startTime= System.nanoTime();

                sudokuGrid.printAStar();
                
            }
        });
        return astar;
    }
    
    private JButton randomPlacementBttn()
    {
        JButton randomPlacement= new JButton("Place Random Numbers ");
        randomPlacement.addActionListener(new ActionListener() {
            @Override
           
            public void actionPerformed(ActionEvent e) {
            
                sudokuGrid.reset();
                sudokuGrid.randomPlacement(20);
                //sudokuGrid.printConstraints();
                //sudokuGrid.printAStar();
                
            }
        });
        return randomPlacement;
    }
    
    private JButton solveRandomGrid()
    {
         JButton solve= new JButton("Backtracking and AC3 ");
        solve.addActionListener(new ActionListener() {
            @Override
           
            public void actionPerformed(ActionEvent e) {
      
                try{
                      sudokuGrid.startTime= System.nanoTime();
              sudokuGrid.solveS(sudokuGrid.gridArray[0][0].row, sudokuGrid.gridArray[0][0].col);
                     }
                     catch( Exception ex )
            {
            }
                
               }
                        
        });
        return solve;
    }
    

   
   private JButton unsolveBttn()
   {
       JButton unsolve= new JButton("Unsolve Grid ");
       unsolve.addActionListener(new ActionListener() {
           @Override
          
           public void actionPerformed(ActionEvent e) {
            sudokuGrid.unsolveBoard();
               
           }
       });
       return unsolve;
   }
   



   private JButton UniqueSolution()
    {
      JButton unique= new JButton("Unique Solution");
        unique.addActionListener(new ActionListener() {
            @Override
            /**
             *This method ensures that whenever, this JComboBox is clicked, 
             * it updates the variable holding information about the user's choice of 
             * an algorithm
             */
           public void actionPerformed(ActionEvent e) {
                try {
                   sudokuGrid.getUniqueSol();
                    //sudokuGrid.checkUniqueness();
                    //sudokuGrid.sol();
                } catch (Exception ex) {
                    
                }

            }
        });

       
        return unique;
    }
}


