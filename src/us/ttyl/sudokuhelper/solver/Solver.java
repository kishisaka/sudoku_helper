package us.ttyl.sudokuhelper.solver;

import java.util.List;

import us.ttyl.sudokuhelper.SudokuHelperActivity;
import us.ttyl.sudokuhelper.SudokuItemAdapter;
import us.ttyl.sudokuhelper.model.SudokuItem;

public class Solver 
{
	SudokuHelperActivity mActivity = null;
	
	public Solver(SudokuHelperActivity activity)
	{
		mActivity = activity;
		updateGrid();
	}
	
	public void updateGrid()
	{
		mActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				// TODO Auto-generated method stub	
				SudokuItemAdapter adapter = mActivity.getSudokuItemAdapter();
				List <SudokuItem> items = adapter.getList();
				for(int i = 0; i < items.size(); i ++)
				{
					items.get(i).setM_item("2");
				}
				adapter.notifyDataSetChanged();
			}
		});
	}
}
