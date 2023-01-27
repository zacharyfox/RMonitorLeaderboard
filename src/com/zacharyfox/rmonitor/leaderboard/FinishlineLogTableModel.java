package com.zacharyfox.rmonitor.leaderboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.zacharyfox.rmonitor.entities.Competitor;

public class FinishlineLogTableModel extends AbstractTableModel
{
	private String[] columnNames = new String[] {
	//	"Pos", "PIC", "#", "Class", "Name", "Laps", "Total Time", "Last Time", "Best Time", "Avg. Time"
			 "No.", "Laps", "Name", "Pos"
	};

	private ArrayList<Object[]> data = new ArrayList<Object[]>();
	
	private static final long serialVersionUID = 2982628995537227551L;
	
	public FinishlineLogTableModel()
	{
		super();
		data.add(new Object[]{"", "", "", ""});
	}
	
	@Override
	public Class<?> getColumnClass(int c)
	{
		if (data.size() > 0 ) return getValueAt(0, c).getClass();
		return Object.class;
	}

	@Override
	public int getColumnCount()
	{
		return columnNames.length;
	}

	@Override
	public String getColumnName(int col)
	{
		return columnNames[col];
	}

	@Override
	public int getRowCount()
	{
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col)
	{
		if (data.size()> row) return data.get(row)[col];
		return null;
	}

	/*
	 * Don't need to implement this method unless your table's data can change.
	 */
	@Override
	public void setValueAt(Object value, int row, int col)
	{
		data.get(row)[col] = value;
		fireTableCellUpdated(row, col);
	}

	public void updateData()
	{
		ArrayList<Object[]> rows = new ArrayList<Object[]>();

		//Set<Entry<String, Integer>> set = hm.entrySet();
        List<Competitor> list = new ArrayList<Competitor>();
		for (Competitor competitor : Competitor.getInstances().values()) {
			if (competitor.getLapsComplete()>0) list.add(competitor);
		}
       
        Collections.sort(list, new Comparator<Competitor>() {
        	@Override public int compare(Competitor c1, Competitor c2) {
                return Float.compare(c2.getTotalTime().toFloat(), c1.getTotalTime().toFloat()) ;
            }
        });
		for (Competitor competitor : list) {
			//System.out.println(competitor.getRegNumber() + " " + competitor.getTotalTime().toFloat());
			rows.add(getRow(competitor));
		}

		data = rows;
		if (data.size() > 0) fireTableDataChanged();
	}

	private Object[] getRow(Competitor competitor)
	{
		return new Object[] {
			competitor.getRegNumber(), competitor.getLapsComplete(),
			competitor.getFirstName() + " " + competitor.getLastName(), 
			competitor.getPosition(), ""
		};
	}
}
