package us.ttyl.sudokuhelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import us.ttyl.sudokuhelper.model.SudokuItem;

import android.content.Context;
import android.content.SharedPreferences;

public class SudokuHelperPreferences 
{
	private static String USER_GAME_FIELDS = "user_game_fields";
	
	public static void saveField(String field, String key, Context context)
    {
    	SharedPreferences fields = context.getSharedPreferences(USER_GAME_FIELDS, 0);
    	SharedPreferences.Editor editor = fields.edit();
        editor.putString(key, field);
        editor.commit();
    }
	
	public static List<SudokuItem> getField(String key, Context context)
	{
		List <SudokuItem> sudokuItems = new ArrayList<SudokuItem>();
		SharedPreferences fields = context.getSharedPreferences(USER_GAME_FIELDS, 0);
		String field = fields.getString(key, "");
		if (field != null && field.isEmpty() == false)
		{
			StringTokenizer serializedSudokuItems = new StringTokenizer(field, "|");
			while(serializedSudokuItems.hasMoreTokens())
			{
				String item = serializedSudokuItems.nextToken();
				StringTokenizer serializedFields = new StringTokenizer(item, ":");
				String value = serializedFields.nextToken();
				String isGiven = serializedFields.nextToken();
				String isHidden = serializedFields.nextToken();
				String x = serializedFields.nextToken();
				String y = serializedFields.nextToken();
				SudokuHelperActivity.mMode = Integer.parseInt(serializedFields.nextToken());
				SudokuItem sudokuItem = new SudokuItem(value, Boolean.valueOf(isGiven), Integer.parseInt(x), Integer.parseInt(y));
				sudokuItem.setM_hidden(Boolean.valueOf(isHidden));
				sudokuItems.add(sudokuItem);
			}
		}
		return sudokuItems;
	}
	
	public static List<String> getFieldNames(Context context)
	{
		List <String> keys = new ArrayList<String>();
		SharedPreferences fields = context.getSharedPreferences(USER_GAME_FIELDS, 0);
		Map<String,?> map = fields.getAll();
		Set<String> keySet = map.keySet();
		Iterator<String> i = keySet.iterator();
		while (i.hasNext())
		{
			keys.add(i.next());
		}
		return keys;
	}
}
