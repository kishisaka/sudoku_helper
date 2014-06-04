package us.ttyl.sudokuhelper.solver;

import java.util.HashMap;
import java.util.List;

/**
 * solve N x N sudoku puzzles. Uses simple backtracking 
 * blazing fast on an Core i5 750 (3.4ghz overclocked) hard2 < 2 sec with full debug
 * slug slow on Samsung Galaxy S3 (1.5Ghz ARM v7)  hard2 ~30 sec with no debug
 * 
 * @author kishisaka
 *
 */
public class SudokuSolver 
{	
	/*
	SudokuItem[][] m_sudoku = new SudokuItem[][] {
		{new SudokuItem("0", false),new SudokuItem("2", true),new SudokuItem("4", true),new SudokuItem("0", false)}
		,{new SudokuItem("1", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("3", true)}
		,{new SudokuItem("4", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("2", true)}
		,{new SudokuItem("0", false),new SudokuItem("1", true),new SudokuItem("3", true),new SudokuItem("0", false)}
	};
		
	// the puzzle (hard)
	SudokuItem[][] m_sudoku = new SudokuItem[][] {
		{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("6", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("8", true),new SudokuItem("5", true),new SudokuItem("0", false),new SudokuItem("0", false)}			
		,{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("7", true),new SudokuItem("0", false),new SudokuItem("6", true),new SudokuItem("1", true),new SudokuItem("3", true)}
		,{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("9", true)}
		,{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("9", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("1", true)}
		,{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("1", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("8", true),new SudokuItem("0", false),new SudokuItem("0", false)}
		,{new SudokuItem("4", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("5", true),new SudokuItem("3", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false)}
		,{new SudokuItem("1", true),new SudokuItem("0", false),new SudokuItem("7", true),new SudokuItem("0", false),new SudokuItem("5", true),new SudokuItem("3", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false)}
		,{new SudokuItem("0", false),new SudokuItem("5", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("6", true),new SudokuItem("4", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false)}
		,{new SudokuItem("3", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("1", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("6", true),new SudokuItem("0", false)}			
	};
	
	// the puzzle (easy)	
	SudokuItem[][] m_sudoku = new SudokuItem[][] {
		{new SudokuItem("0", false),new SudokuItem("2", true),new SudokuItem("0", false),new SudokuItem("3", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("1", true)}			
		,{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("8", true),new SudokuItem("0", false),new SudokuItem("2", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("4", true),new SudokuItem("0", false)}
		,{new SudokuItem("0", false),new SudokuItem("4", true),new SudokuItem("0", false),new SudokuItem("9", true),new SudokuItem("0", false),new SudokuItem("7", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("3", true)}
		,{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("3", true),new SudokuItem("0", false),new SudokuItem("7", true),new SudokuItem("0", false),new SudokuItem("8", true)}
		,{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("7", true),new SudokuItem("4", true),new SudokuItem("0", false),new SudokuItem("5", true),new SudokuItem("1", true),new SudokuItem("0", false),new SudokuItem("0", false)}
		,{new SudokuItem("9", true),new SudokuItem("0", false),new SudokuItem("3", true),new SudokuItem("0", false),new SudokuItem("8", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false)}
		,{new SudokuItem("1", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("8", true),new SudokuItem("0", false),new SudokuItem("3", true),new SudokuItem("0", false),new SudokuItem("6", true),new SudokuItem("0", false)}
		,{new SudokuItem("0", false),new SudokuItem("3", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("1", true),new SudokuItem("0", false),new SudokuItem("8", true),new SudokuItem("0", false),new SudokuItem("0", false)}
		,{new SudokuItem("8", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("2", true),new SudokuItem("0", false),new SudokuItem("1", true),new SudokuItem("0", false)}			
	};
	
	// the puzzle (empty)
	SudokuItem[][] m_sudoku = new SudokuItem[][] {
		{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false)}			
		,{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false)}
		,{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false)}
		,{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false)}
		,{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false)}
		,{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false)}
		,{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false)}
		,{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false)}
		,{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false)}			
	};


	// the puzzle (hard 2)
	SudokuItem[][] m_sudoku = new SudokuItem[][] {
		{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("8", true),new SudokuItem("4", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("9", true)}			
		,{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("1", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("5", true)}
		,{new SudokuItem("8", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("2", true),new SudokuItem("1", true),new SudokuItem("4", true),new SudokuItem("6", true),new SudokuItem("0", false)}
		,{new SudokuItem("7", true),new SudokuItem("0", false),new SudokuItem("8", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("9", true),new SudokuItem("0", false)}
		,{new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("3", true),new SudokuItem("0", false),new SudokuItem("1", true)}
		,{new SudokuItem("0", false),new SudokuItem("5", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false)}
		,{new SudokuItem("0", false),new SudokuItem("2", true),new SudokuItem("4", true),new SudokuItem("9", true),new SudokuItem("1", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("7", true)}
		,{new SudokuItem("9", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("5", true),new SudokuItem("0", false),new SudokuItem("0", false)}
		,{new SudokuItem("3", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("8", true),new SudokuItem("4", true),new SudokuItem("0", false),new SudokuItem("0", false),new SudokuItem("0", false)}			
	};
	*/
		
	SudokuItem[][] m_sudoku = null;
	
	// the block size
	int m_blockSize = 0;
	
	//current x in the puzzle play area
	int m_counterX = 0;
	
	//current y in the puzzle play area
	int m_counterY = 0;
	
	int m_iterations = 0;
	
	boolean m_isValid = true;
	
	int m_errorX = 0;
	int m_errorY = 0;
	String m_errorValue = "";
	
	public SudokuSolver(List<us.ttyl.sudokuhelper.model.SudokuItem> sudokuItems) throws Exception
	{
		initalizer(sudokuItems);
		
		m_blockSize = (int)(Math.sqrt((double)m_sudoku.length));
		
		//check that the user inserted cells are valid before solving. 
		if (verifyStartingPuzzel() == false)
		{
			m_isValid = false;
			return;
		}
		
		int current = 1;
		
		printPuzzle();
		
		while(true)
		{		
			//do work here!
			if (m_sudoku[m_counterX][m_counterY].m_item.equals("0"))				
			{						
				boolean found = false;
				while (found == false)
				{
					//populate current cell
					found = populateCell(m_counterX, m_counterY, current);
					
					//if current cell is not populated (popluateCell() returns false), go back one cell, add one to the item and try populate again
					if (found == false)
					{						
						SudokuItem item = findPreviousEmptyCell();
						current = (Integer.parseInt(item.m_item)) + 1;						
					}
					else
					{
						current = 1;
						break;						
					}
				}
			}
			//System.out.println(m_counterX + "," + m_counterY + " : " + m_sudoku[m_counterX][m_counterY].m_item + " | " + m_sudoku.length);
			
			//increment x and y counters, do bounds checking, reset x and increment y as needed
			findNextEmptyCell();			
			
			//check to see that there are more cells to process, if this is not the case, we are done! ,exit and dump puzzle to screen, else continue 
			if ( (m_counterX == m_sudoku.length - 1)  && (m_counterY == m_sudoku.length - 1) && (m_sudoku[m_counterX][m_counterY].m_item.equals("0") == false) )
			{
				//System.out.println(m_counterX + "," + m_counterY + " : " + m_sudoku[m_counterX][m_counterY].m_item + " | " + m_sudoku.length);
				break;
			}			
		}
		System.out.println(">>-------->> iterations: " + m_iterations);
		//print complete puzzle
		printPuzzle();
		
		finisher(sudokuItems);		
	}
	
	/**
	 * find a previously processed cell for backtracking/re-processing purposes 
	 * @return
	 */
	public SudokuItem findPreviousEmptyCell() throws Exception
	{		
		while (true)
		{
			m_counterY = m_counterY - 1;
			if (m_counterY < 0)
			{
				m_counterX = m_counterX - 1;
				m_counterY = m_sudoku.length -1;
			}
			if (m_sudoku[m_counterX][m_counterY].m_given == false)
			{
				return m_sudoku[m_counterX][m_counterY];
			}
			if (m_counterX == 0 && m_counterY == 0)
			{
				return m_sudoku[m_counterX][m_counterY];
			}
		}		
	}
	
	/**
	 * find next empty cell for processing
	 */
	public void findNextEmptyCell() throws Exception
	{
		//System.out.println("findNextEmptyCell   m_counterX: " + m_counterX + ":  m_counterY: " + m_counterY);
		//check boundaries! 
		if (m_counterX >= m_sudoku.length -1 && m_counterY >= m_sudoku.length -1)
		{
			return;
		}
		
		while (true)
		{						
			m_counterY ++;
			if (m_counterY >= m_sudoku.length)
			{
				m_counterY = 0;
				m_counterX ++;
			}
			//check boundary again! 
			if (m_counterX >= m_sudoku.length -1 && m_counterY >= m_sudoku.length -1)
			{
				return;
			}
			if (m_sudoku[m_counterX][m_counterY].m_given == false)
			{
				return;
			}			
			if (m_counterX == 0 && m_counterY == 0)
			{
				return;
			}
		}
	}
	
	/**
	 * update cell with number and test its validity in the puzzle
	 * @param x
	 * @param y
	 * @param start
	 * @return
	 */
	public boolean populateCell(int x, int y, int start) throws Exception
	{ 		
		for(int i = start; i <= m_sudoku.length; i ++)
		{
			m_sudoku[x][y].m_item = Integer.toString(i);
			if (verifyNewItem(x,y))
			{
				//System.out.println("try " + i + " for " + x + "," + y );
				m_iterations = m_iterations + 1;
				return true;
			}			
		}
		m_sudoku[x][y].m_item = "0";
		//System.out.println("have to go back! " + x + ":" + y);
		m_iterations = m_iterations + 1;
		return false;
	}
	
	/**
	 * verify row, column and blocks for duplicate numbers
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean verifyNewItem(int x, int y)  throws Exception
	{
		HashMap <String, String> mapX = new HashMap <String, String> ();
		HashMap <String, String> mapY = new HashMap <String, String> ();
		HashMap <String, String> block = new HashMap <String, String> ();				
		
		//check for x row
		for(int i = 0 ; i < m_sudoku.length; i ++)
		{
			if (m_sudoku[i][y].m_item.equals("0") == false)			
			{
				if (mapX.containsKey(m_sudoku[i][y].m_item) == false)
				{
					mapX.put(m_sudoku[i][y].m_item, m_sudoku[i][y].m_item);
				}
				else
				{
					m_errorX = i;
					m_errorY = y;
					m_errorValue = m_sudoku[i][y].m_item;
					return false;
				}	
			}
		}
		
		//check for y column
		for(int i = 0 ; i < m_sudoku.length; i ++)
		{
			if (m_sudoku[x][i].m_item.equals("0") == false)
			{
				if (mapY.containsKey(m_sudoku[x][i].m_item) == false)
				{
					mapY.put(m_sudoku[x][i].m_item, m_sudoku[x][i].m_item);
				}
				else
				{
					m_errorX = x;
					m_errorY = i;
					m_errorValue = m_sudoku[x][i].m_item;
					return false;
				}	
			}
		}
		
		//check block
		int startX = (x/m_blockSize) * m_blockSize;
		int startY = (y/m_blockSize) * m_blockSize;
		
		// System.out.println("block: " + startX + "," + startY);
		
		// test the block
		for(int i = 0; i < m_blockSize; i ++)
		{
			for (int j = 0; j < m_blockSize; j ++)
			{
				if (m_sudoku[startX + i][startY + j].m_item.equals("0") == false)
				{
					if (block.containsKey(m_sudoku[startX + i][startY + j].m_item) == false)
					{
						block.put(m_sudoku[startX + i][startY + j].m_item, m_sudoku[startX + i][startY + j].m_item);
					}
					else
					{
						m_errorX = i;
						m_errorY = j;
						m_errorValue = m_sudoku[i][j].m_item;
						return false;
					}	
				}
			}
		}
		//if we get here, we are good!
		return true;
	}	
	
	/**
	 * dump puzzle to screen for debug
	 */
	public void printPuzzle() throws Exception
	{
		for(int i = 0; i < m_sudoku.length; i ++)
		{
			for(int j = 0; j < m_sudoku.length; j ++)
			{
				System.out.print(m_sudoku[j][i].m_item + "  ");
			}
			System.out.println("\n");
		}		
		System.out.println("----------------------------------------");
	} 
	
	/**
	 * copy items from adapter list into work array
	 * @param sudokuItems
	 */
	public void initalizer(List<us.ttyl.sudokuhelper.model.SudokuItem> sudokuItems) throws Exception
	{
		int size = (int)Math.sqrt(sudokuItems.size());
		m_sudoku = new SudokuItem[size][size];
		int x = 0;
		int y = 0;
		for(int i = 0; i < sudokuItems.size(); i ++)
		{
			String item = sudokuItems.get(i).getM_item();
			boolean given = true;
			if (item.equals("0") == true)
			{
				given = false;
			}
			m_sudoku[x][y] = new SudokuItem(item, given);								
			x ++;
			if (x >= size)
			{
				x = 0;
				y++;
			}
		}
	}
	
	/**
	 * copy items from work array into adapter list
	 * @param sudokuItems
	 */
	public void finisher(List<us.ttyl.sudokuhelper.model.SudokuItem> sudokuItems)  throws Exception
	{
		int size = (int)Math.sqrt(sudokuItems.size());		
		int x = 0;
		int y = 0;
		for(int i = 0; i < sudokuItems.size(); i ++)
		{
			sudokuItems.get(i).setM_item(m_sudoku[x][y].m_item);		
			x ++;
			if (x >= size)
			{
				x = 0;
				y++;
			}
		}
	}
	
	/**
	 * verify that the starting puzzle pieces are valid, return false if not.
	 * @return
	 */
	public boolean verifyStartingPuzzel()
	{
		try
		{
			for(int y = 0; y < m_sudoku.length; y ++)
			{
				for(int x = 0; x < m_sudoku.length; x ++)
				{
					if (m_sudoku[x][y].m_given == true)
					{
						if (verifyNewItem(x,y) == false)
						{
							return false;
						}
					} 
				}
			}
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isValid()
	{
		return m_isValid;
	}
	
	public String getErrorValue()
	{
		return m_errorValue;		
	}
	
	public int getErrorX()
	{
		return m_errorX;
	}
	
	public int getErrorY()
	{
		return m_errorY;
	}
}

/**
 * the sudoku cell, contains the number and indicator (boolean flag) if the item is a given cell (cannot be modified)
 * @author test
 *
 */
class SudokuItem
{
	String m_item;
	boolean m_given = false;
	
	public SudokuItem(String item, boolean given)
	{
		m_item = item;
		m_given = given;
	}
}