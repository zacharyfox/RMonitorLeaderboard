package com.zacharyfox.rmonitor.leaderboard.frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

import com.zacharyfox.rmonitor.entities.Race;
import com.zacharyfox.rmonitor.leaderboard.LeaderBoardTable;
import com.zacharyfox.rmonitor.leaderboard.LeaderBoardTableModel;
import com.zacharyfox.rmonitor.leaderboard.Worker;
import com.zacharyfox.rmonitor.utils.Duration;

import java.awt.FlowLayout;

public class MainFrame extends JFrame implements ActionListener
{
	private JMenuItem aboutMenuItem;

	private JMenuItem connectMenuItem;
	private JLabel elapsedTime;
	private JMenu fileMenu;
	private JPanel flagColor;
	private JPanel flagColor_1;
	private JPanel flagColor_2;
	private JPanel flagColor_3;
	private JPanel flagColor_4;
	private JMenu helpMenu;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private LeaderBoardTable leaderBoardTable;
	private JMenuBar menuBar;
	private JPanel titleBar;
	private Race race;
	private JScrollPane resultsScrollPane;
	private JLabel runName;
	private JLabel timeToGo;
	private JLabel trackName;
	private Worker worker;
	private static final long serialVersionUID = -743830529485841322L;
	private JPanel resultsTablePanel;
	private JPanel timeBar;
	private JSeparator separator;

	public MainFrame()
	{
		Font systemLabelFont = UIManager.getFont("Label.font");
		this.setBounds(100, 100, 870, 430);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new MigLayout("", "[grow][grow]", "[][10:10:10][][][grow]"));

		titleBar = new JPanel();
		this.getContentPane().add(titleBar, "cell 0 0 2 1,grow");
		titleBar.setLayout(new GridLayout(1, 0, 0, 0));

		runName = new JLabel("-");
		runName.setFont(new Font(systemLabelFont.getName(), Font.BOLD, systemLabelFont.getSize() + 3));
		titleBar.add(runName);

		trackName = new JLabel("-");
		trackName.setFont(new Font(systemLabelFont.getName(), Font.BOLD, systemLabelFont.getSize() + 3));
		trackName.setHorizontalAlignment(SwingConstants.RIGHT);
		titleBar.add(trackName);

		separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBorder(null);
		getContentPane().add(separator, "cell 0 1 2 1,growx,aligny top");

		timeBar = new JPanel();
		getContentPane().add(timeBar, "cell 0 3 2 1,growx");
		timeBar.setLayout(new MigLayout("", "[50:50:50][grow][grow][grow]", "[][]"));

		flagColor = new JPanel();
		timeBar.add(flagColor, "flowx,cell 0 0 1 2,grow");
		flagColor.setBackground(null);
		flagColor.setBorder(null);
		flagColor.setLayout(new GridLayout(0, 2, 0, 0));

		flagColor_1 = new JPanel();
		flagColor.add(flagColor_1);
		flagColor_1.setBorder(null);
		flagColor_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		flagColor_2 = new JPanel();
		flagColor.add(flagColor_2);
		flagColor_2.setBorder(null);
		flagColor_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		flagColor_3 = new JPanel();
		flagColor.add(flagColor_3);
		flagColor_3.setBorder(null);
		flagColor_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		flagColor_4 = new JPanel();
		flagColor.add(flagColor_4);
		flagColor_4.setBorder(null);
		flagColor_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblNewLabel_1 = new JLabel("Elapsed:");
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		timeBar.add(lblNewLabel_1, "cell 2 0");

		lblNewLabel_2 = new JLabel("To Go:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		timeBar.add(lblNewLabel_2, "cell 3 0");

		elapsedTime = new JLabel(new Duration().toString());
		elapsedTime.setHorizontalAlignment(SwingConstants.RIGHT);
		elapsedTime.setFont(new Font(systemLabelFont.getName(), Font.BOLD, systemLabelFont.getSize() + 3));
		timeBar.add(elapsedTime, "cell 2 1");

		timeToGo = new JLabel(new Duration().toString());
		timeToGo.setHorizontalAlignment(SwingConstants.RIGHT);
		timeToGo.setFont(new Font(systemLabelFont.getName(), Font.BOLD, systemLabelFont.getSize() + 3));
		timeBar.add(timeToGo, "cell 3 1");

		resultsTablePanel = new JPanel();
		getContentPane().add(resultsTablePanel, "cell 0 4 2 1,grow");
		resultsTablePanel.setLayout(new GridLayout(1, 0, 0, 0));

		resultsScrollPane = new JScrollPane();
		resultsTablePanel.add(resultsScrollPane);

		leaderBoardTable = new LeaderBoardTable();
		leaderBoardTable.setIntercellSpacing(new Dimension(10, 1));
		leaderBoardTable.setFillsViewportHeight(true);
		leaderBoardTable.setShowVerticalLines(false);
		leaderBoardTable.setShowHorizontalLines(false);
		leaderBoardTable.setShowGrid(false);
		leaderBoardTable.setRowMargin(2);
		leaderBoardTable.setRowHeight(18);
		leaderBoardTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		leaderBoardTable.setRowSelectionAllowed(false);
		resultsScrollPane.setViewportView(leaderBoardTable);

		menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.menu);
		menuBar.setBorder(UIManager.getBorder("Menu.border"));
		this.setJMenuBar(menuBar);

		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		connectMenuItem = new JMenuItem("Connection");
		connectMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				ConnectFrame newFrame = ConnectFrame.getInstance(MainFrame.this);
				newFrame.setVisible(true);
			}
		});

		fileMenu.add(connectMenuItem);

		helpMenu = new JMenu("Help");
		helpMenu.setBorder(UIManager.getBorder("MenuItem.border"));
		helpMenu.setBackground(SystemColor.menu);
		menuBar.add(helpMenu);

		aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.setBorder(UIManager.getBorder("MenuItem.border"));
		aboutMenuItem.setBackground(SystemColor.menu);
		aboutMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				AboutFrame newFrame = new AboutFrame();
				newFrame.setVisible(true);
			}
		});

		helpMenu.add(aboutMenuItem);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("Connect")) {
			race = new Race();
			race.addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt)
				{
					updateDisplay(evt);
				}
			});
			worker = new Worker(ConnectFrame.getInstance(this), race);
			worker.execute();
		} else if (e.getActionCommand().equals("Disconnect")) {
			worker.cancel(true);
		}

		return;
	}

	private void setFlagColor(String status)
	{
		// TODO: this is expedient, but not elegant.
		if (status.equals("Green")) {
			flagColor_1.setBackground(new Color(0, 150, 0));
			flagColor_2.setBackground(new Color(0, 150, 0));
			flagColor_3.setBackground(new Color(0, 150, 0));
			flagColor_4.setBackground(new Color(0, 150, 0));
		} else if (status.equals("Yellow")) {
			flagColor_1.setBackground(new Color(255, 255, 0));
			flagColor_2.setBackground(new Color(255, 255, 0));
			flagColor_3.setBackground(new Color(255, 255, 0));
			flagColor_4.setBackground(new Color(255, 255, 0));
		} else if (status.equals("Red")) {
			flagColor_1.setBackground(new Color(255, 0, 0));
			flagColor_2.setBackground(new Color(255, 0, 0));
			flagColor_3.setBackground(new Color(255, 0, 0));
			flagColor_4.setBackground(new Color(255, 0, 0));
		} else if (status.equals("Finish")) {
			flagColor_1.setBackground(new Color(0, 0, 0));
			flagColor_2.setBackground(new Color(255, 255, 255));
			flagColor_3.setBackground(new Color(255, 255, 255));
			flagColor_4.setBackground(new Color(0, 0, 0));
		}
	}

	private void updateDisplay(PropertyChangeEvent evt)
	{
		if (evt.getPropertyName().equals("name")) {
			runName.setText((String) evt.getNewValue());
		}

		if (evt.getPropertyName().equals("elapsedTime")) {
			elapsedTime.setText(((Duration) evt.getNewValue()).toString());
		}

		if (evt.getPropertyName().equals("timeToGo")) {
			timeToGo.setText(((Duration) evt.getNewValue()).toString());
		}

		if (evt.getPropertyName().equals("competitorsVersion")) {
			((LeaderBoardTableModel) leaderBoardTable.getModel()).updateData();
		}

		if (evt.getPropertyName().equals("flagStatus")) {
			setFlagColor(evt.getNewValue().toString());
		}

		if (evt.getPropertyName().equals("trackName")) {
			trackName.setText(evt.getNewValue().toString());
		}

		if (evt.getPropertyName().equals("trackLength")) {
			// TODO: Handle Track Length
			// trackLength.setText(evt.getNewValue().toString());
		}
	}
}