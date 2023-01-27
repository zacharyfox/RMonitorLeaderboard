package com.zacharyfox.rmonitor.leaderboard;

import javax.swing.JTable;

import com.zacharyfox.rmonitor.utils.Duration;

public class FinishLineLogTable extends JTable
{
	private static final long serialVersionUID = -6458659058033888484L;
	private FinishlineLogTableModel finishLineLogTableModel = new FinishlineLogTableModel();

	private int rowHeight;
	
	public FinishLineLogTable(int rowHeight)
	{
		super();
		this.rowHeight = rowHeight;
		this.setModel(finishLineLogTableModel);
		this.setDefaultRenderer(Duration.class, new LeaderBoardTableCellRenderer());
		
		this.initColumns();
		
	}
	
	private void initColumns()
	{
		Integer smallColumnSize = 10*rowHeight;
		Integer smallColumnMaxSize = 12*rowHeight;
		Integer bigColumnSize = 60*rowHeight;
		
		Integer[] smallColumns = {0, 1, 3 };
		Integer[] bigColumns = {2};
		
		for (Integer column : smallColumns) {
			this.getColumnModel().getColumn(column).setPreferredWidth(smallColumnSize);
			this.getColumnModel().getColumn(column).setMaxWidth(smallColumnMaxSize);

		}
		
		for (Integer column : bigColumns) {
			this.getColumnModel().getColumn(column).setPreferredWidth(bigColumnSize);
		}
	}
}
