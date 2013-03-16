package com.zacharyfox.rmonitor.leaderboard;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.zacharyfox.rmonitor.entities.Competitor;
import com.zacharyfox.rmonitor.entities.RaceClass;

public class LeaderBoardTableModel extends AbstractTableModel
{
	private String[] columnNames = new String[] {
		"Pos", "PIC", "#", "Class", "Name", "Laps", "Total Time", "Last Time", "Best Time", "Avg. Time"
	};

	private ArrayList<Object[]> data = new ArrayList<Object[]>();
	
	private static final long serialVersionUID = 2982628995537227551L;
	
	public LeaderBoardTableModel()
	{
		super();
		data.add(new Object[]{"", "", "", "", "", "", "", "", "", ""});
	}
	
	@Override
	public Class<?> getColumnClass(int c)
	{
		return getValueAt(0, c).getClass();
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
		return data.get(row)[col];
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

		for (Competitor competitor : Competitor.getInstances().values()) {
			rows.add(getRow(competitor));
		}

		data = rows;
		fireTableDataChanged();
	}

	private Object[] getRow(Competitor competitor)
	{
		return new Object[] {
			competitor.getPosition(), competitor.getPositionInClass(), competitor.getRegNumber(), RaceClass.getClassName(competitor.getClassId()),
			competitor.getFirstName() + " " + competitor.getLastName(), competitor.getLapsComplete(),
			competitor.getTotalTime(), competitor.getLastLap(), competitor.getBestLap(), competitor.getAvgLap(), ""
		};
	}
}
