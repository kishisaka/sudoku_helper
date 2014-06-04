package us.ttyl.sudokuhelper;

import java.util.ArrayList;
import java.util.List;

import us.ttyl.sudokuhelper.model.SudokuItem;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SudokuItemAdapter extends BaseAdapter
{
	List <SudokuItem> mSudokuItemlist = new ArrayList <SudokuItem>();	
	private Context mContext;
	
	public String getTextRepresentation()
	{
		StringBuffer sb = new StringBuffer();
		for(SudokuItem sudokuItem : mSudokuItemlist)
		{
			sb.append(sudokuItem.toString() + "|");
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}
	
	public SudokuItemAdapter(Context c)
	{
		mContext = c;
	}
	
	public void addSudokuItem(SudokuItem item)
	{
		mSudokuItemlist.add(item);
	}
	
	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		return mSudokuItemlist.size();
	}

	@Override
	public Object getItem(int position) 
	{
		// TODO Auto-generated method stub
		return (Object)mSudokuItemlist.get(position);
	}
	
	public List <SudokuItem> getList()
	{
		return mSudokuItemlist;
	}
	
	@Override
	public long getItemId(int position) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		// TODO Auto-generated method stub
		return createView(position);
	}
	
	private int[] getColor(int position)
	{
		//determine colors! 
		int x = position%9;
		int y = (int)position/9;		
		
		int[] colors = null;
		
		//row 1
		//block 1
		if (x < 3 && y < 3)
		{
			colors = new int[]{86,188,121}; 
		}
		//block 2         	
		if (x >= 3 && x <= 6 && y < 3)
		{
			colors = new int[]{172,214,150};
		}
		//block 3
		if (x >= 6 && y < 3)
		{
			colors = new int[]{134,208,244};
		}
		
		//row 2
		//block 4
		if (x < 3 && y >= 3 &&y >= 3 && y < 6)
		{
			colors = new int[]{236,241,190};
		}
		//block 5         	
		if (x >= 3 && x <= 6 &&  y >= 3 && y < 6)
		{
			colors = new int[]{136,203,152};
		}
		//block 6
		if (x >= 6 &&  y >= 3 && y >= 3 && y < 6)
		{
			colors = new int[]{151,209,166}; 
		}
		
		// row 3
		//block 7
		if (x < 3 && y >= 3 && y >= 6 )
		{
			colors = new int[]{153,215,219};
		}
		//block 8         	
		if (x >= 3 && x <= 6 &&  y >= 6)
		{
			colors = new int[]{250,174,97};
		}
		//block 9
		if (x >= 6 && y >= 6)
		{
			colors = new int[]{172,214,150};
		}
		return colors;
	}
		
	private RelativeLayout createView(int position)
	{
		SudokuItem item = mSudokuItemlist.get(position);
		Activity activty = (Activity)mContext;
    	LayoutInflater inflater = (LayoutInflater)activty.getLayoutInflater();
    	RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.sudoku_item, null);
    	ImageView sudokuBoxColor = (ImageView)layout.findViewById(R.id.number_color);
    	TextView sudokuItemText = (TextView)layout.findViewById(R.id.number_text);
    	int[] color = getColor(position);
    	sudokuBoxColor.setBackgroundColor(Color.argb(255, color[0], color[1], color[2]));
    	if (item.isM_hidden() == false)
    	{
    		sudokuItemText.setText(item.getM_item()); 
    	}
    	else
    	{
    		sudokuItemText.setText("?"); 	
    	}
    	return layout;
	}
}
