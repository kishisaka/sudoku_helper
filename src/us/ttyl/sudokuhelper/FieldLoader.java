package us.ttyl.sudokuhelper;

import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import us.ttyl.sudokuhelper.model.SudokuItem;

public class FieldLoader extends DialogFragment
{ 
	 private FieldLoaderAdapter mFieldLoaderAdapter = null;
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
		 
		 View fieldLoader = inflater.inflate(R.layout.field_loader, null);
		 Button  cancelButton = (Button)fieldLoader.findViewById(R.id.cancel_button_loader);
		 cancelButton.setOnClickListener(new OnClickListener()
		 {
			@Override
			public void onClick(View v)
			{
				dismiss();
			}
		 });
		
		 ListView fieldNameList = (ListView)fieldLoader.findViewById(R.id.field_names);
		 mFieldLoaderAdapter = new FieldLoaderAdapter(this.getActivity());
		 mFieldLoaderAdapter.setFieldNames(SudokuHelperPreferences.getFieldNames(this.getActivity()));
		 fieldNameList.setAdapter(mFieldLoaderAdapter);
		 fieldNameList.setOnItemClickListener(new OnItemClickListener()
		 {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) 
			{
				// load the data from preferences
				List<SudokuItem> sudokuItems = SudokuHelperPreferences.getField((String)mFieldLoaderAdapter.getItem(position), getActivity().getApplicationContext());
				// clear the board
				mSudokuItemAdapter.mSudokuItemlist = sudokuItems;
				// load the board
				mSudokuItemAdapter.notifyDataSetChanged();	
				SudokuHelperActivity.mMode = SudokuHelperActivity.MODE_EDIT;
				SudokuHelperActivity.configureSoveButton();
				dismiss();
			}
		 }); 
		 return fieldLoader;
	 }
}
