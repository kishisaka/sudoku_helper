package us.ttyl.sudokuhelper;

import java.util.List;

import us.ttyl.sudokuhelper.model.SudokuItem;
import us.ttyl.sudokuhelper.solver.SudokuSolver;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class SudokuHelperActivity extends FragmentActivity
{
	private final String TAG = SudokuHelperActivity.class.getName();
	
	private GridView mPlayField;
	private static Button mSolveButton;
	private Button mClearButton;
	private SudokuItemAdapter mSudokuItemAdapter;
	final private SudokuHelperActivity mActivity = this;
	private ProgressDialog mProgressBar = null;
	
	//board mode, edit = 0, solve = 1
	
	public static final int MODE_EDIT = 0;
	public static final int MODE_SOLVE = 1;
	
	public static int mMode = MODE_EDIT; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_helper);
        mPlayField = (GridView)findViewById(R.id.play_field);
        mSolveButton = (Button)findViewById(R.id.solve_button);
        mClearButton = (Button)findViewById(R.id.clear_button);
                
        //initalize board
        mSudokuItemAdapter = new SudokuItemAdapter(this);
        for(int i = 0; i < 81; i ++)
        {
        	int x = i%9;
    		int y = (int)i/9;
        	SudokuItem item = new SudokuItem("0", true, x, y);        	
        	mSudokuItemAdapter.addSudokuItem(item);
        }
        mPlayField.setAdapter(mSudokuItemAdapter);
        
        //set click listener for the board
        OnItemClickListener numberChooserListener = new OnItemClickListener() 
        {          
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) 
			{	
				if (mMode == MODE_EDIT)
				{
					NumberChooser dialog = new NumberChooser();
					dialog.setAdadpter(mSudokuItemAdapter);
					dialog.setItemPosition(position);
			        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
				}
				else
				{
					SudokuItem item = (SudokuItem)mSudokuItemAdapter.getItem(position);
					item.setM_hidden(false);
					mSudokuItemAdapter.notifyDataSetChanged();
				}
			}
        };
        
        mPlayField.setOnItemClickListener(numberChooserListener);
        
        //listen for button solve press requests
        mSolveButton.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				try
				{
					mProgressBar = new ProgressDialog(v.getContext());
					mProgressBar.setCancelable(true);
					mProgressBar.setMessage("Solving....");
					mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					mProgressBar.setProgress(0);
					mProgressBar.setMax(100);
					mProgressBar.show();
					
					Thread solverRunner = new Thread(new Runnable()
					{
						@Override
						public void run() 
						{
							try
							{												
								final SudokuSolver solver = new SudokuSolver(mSudokuItemAdapter.getList());
								mActivity.runOnUiThread(new Runnable()
								{
									@Override
									public void run() 
									{
										mSudokuItemAdapter.notifyDataSetChanged();
									}
								});																
								mProgressBar.dismiss();
								
								if (solver.isValid() == false)
								{
									mActivity.runOnUiThread(new Runnable()
									{
										@Override
										public void run() 
										{
											Toast toast = Toast.makeText(mActivity, "Incorrect number sequence at "+solver.getErrorX()+","+solver.getErrorY()+" value: " + solver.getErrorValue() + ". Please fix and try again.", Toast.LENGTH_SHORT);
											toast.show();
										}
									});
									mMode = MODE_EDIT;
								}
								else
								{
									mActivity.runOnUiThread(new Runnable()
									{
										@Override
										public void run() 
										{
											mSolveButton.setEnabled(false);
										}
									});
									
									mMode = MODE_SOLVE;
								}
							}
							catch(Exception e)
							{
								Log.e(TAG, e.getMessage());
								mMode = MODE_EDIT;
							}					
						}
					}, "SudokuSolver");
					
					solverRunner.start();
				}
				catch(Exception e)
				{	
					Toast toast = Toast.makeText(mActivity, "Incorrect number sequence. Please fix and try again.", Toast.LENGTH_SHORT);
					toast.show();
					mMode = MODE_EDIT;
				}
			}
		});
        
        //listen for button clear press requests
        mClearButton.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{		
				clearBoard();
			}
		});
    }
    
    /**
     * clear the board! reset flags to default, reset solve and clear buttons. 
     */
    public void clearBoard()
    {
    	List <SudokuItem> sudokuList = mSudokuItemAdapter.getList();
		for(int i = 0 ; i < sudokuList.size(); i ++)
		{
			sudokuList.get(i).setM_item("0");
			sudokuList.get(i).setM_hidden(true);
			sudokuList.get(i).setM_given(false);
		}
		mSudokuItemAdapter.notifyDataSetChanged();
		mMode = MODE_EDIT;
		
		Toast toast = Toast.makeText(mActivity, "Board cleared.", Toast.LENGTH_SHORT);
		toast.show();
		mSolveButton.setEnabled(true);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_sudoku_helper, menu);    
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        // Handle item selection
        switch (item.getItemId())
        {
            case R.id.save_field:
                FieldSaver fieldSaver = new FieldSaver();
                fieldSaver.setSudokuAdpater(mSudokuItemAdapter);
                fieldSaver.show(this.getSupportFragmentManager(), "");
                return true;
            case R.id.load_field:
                FieldLoader fieldLoader = new FieldLoader();
                fieldLoader.setSudokuAdpater(mSudokuItemAdapter);
                fieldLoader.show(this.getSupportFragmentManager(), "");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public SudokuItemAdapter getSudokuItemAdapter()
    {
    	return mSudokuItemAdapter;
    }
    
    public SudokuHelperActivity getActivity()
    {
    	return mActivity;
    }
    
    public static void configureSoveButton()
    {
    	
    	if (mMode == MODE_EDIT)
    	{
    		mSolveButton.setEnabled(true);
    	}
    	else
    	{
    		mSolveButton.setEnabled(false);
    	}
    }
    
}
