package us.ttyl.sudokuhelper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class FieldSaver extends DialogFragment
{
	 private Button mSave;
	 private Button mDismiss;
	 private TextView mName; 
	 private SudokuItemAdapter mSudokuItemAdapter;
	
	 public void setSudokuAdpater(SudokuItemAdapter sudokuItemAdapter)
	 {
		 mSudokuItemAdapter = sudokuItemAdapter;
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
		 View numberSelector = inflater.inflate(R.layout.field_saver, null);
		 mSave = (Button)numberSelector.findViewById(R.id.save_button);
		 mDismiss = (Button)numberSelector.findViewById(R.id.cancel_button);
		 mName = (TextView)numberSelector.findViewById(R.id.save_name);
		 
		 mSave.setOnClickListener(new OnClickListener()
		 {
			@Override
			public void onClick(View v) 
			{
				// do the save here!
				Context context = getActivity().getApplicationContext();
				SudokuHelperPreferences.saveField(mSudokuItemAdapter.getTextRepresentation(), mName.getText().toString(), context);
				dismiss();
			}
		 });
		 
		 
		 mDismiss.setOnClickListener(new OnClickListener()
		 {
			@Override
			public void onClick(View v)
			{
				dismiss();
			}
		 });
		 
		 return numberSelector;
   }
}
