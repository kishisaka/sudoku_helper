package us.ttyl.sudokuhelper;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FieldLoaderAdapter extends BaseAdapter
{
	private List<String> mFieldNames;
	private Activity mActivity;
	
	public FieldLoaderAdapter(Activity activity)
	{
		mActivity = activity;
	}
	
	@Override
	public int getCount()
	{
		return mFieldNames.size();
	}

	public void setFieldNames(List<String> fieldNames)
	{
		mFieldNames = fieldNames;
		notifyDataSetChanged();
	}
	 
	@Override
	public Object getItem(int position) 
	{
		return mFieldNames.get(position);
	}

	@Override
	public long getItemId(int position) 
	{	
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		LayoutInflater inflater = (LayoutInflater)mActivity.getLayoutInflater();
		LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.field_loader_item, null);
		TextView sudokuItemText = (TextView)layout.findViewById(R.id.field_title);
		sudokuItemText.setText(mFieldNames.get(position));  
		return layout;
	}
}
