package com.zacharyfox.rmonitor.leaderboard;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableRowSorter;

import net.miginfocom.swing.MigLayout;

import com.zacharyfox.rmonitor.entities.Race;

public class LeaderBoard implements ActionListener
{

	private JButton connectButton;
	private JFrame frame;
	private JTextField ip;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel timeToGo;
	private JLabel lblServerIp;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JTextField port;
	private Race race;
	private JScrollPane resultsScrollPane;
	private JLabel runName;
	private JTabbedPane tabbedPane;
	private JTable leaderBoard;
	private Table leaderBoardTable = new Table();
	private JLabel trackName;
	private Worker worker;
	private JLabel elapsedTime;
	private JLabel scheduledTime;
	private JLabel lblNewLabel_3;

	/**
	 * Create the application.
	 */
	public LeaderBoard()
	{
		initialize();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("Connect")) {
			System.out.println("Executing worker");
			race = new Race();
			race.addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt)
				{
					updateDisplay(evt);
				}
			});
			worker = new Worker(ip.getText(), Integer.parseInt(port.getText()), connectButton, race);
			worker.execute();
		} else if (e.getActionCommand().equals("Disconnect")) {
			System.out.println("Sending cancel");
			worker.cancel(true);
		} else {
			System.out.println(e.getActionCommand());
		}

		return;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBackground(SystemColor.activeCaption);
		frame.getContentPane().setBackground(new Color(220, 220, 220));
		frame.setBounds(100, 100, 800, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
			new MigLayout("", "[grow]", "[:40.00:40px,grow][:31.00:30.00,grow][::25.00,grow][240.00]"));

		panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBackground(new Color(220, 220, 220));
		frame.getContentPane().add(panel, "cell 0 0,alignx right,aligny top");
		panel.setLayout(new GridLayout(0, 5, 0, 0));

		lblServerIp = new JLabel("Server IP:");
		lblServerIp.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblServerIp);

		ip = new JTextField();
		panel.add(ip);
		ip.setText("192.168.1.4");
		ip.setColumns(10);

		lblNewLabel = new JLabel("Server Port:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel);

		port = new JTextField();
		panel.add(port);
		port.setText("50000");
		port.setColumns(10);

		connectButton = new JButton("Connect");
		connectButton.addActionListener(this);
		panel.add(connectButton);

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(220, 220, 220));
		frame.getContentPane().add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));

		runName = new JLabel("Unknown Run");
		panel_1.add(runName);
		runName.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		trackName = new JLabel("Unknown Track");
		panel_1.add(trackName);
		trackName.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		trackName.setHorizontalAlignment(SwingConstants.RIGHT);

		panel_2 = new JPanel();
		panel_2.setBackground(new Color(220, 220, 220));
		frame.getContentPane().add(panel_2, "cell 0 2,grow");
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

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, "cell 0 3,grow");

		resultsScrollPane = new JScrollPane();
		tabbedPane.addTab("Race", null, resultsScrollPane, null);

		leaderBoard = new JTable();
		leaderBoard.setModel(leaderBoardTable);
		TableRowSorter<Table> sorter = new TableRowSorter<Table>(leaderBoardTable);
		leaderBoard.setRowSorter(sorter);
		sorter.setSortsOnUpdates(true);
		
		resultsScrollPane.setViewportView(leaderBoard);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				try {
					LeaderBoard window = new LeaderBoard();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void updateDisplay(PropertyChangeEvent evt)
	{
		// System.out.println("Property Change Event: " + evt.getPropertyName());

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
			leaderBoardTable.updateData();

		}
	}

}
