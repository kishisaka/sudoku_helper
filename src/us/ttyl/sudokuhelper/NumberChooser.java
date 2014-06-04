package us.ttyl.sudokuhelper;

import us.ttyl.sudokuhelper.model.SudokuItem;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

public class NumberChooser extends DialogFragment
{
	 SudokuItemAdapter mSudokuItemAdapter;
	 int mSelectedItemPosition = 0;
	 DialogFragment mNumberChooser = this;
	 
	 public void setAdadpter(SudokuItemAdapter sudokuItemAdapter)
	 {
		 mSudokuItemAdapter = sudokuItemAdapter;
	 }
	 
	 public void setItemPosition(int selectedItemPosition)
	 {
		 mSelectedItemPosition = selectedItemPosition; 
	 }
	 
	 /** The system calls this only when creating the layout in a dialog. */
	 @Override
	 public Dialog onCreateDialog(Bundle savedInstanceState)
	 {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
	 }
	 
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) 
	 {
		 View mNumberSelector = inflater.inflate(R.layout.number_picker, null);
		 generateButton(R.id.solve_1, "1", mNumberSelector);
		 generateButton(R.id.solve_2, "2", mNumberSelector);
		 generateButton(R.id.solve_3, "3", mNumberSelector);
		 generateButton(R.id.solve_4, "4", mNumberSelector);
		 generateButton(R.id.solve_5, "5", mNumberSelector);
		 generateButton(R.id.solve_6, "6", mNumberSelector);
		 generateButton(R.id.solve_7, "7", mNumberSelector);
		 generateButton(R.id.solve_8, "8", mNumberSelector);
		 generateButton(R.id.solve_9, "9", mNumberSelector);
		 generateButton(R.id.solve_0, "0", mNumberSelector);
			
		 return mNumberSelector;
    }
	
	private Button generateButton(final int id, final String number, final View layout)
	{
    	Button button = (Button)layout.findViewById(id);
		button.setOnTouchListener(getTouchListener(number));
		return button;
	}
    
    private OnTouchListener getTouchListener(final String number)
    {
    	OnTouchListener touchListener = new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				SudokuItem item = mSudokuItemAdapter.getList().get(mSelectedItemPosition);
				if (number.equals("0") == false)
				{
					item.setM_hidden(false);
					item.setM_given(true);
				}
				else
				{
					item.setM_hidden(false);
					item.setM_given(true);
				}
				item.setM_item(number);
				mSudokuItemAdapter.notifyDataSetChanged();	
				mNumberChooser.dismiss();
				return false;
			}
		};
    	return touchListener;
    }
}
