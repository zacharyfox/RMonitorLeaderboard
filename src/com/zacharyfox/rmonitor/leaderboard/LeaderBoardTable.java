package com.zacharyfox.rmonitor.leaderboard;

import javax.swing.JTable;
import javax.swing.table.TableRowSorter;

import com.zacharyfox.rmonitor.utils.Duration;

public class LeaderBoardTable extends JTable
{
	private static final long serialVersionUID = -6458659058033888484L;
	private LeaderBoardTableModel leaderBoardTableModel = new LeaderBoardTableModel();
	private TableRowSorter<LeaderBoardTableModel> sorter = new TableRowSorter<LeaderBoardTableModel>(leaderBoardTableModel);

	public LeaderBoardTable()
	{
		super();

		this.setModel(leaderBoardTableModel);
		this.setRowSorter(sorter);
		this.setDefaultRenderer(Duration.class, new LeaderBoardTableCellRenderer());
		
		this.initColumns();
		
		sorter.setSortsOnUpdates(true);
		sorter.toggleSortOrder(0);
	}
	
	private void initColumns()
	{
		Integer smallColumnSize = 40;
		Integer timeColumnSize = 100;
		
		Integer[] smallColumns = {0, 1 , 2, 3, 5};
		Integer[] timeColumns = {6, 7, 8, 9};
		
		for (Integer column : smallColumns) {
			this.getColumnModel().getColumn(column).setPreferredWidth(smallColumnSize);
		}
		
		for (Integer column : timeColumns) {
			this.getColumnModel().getColumn(column).setPreferredWidth(timeColumnSize);
		}
	}
}
