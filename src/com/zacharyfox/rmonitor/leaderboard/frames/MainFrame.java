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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

import com.zacharyfox.rmonitor.entities.Race;
import com.zacharyfox.rmonitor.leaderboard.LeaderBoardTable;
import com.zacharyfox.rmonitor.leaderboard.LeaderBoardTableModel;
import com.zacharyfox.rmonitor.leaderboard.Worker;

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
	private JLabel lblNewLabel_3;
	private LeaderBoardTable leaderBoardTable;
	private JMenuBar menuBar;
	private JPanel panel_1;
	private JPanel panel_2;
	private Race race;
	private JScrollPane resultsScrollPane;
	private JLabel runName;
	private JLabel scheduledTime;
	private JSeparator separator;
	private JTabbedPane tabbedPane;
	private JLabel timeToGo;
	private JLabel trackName;
	private Worker worker;
	private static final long serialVersionUID = -743830529485841322L;

	public MainFrame()
	{
		this.setBounds(100, 100, 870, 430);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(
			new MigLayout("", "[grow][grow]", "[:31.00:30.00,grow][][::25.00,grow][240.00,grow]"));

		panel_1 = new JPanel();
		this.getContentPane().add(panel_1, "cell 0 0 2 1,grow");
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));

		runName = new JLabel("Unknown Run");
		panel_1.add(runName);
		runName.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		flagColor = new JPanel();
		panel_1.add(flagColor);
		flagColor.setBackground(null);
		flagColor.setBorder(null);
		flagColor.setLayout(new GridLayout(2, 2, 0, 0));

		flagColor_1 = new JPanel();
		flagColor_1.setBorder(null);
		flagColor.add(flagColor_1);

		flagColor_2 = new JPanel();
		flagColor_2.setBorder(null);
		flagColor.add(flagColor_2);

		flagColor_3 = new JPanel();
		flagColor_3.setBorder(null);
		flagColor.add(flagColor_3);

		flagColor_4 = new JPanel();
		flagColor_4.setBorder(null);
		flagColor.add(flagColor_4);

		trackName = new JLabel("Unknown Track");
		panel_1.add(trackName);
		trackName.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		trackName.setHorizontalAlignment(SwingConstants.RIGHT);

		separator = new JSeparator();
		separator.setPreferredSize(new Dimension(32767, 12));
		separator.setForeground(new Color(169, 169, 169));
		this.getContentPane().add(separator, "cell 0 1 2 1");

		panel_2 = new JPanel();
		this.getContentPane().add(panel_2, "cell 0 2 2 1,grow");
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));

		lblNewLabel_1 = new JLabel("Elapsed Time:");
		panel_2.add(lblNewLabel_1);

		elapsedTime = new JLabel("00:00:00");
		panel_2.add(elapsedTime);

		lblNewLabel_2 = new JLabel("Time To Go:");
		panel_2.add(lblNewLabel_2);

		timeToGo = new JLabel("00:00:00");
		panel_2.add(timeToGo);

		lblNewLabel_3 = new JLabel("Race Length:");
		panel_2.add(lblNewLabel_3);

		scheduledTime = new JLabel("00:00:00");
		panel_2.add(scheduledTime);

		tabbedPane = new JTabbedPane(SwingConstants.TOP);
		tabbedPane.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		this.getContentPane().add(tabbedPane, "cell 0 3 2 1,grow");

		resultsScrollPane = new JScrollPane();
		tabbedPane.addTab("Race", null, resultsScrollPane, null);

		leaderBoardTable = new LeaderBoardTable();
		leaderBoardTable.setShowVerticalLines(false);
		leaderBoardTable.setShowHorizontalLines(false);
		leaderBoardTable.setShowGrid(false);
		leaderBoardTable.setRowMargin(2);
		leaderBoardTable.setRowHeight(18);
		leaderBoardTable.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
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
			elapsedTime.setText(evt.getNewValue().toString());
		}

		if (evt.getPropertyName().equals("timeToGo")) {
			timeToGo.setText(evt.getNewValue().toString());
		}

		if (evt.getPropertyName().equals("scheduledTime")) {
			scheduledTime.setText(evt.getNewValue().toString());
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
