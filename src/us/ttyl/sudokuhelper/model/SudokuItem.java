package us.ttyl.sudokuhelper.model;

import us.ttyl.sudokuhelper.SudokuHelperActivity;

public class SudokuItem
{
	private String m_item;
	private boolean m_given = false;
	private boolean m_isHidden = true;
	private int mX = 0;
	private int mY = 0;
	
	public SudokuItem(String item, boolean given, int x, int y)
	{
		m_item = item;
		m_given = given;
		mX = x;
		mY = y;
	}	
	
	
	public int getmX() {
		return mX;
	}


	public int getmY() {
		return mY;
	}

	public String getM_item() {
		return m_item;
	}

	public void setM_item(String m_item) {
		this.m_item = m_item;
	}

	public boolean isM_given() {
		return m_given;
	}

	public void setM_given(boolean m_given) {
		this.m_given = m_given;
	}

	public boolean isM_hidden() {
		return m_isHidden;
	}

	public void setM_hidden(boolean m_hidden) {
		this.m_isHidden = m_hidden;
	}
	
	/**
	 * dump the SudokuItem to the following format:
	 * "m_item:m_given:m_isHidden:m_x:m_y:mmode"
	 * 
	 * example: 
	 * "3:true:false:10:10:0"
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(m_item + ":");
		sb.append(m_given + ":");
		sb.append(m_isHidden + ":");
		sb.append(mX + ":");
		sb.append(mY + ":");
		sb.append(SudokuHelperActivity.mMode);
		return sb.toString();
	}
	
}