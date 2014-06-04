package us.ttyl.sudokuhelper.widgets;

import us.ttyl.sudokuhelper.R;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PopupPicker 
{
	Activity  mActivity;
	int mColor[];
	String mNumber = "";
	
	public PopupPicker(Activity activity)
	{
		mActivity = activity;
	}	
	
	public RelativeLayout createPickerTextView()
    {
    	LayoutInflater inflater = (LayoutInflater)mActivity.getLayoutInflater();
    	RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.sudoku_item, null);
    	ImageView sudokuBoxColor = (ImageView)layout.findViewById(R.id.number_color);
    	TextView sudokuItemText = (TextView)layout.findViewById(R.id.number_text);
    	sudokuBoxColor.setBackgroundColor(Color.argb(255, mColor[0], mColor[1], mColor[2]));
    	sudokuItemText.setText(mNumber);     	
    	return layout;
    }

	public int[] getmColor() 
	{
		return mColor;
	}

	public void setmColor(int[] mColor) 
	{
		this.mColor = mColor;
	}

	public String getmNumber() 
	{
		return mNumber;
	}

	public void setmNumber(String mNumber) 
	{
		this.mNumber = mNumber;
	}	
	
}
