
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Minnal
 */
public class Grid extends JPanel{
    
    Cell[][] gridArray= new Cell[9][9];
     String[][] copyArray;
    public static int SIZE =9;
   Set fixed= new HashSet();
   List<String> values=  new ArrayList<String>() ;
     long startTime;
int counter=0;
     PriorityQueue<Pair<Cell, Integer>> queue = new PriorityQueue<Pair<Cell, Integer>>(1000, 
    		        new Comparator<Pair<Cell, Integer>>() {
    		          public int compare(Pair<Cell, Integer> a, Pair<Cell, Integer> b) {
    		            return a.c - b.c;
    		          }
    		        }
    		      );
    
    public Grid() throws Exception
            {
                startTime=0;
               this.setLayout(new GridLayout(9,9));
               generateGrid();
               this.setPreferredSize(new Dimension(450, 300));
        
            
            }
    
    /**
     * Method that generates the 9x9 grid with the JLabel cells in it
     */
    public void generateGrid(){
        
        for (int i=0; i<9; i++)
        {
            
            
            for (int j=0; j<9; j++)
            {
                Cell cell= new Cell(i,j);
                gridArray[i][j]= cell;
                
                {
                    if (j==2||j==5)
                    {
                         gridArray[i][j].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
                    }
                    
                   if (i==2||i==5)
                       {
                         gridArray[i][j].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
                          if (j==2||j==5)
                          {
                              gridArray[i][j].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
                          }
                    }
                }
                
                this.add(cell);
               // System.out.println("domains[i][j] "+domains[i][j].toString());
                
            }
           
        } 
        
        for (int i=0; i<9; i++)
        {
            
            
            for (int j=0; j<9; j++)
            {
                
                getNeighbor(gridArray[i][j]);
                
                
            }
           
        } 
        
        
        
        
    }
    
    public boolean redundantFilled(Set<Cell> myFilled, Cell myCell)
    {
        for (Cell cell : myFilled) {
            if (cell.row==myCell.row && cell.col==myCell.col)
            {
                return true;
            }
        }
        return false;
    }
    public void randomPlacement(int num)
    {   
        Set filled= new HashSet();
        fixed=new HashSet();
        Random r= new Random();
        Cell newCell;
        while (filled.size()<=num)
        {
            
            int x= r.nextInt(9) + 0;
            int y= r.nextInt(9) + 0;
            newCell= new Cell(x,y,"0");
            
            Iterator iter= filled.iterator();
           
          if (!filled.isEmpty()){
          if(redundantFilled(filled, newCell) )
           {
               while(filled.contains(newCell))
               { 
                   x= r.nextInt(9) + 0;
                   y= r.nextInt(2) + 0;
                   newCell= new Cell(x,y);
               }
               }filled.add(newCell);
               fixed.add(gridArray[newCell.row][ newCell.col]);
               
           }
       
            
            
            else 
            {
            
           filled.add(newCell);
           fixed.add(gridArray[newCell.row][newCell.col]);
           //System.out.println("the filled set has: "+filled());
            }

          
        newCell.domains= checkRowColConstraints(newCell.row, newCell.col);
       
        //if (!newCell.domains.isEmpty()){
        setValue(newCell,newCell.domains );
        // arcs.add(newCell);
        //System.out.println("At the end!");
        
        
        
    
}
}
    private void setValue(Cell cell, Set cellDomain)
    {
        Random r = new Random();
        int i=0;
        int num=0;
        if (!cellDomain.isEmpty())
        { int random= r.nextInt(9-1+1) + 1;
         if (cellDomain.contains(random))
         {
            cell.domains.remove(random);}
         
         else {
             while (!cellDomain.contains(random))
             {
                 random= r.nextInt(9-1+1) + 1;
             }
             
              cell.domains.remove(random);
         }
           // System.out.println("The value is: " + Integer.toString(num));
            
             gridArray[cell.row][cell.col].setForeground(Color.red);
            gridArray[cell.row][cell.col].setValue(Integer.toString(random));
            cell.domains.clear();
           // System.out.println("The new cell domain is : "+ cell.domains);
            
            
            
        }  
            
        
    }
    public Set checkRowColConstraints(int i, int j)
    
    {
        
        Set setForLoc = new HashSet();
        
        for (int k=1; k<10; k++)
        {
            setForLoc.add(Integer.toString(k));
        }
                 for (int k=0; k<i; k++)
                {
                if(setForLoc.contains(gridArray[k][j].getText()))
                {
                  setForLoc.remove(gridArray[k][j].getText());
                
                }
                }
                 
                 
                for (int k=8; k>i; k--)
                {
                    if(setForLoc.contains(gridArray[k][j].getText()))
                {
                  setForLoc.remove(gridArray[k][j].getText());
                }
                
                }
                
                
                 for (int k=0; k<j; k++)
                 {
                     if(setForLoc.contains(gridArray[i][k].getText()))
                {
                  setForLoc.remove(gridArray[i][k].getText());
                }
                 }
                 
                 
                 for (int k=8; k>j; k--)
                 {
                     if(setForLoc.contains(gridArray[i][k].getText()))
                {
                  setForLoc.remove(gridArray[i][k].getText());
                }
                      
                 }
                 
                 setForLoc = checkRegionConstraints(j,i,setForLoc);
                 
                 Set newSet= new HashSet();
                 Iterator setIter= setForLoc.iterator();
                 while (setIter.hasNext())
                 {
                     String textVal= (String) setIter.next();
                     newSet.add(Integer.parseInt(textVal));
                 }
                // System.out.println("for cell at loction "+i+","+j+" the domain is: "+ newSet.toString());
                return newSet;
                
        
        
    }
    
    public Set checkRegionConstraints(int i, int j, Set set)
            
    {
       Set newSet= new HashSet();
        if (j==0|| j==1||j==2)
        {
           newSet= checkRegionCols(i,0,2,set);
        }
        
        
        
       else if (j==3|| j==4||j==5)
       {
          newSet= checkRegionCols(i,3,5,set);
       }
        
       else {
         newSet=  checkRegionCols(i,6,8,set);
       }
        
        return newSet;
    
}
    
    private Set checkRegionCols(int i,int j,int end, Set set)
    {
         if (i==0|| i==1|| i==2)
            {
                for (int row= j; row<=end; row++)
                {
                    for (int col=0; col<=2; col++)
                    {
                        if(set.contains(gridArray[row][col].getText()))
                        {
                          set.remove(gridArray[row][col].getText());
                         }
                    }
                }
            }
            
            //region B
            else if (i==3|| i==4|| i==5)
            {
                for (int row= j; row<=end; row++)
                {
                    for (int col=3; col<=5; col++)
                    {
                        if(set.contains(gridArray[row][col].getText()))
                        {
                          set.remove(gridArray[row][col].getText());
                         }
                    }
                }
    }
         else
            {
                for (int row= j; row<=end; row++)
                {
                    for (int col=6; col<=8; col++)
                    {
                        if(set.contains(gridArray[row][col].getText()))
                        {
                          set.remove(gridArray[row][col].getText());
                         }
                    }
                }
            }
         
         return set;
}
    
    public void getNeighbor(Cell cell)
        {
            
             for (int k=0; k<cell.row; k++)
                {
                    cell.neighbors.add(gridArray[k][cell.col]);
                }
                 
                 
                for (int k=8; k>cell.row; k--)
                {
                  cell.neighbors.add(gridArray[k][cell.col]);
                
                }
                
                
                 for (int k=0; k<cell.col; k++)
                 {
                   cell.neighbors.add(gridArray[cell.row][k]);
                 }
                 
                 
                 for (int k=8; k>cell.col; k--)
                 {
                     cell.neighbors.add(gridArray[cell.row][k]);
                 }
                 
                  if (cell.row==0|| cell.row==1||cell.row==2)
        {
           cell.neighbors=findRegionalNeighbors(0, cell.col, 2 , cell.neighbors);
        }
        
        
        
       else if (cell.row==3|| cell.row==4||cell.row==5)
       {
         cell.neighbors=findRegionalNeighbors(3, cell.col, 5 , cell.neighbors);
       }
        
       else {
        cell.neighbors=findRegionalNeighbors(6, cell.col, 8 , cell.neighbors);
       }
        
                 
        }

    private Set findRegionalNeighbors(int i, int j, int end, Set set)
    {
        if (j==0|| j==1|| j==2)
            {
                for (int row= i; row<=end; row++)
                {
                    for (int col=0; col<=2; col++)
                    {
                        set.add(gridArray[i][j]);
                    }
                }
            }
            
            //region B
            else if (j==3|| j==4|| j==5)
            {
                for (int row= i; row<=end; row++)
                {
                    for (int col=3; col<=5; col++)
                    {
                          set.add(gridArray[i][j]);
                    }
                }
    }
         else
            {
                for (int row= i; row<=end; row++)
                {
                    for (int col=6; col<=8; col++)
                    {
                          set.add(gridArray[i][j]);
                    }
                }
            }
        return set;
         
    }
    
  
     public void solveS( int row, int col ) throws Exception
        {
            
           // Throw exception to stop code
           if( row > 8 ){
               final long duration = System.nanoTime() - startTime;
           //System.out.println("Duration for Backtracking + Arc Consistency: "+duration/1000000000.0);
              throw new Exception( "DONE" ) ;}

           // If the grid isn't empty, go to another grid
           if( !gridArray[row][col].val.equals(Integer.toString(0)) )
           {
               //checkNeighbors(gridArray[row][col]);
        	   if( col < 8 )
                   {solveS( row, col+1 ) ;}
                else
                   {solveS( row+1, 0 ) ;}
           }
           else
           {
              for( int value = 1; value <=9; value++ )
              {
                  gridArray[row][col].domains=checkRowColConstraints(gridArray[row][col].row, gridArray[row][col].col);
                 if( gridArray[row][col].domains.contains(value) )
                 {
                    
                    gridArray[row][col].setValue(Integer.toString(value));
                    checkNeighbors(gridArray[row][col]);
                  

                    if( col < 8 )
                    {solveS( row, col+1 ) ;}
                     else
                    { solveS( row+1, 0 ) ;}
                 }
              }
             // System.out.println("backtrack");
              // THIS IS BACKTRACKING
             gridArray[row][col].setValue(Integer.toString(0));
           
           }
           
        }
    



    
    
    private void checkNeighbors(Cell cell)
    {
        int size=0;
      Iterator iter= cell.neighbors.iterator();
      while (iter.hasNext())
      {
          Cell neighborCell= (Cell) iter.next();
          size=neighborCell.domains.size();
          neighborCell.domains=checkRowColConstraints(neighborCell.row, neighborCell.col);
         
          if (size!=neighborCell.domains.size())
          {
             /**  if( neighborCell.domains.size()==1)
          {
              Iterator iterate= neighborCell.domains.iterator();
              int val= (int) iterate.next();
              neighborCell.setText(Integer.toString(val));
              neighborCell.val=Integer.toString(val);
              neighborCell.domains.remove(val);
              
          }**/
              checkNeighbors(neighborCell);
          }
      }
       
    }
    
    public void reset()
    {
        for (int i=0; i<9; i++)
        {
            for (int j=0; j<9; j++)
            {
                gridArray[i][j].setValue(Integer.toString(0));
                gridArray[i][j].setForeground(Color.BLACK);
                 
                for (int k=1; k<=9; k++)
                {
                    gridArray[i][j].domains.add(k);
                }
            }
        }
    }
    
  //total remaining values in the domain
    public int getConstraintsHeuristic(int i, int j)
    {
   	 
    	return checkRowColConstraints(i,j).size();
    }
    
    //total unfilled neighboring cells
    private Set getRegionCols(int i,int j,int end)
    {
    	Set setOf3by3 = new HashSet();
    	//i is column	
         if (i==0|| i==1|| i==2)
            {	
        	 	//j is row
                for (int row= 0; row<=2; row++)
                {
                    for (int col=j; col<=end; col++)
                    {
                        if(row==i && col==j)
                        {
                          //do nothing
                        }
                        else
                        {
                        	if(!gridArray[row][col].getText().equals("0"))
                        	{
                        		setOf3by3.add(gridArray[row][col]);
                        	}
                        }
                    }
                }
            }
            
            //region B
            else if (i==3|| i==4|| i==5)
            {
                for (int row= 3; row<=5; row++)
                {
                    for (int col=j; col<=end; col++)
                    {
                    	if(row==i && col==j)
                        {
                          //do nothing
                        }
                        else
                        {
                        	if(!gridArray[row][col].getText().equals("0"))
                        	{
                        		setOf3by3.add(gridArray[row][col]);
                        	}
                        }
                    }
                }
    }
         else
            {
                for (int row= 6; row<=8; row++)
                {
                    for (int col=j; col<=end; col++)
                    {
                    	if(row==j && col==i)
                        {
                          //do nothing
                        }
                        else
                        {
                        	if(!gridArray[row][col].getText().equals("0"))
                        	{
                        		setOf3by3.add(gridArray[row][col]);
                        	}
                        }
                    }
                }
            }
         
         return setOf3by3;
}
    public Set check3by3Constraints(int i, int j)
    
    {
       Set newSet= new HashSet();
        if (j==0|| j==1||j==2)
        {
           newSet= getRegionCols(i,0,2);
        }
        
        
        
       else if (j==3|| j==4||j==5)
       {
          newSet= getRegionCols(i,3,5);
       }
        
       else {
         newSet=  getRegionCols(i,6,8);
       }
        
        return newSet;
    
}
    
    public void printConstraints()
    {
   	 int hn;
   	 int gn;
   	 for (int i=0; i<SIZE; i++)
   	 {
   		 for (int j=0; j<SIZE; j++)
   		 {	if(gridArray[i][j].getText().equals("0"))
   		 	{
   			 // System.out.println("reaining values in the domain "+ checkRowColConstraints(i,j));
   			 //hn= SIZE-getConstraintsHeuristic(i,j);
   			 //gn= gn(i,j);
   			//System.out.println("Total numbers that can be filled at ("+ i+", " + j + ") is "+ (SIZE-getConstraintsHeuristic(i,j)));
   		 	//System.out.println("gn is at " + i+ ", "+ j+ "is "+gn(i,j));
   		 	}
   		 }
   	 }
    }
    
    
    
    //total unfilled neighboring cells
    public int gn(int i, int j)
    {
   	 Set constraints = new HashSet();
    	int totalSpace=20;
    	int totalConstraint=0;
   	 //checking columns
   	 
   	 for (int k=0; k<i; k++)
        {
   		
        if(!(gridArray[k][j].getText().equals("0")))
        {
       	 
        	//System.out.println("is equal");
       	 constraints.add(gridArray[k][j]);
        }
        }
        for (int k=SIZE-1; k>i; k--)
        {
      
         if(!(gridArray[k][j].getText().equals("0")))
        
        	 constraints.add(gridArray[k][j]);
        
        
        }
        //checking row
         for (int k=0; k<j; k++)
         {	
         if(!(gridArray[i][k].getText().equals("0")))
         {      		 
        	 constraints.add(gridArray[i][k]);
             
         }
         }
         for (int k=SIZE-1; k>j; k--)
         {		
         if(!(gridArray[i][k].getText().equals("0")))
             {
        	 constraints.add(gridArray[i][k]);
             }
        }
         //System.out.println("total row and column only constraints are "+constraints.size());
         int total =0;
         Set ThreeThreeConstraintGrids = new HashSet();
         ThreeThreeConstraintGrids =check3by3Constraints(i, j);
         Iterator iter = ThreeThreeConstraintGrids.iterator();
         while(iter.hasNext())
         {
        	total=total+1;
        	 constraints.add(iter.next());
         }
     //System.out.println("total added in constraints from box "+total);
   	 return (totalSpace-(constraints.size()));
    }
    
    
   
   public void Astarsearch()
   {

	   
   
   	for (int i=0; i<SIZE; i++)
   	{
   		for (int j =0; j<SIZE; j++)
   		{
   			if(gridArray[i][j].getText().equals("0"))
   			{
   				@SuppressWarnings("unchecked")
				Pair gridcost = new Pair(gridArray[i][j], getConstraintsHeuristic(i,j)+gn(i,j));
   				queue.add(gridcost);
   				
   			}
   		}
   	}
   	
	//remove first thing in queue
				Cell cell=queue.remove().cell;
				int row = cell.row;
				int col = cell.col;
				try
				{
				recursiveAStar(row, col);
				}
				catch(Exception e)
				{
					
				}
			
	
   }
   				
   				
   	public void recursiveAStar(int row, int col) throws Exception
   	{
   		for( int value = 1; value <=9; value++ )
        {
            gridArray[row][col].domains=checkRowColConstraints(gridArray[row][col].row, gridArray[row][col].col);
           if( gridArray[row][col].domains.contains(value) )
           {
              
              gridArray[row][col].setValue(Integer.toString(value));
              //update queue

              queue=updateQueue();
              if (queue.isEmpty()){
              //throw exception
            	  final long duration = System.nanoTime() - startTime;
                 // System.out.println("Duration for Backtracking + A Star: "+duration/1000000000.0);
            	  throw new Exception("All values found");}
              Cell cell = queue.remove().cell;
              recursiveAStar(cell.row, cell.col);
              
           }
        }
       // System.out.println("backtrack a star WEEEEEEE");
        // THIS IS BACKTRACKING, Hopefully works
       gridArray[row][col].setValue(Integer.toString(0));
   	}
   		
   	
   
   
   public  PriorityQueue updateQueue()
   {
	   PriorityQueue<Pair<Cell, Integer>> queue = new PriorityQueue<Pair<Cell, Integer>>(1000, 
		        new Comparator<Pair<Cell, Integer>>() {
		          public int compare(Pair<Cell, Integer> a, Pair<Cell, Integer> b) {
		            return a.c - b.c;
		          }
		        }
		      );
	   
  
  	for (int i=0; i<SIZE; i++)
  	{
  		for (int j =0; j<SIZE; j++)
  		{
  			if(gridArray[i][j].getText().equals("0"))
  			{
  				@SuppressWarnings("unchecked")
				Pair gridcost = new Pair(gridArray[i][j], getConstraintsHeuristic(i,j)+gn(i,j));
  				queue.add(gridcost);
  				
  			}
  		}
  	}
  	return queue;
  	
   }
   
   public void printAStar()
   {
       

	   Astarsearch();
	  
   }
   
   public void unsolveBoard()
   {
	   for (int i=0; i<9; i++)
	   {
		   for (int j=0; j<9; j++)
		   {
			   if (!fixed.contains(gridArray[i][j]))
			   {
				   gridArray[i][j].setValue(Integer.toString(0));
			   }
			   else
			  {
				  gridArray[i][j].setForeground(Color.red);
			   }
		   }
	   }
   }
   
   
   
   
   
   ////////////////////******************LOOK AT THIS FOR THE FINAL UNIQUENESS SOL****************//////////////////////
   
   
   public int uniqueSol( int row, int col , int count) throws Exception
        {
            
          if (row>8)
          {
              row=0;
              if ( ++col>8)
              {
                  return 1+count;
              }
          }
          
          if (!copyArray[row][col].equals(Integer.toString(0)))//skip over any filled cells
          {
              return uniqueSol(row+1, col, count);
              
          }
          //now search for more than 1 sol, and break if 2 found
              for (int i=1; i<=9 && count<2; i++)
              {
                  gridArray[row][col].domains=checkRowColConstraints(row, col);
                          if ( gridArray[row][col].domains.contains(i))
                          {
                              copyArray[row][col]=(Integer.toString(i));
                              count=uniqueSol(row+1, col, count);
                          }
              }
              copyArray[row][col]=Integer.toString(0);
              return count;
        }
   
  
           
           
   public void sol() throws Exception
   {
       //unsolveBoard();
       counter=0;
       copyBoard();
       if (uniqueSol(0,0,0)==1)
       {
           JOptionPane.showMessageDialog(null,"This puzzle IS unique !","Test Uniqueness",JOptionPane.WARNING_MESSAGE);
                
       }
       else if (uniqueSol(0,0,0)==0)
       {
           JOptionPane.showMessageDialog(null,"This puzzle has no solution!!","Test Uniqueness",JOptionPane.WARNING_MESSAGE);
            
       }
       else {
           JOptionPane.showMessageDialog(null,"This puzzle is NOT unique!","Test Uniqueness",JOptionPane.WARNING_MESSAGE);
                 
       }
   }
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   ///////************THIS IS JUST INITIAL WORK----NOT USED IN THE PROGRAM.I HAVE UPDATED TO A NEW ALGORITHM NOW*************//////
   public void copyBoard()
   {
       for (int i=0; i<9; i++)
        {for (int j=0; j<9; j++)
            {
                values.add(gridArray[i][j].val); 
            }
        }
Iterator iter= values.iterator();
       copyArray= new String[9][9];
       for (int i=0; i<9; i++)
        {for (int j=0; j<9; j++)
            {
                String val= (String) iter.next();
                copyArray[i][j]= val ;
                //values.add(val);
            }
        }
   }
   
    
   public void printcopyBoard()
   {
      //Cell[][] copyArray=new Cell[9][9];
       for (int i=0; i<9; i++)
        {for (int j=0; j<9; j++)
            {
                System.out.println(copyArray[i][j] );
                
            }
        }
   }
   public void printgridBoard()
   {
     // Cell[][] copyArray=new Cell[9][9];
       for (int i=0; i<9; i++)
        {for (int j=0; j<9; j++)
            {
                System.out.println(gridArray[i][j].val );
            }
        }
   }
   public boolean same()
   {
       
       
        for (int i=0; i<9; i++)
            {
                for (int j=0; j<9; j++)
                {
                    if (!((copyArray[i][j]).equals(gridArray[i][j].val)))
                    {//System.out.println("Same is FALSE"); 
                        return false;}
               
                }
            }
        
        
        return true;
   }
   public void getUniqueSol() 
   {
      
           
        try{
           // System.out.println("About to print A*!!");
           
           solveS(0, 0);
           }catch( Exception ex )
            {
            } 
             
           // System.out.println("About to copy Board!!");
            copyBoard();
           
             
             unsolveBoard();
            
                 
      try{
             printAStar();
      }
             catch( Exception ex )
            {
            }
           
           
          
         if (same()==false)
               {
                   System.out.println("This puzzle is NOT unique !");
                   JOptionPane.showMessageDialog(null,"This puzzle is NOT unique !","Test Uniqueness",JOptionPane.WARNING_MESSAGE);
                  unsolveBoard();
               }
            
            else 
            {
                 System.out.println("This puzzle IS unique !");
                JOptionPane.showMessageDialog(null,"This puzzle IS unique!","Test Uniqueness",JOptionPane.WARNING_MESSAGE);
                  unsolveBoard();
           }
       
   }
   
 
   public int getUniqueSol2(int row, int col) 
   {
      // Throw exception to stop code
           if( row > 8 ){
               final long duration = System.nanoTime() - startTime;
           //System.out.println("Duration for Backtracking + Arc Consistency: "+duration/1000000000.0);
             // throw new Exception( "DONE" )
               counter++;
               System.out.println("there are "+ counter+" number of solutions");
               return counter;
           }

           // If the grid isn't empty, go to another grid
           if( !gridArray[row][col].val.equals(Integer.toString(0)) )
           {
               //checkNeighbors(gridArray[row][col]);
        	   if( col < 8 )
                   {getUniqueSol2( row, col+1 ) ;}
                else
                   {getUniqueSol2( row+1, 0 ) ;}
           }
           else
           {
              for( int value = 1; value <=9; value++ )
              {
                  gridArray[row][col].domains=checkRowColConstraints(gridArray[row][col].row, gridArray[row][col].col);
                 if( gridArray[row][col].domains.contains(value) )
                 {
                    
                    gridArray[row][col].setValue(Integer.toString(value));
                   checkNeighbors(gridArray[row][col]);
                
                    counter++;
                    
                    if( col < 8 )
                    {getUniqueSol2( row, col+1 ) ;}
                     else
                    { getUniqueSol2( row+1, 0 ) ;}
                 }
              }
                  gridArray[row][col].setValue(Integer.toString(0));//backtracking!!
           
           }
           
        System.out.println("there are : "+ counter+" number of solutions!!!!");
               return counter;
   
}
 
  
}