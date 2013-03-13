package com.zacharyfox.rmonitor.leaderboard;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableRowSorter;

import com.zacharyfox.rmonitor.entities.Race;

public class LeaderBoard implements ActionListener
{

	private JButton connectButton = new JButton("Connect");

	private JLabel elapsedTime = new JLabel("00:00:00");
	private JLabel estLaps = new JLabel("0");
	private JFrame frame;

	private JTable leaderBoard;
	private Table leaderBoardTable = new Table();
	private Race race = new Race();
	// Info
	private JLabel raceName = new JLabel("Unknown");
	private JLabel scheduledTime = new JLabel("00:00:00");

	// Controls
	private JTextField ip = new JTextField("192.168.1.4");
	private JTextField port = new JTextField("50000");
	private JScrollPane scrollPane;

	private JSeparator separator;
	private JSeparator separator_1;
	private JLabel timeToGo = new JLabel("00:00:00");
	private Worker worker;

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
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Server IP:");
		lblNewLabel.setBounds(6, 10, 65, 20);
		frame.getContentPane().add(lblNewLabel);

		ip.setBounds(64, 6, 134, 28);
		lblNewLabel.setLabelFor(ip);
		frame.getContentPane().add(ip);
		ip.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Server Port:");
		lblNewLabel_1.setBounds(201, 12, 71, 16);
		frame.getContentPane().add(lblNewLabel_1);

		port.setBounds(277, 6, 134, 28);
		lblNewLabel_1.setLabelFor(port);
		frame.getContentPane().add(port);
		port.setColumns(10);

		connectButton.setBounds(415, 7, 117, 29);
		frame.getContentPane().add(connectButton);

		JLabel lblNewLabel_2 = new JLabel("Race:");
		lblNewLabel_2.setBounds(6, 59, 39, 16);
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		frame.getContentPane().add(lblNewLabel_2);

		raceName.setBounds(45, 59, 480, 16);
		frame.getContentPane().add(raceName);

		JLabel lblNewLabel_3 = new JLabel("Time To Go:");
		lblNewLabel_3.setBounds(6, 87, 87, 16);
		lblNewLabel_3.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		frame.getContentPane().add(lblNewLabel_3);
		timeToGo.setBounds(89, 87, 64, 16);
		frame.getContentPane().add(timeToGo);

		JLabel lblNewLabel_4 = new JLabel("Elapsed Time:");
		lblNewLabel_4.setBounds(179, 87, 102, 16);
		lblNewLabel_4.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		frame.getContentPane().add(lblNewLabel_4);
		elapsedTime.setBounds(278, 87, 61, 16);
		frame.getContentPane().add(elapsedTime);

		JLabel lblNewLabel_5 = new JLabel("Race Length:");
		lblNewLabel_5.setBounds(379, 87, 88, 16);
		lblNewLabel_5.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		frame.getContentPane().add(lblNewLabel_5);
		scheduledTime.setBounds(471, 87, 61, 16);
		frame.getContentPane().add(scheduledTime);

		JLabel lblNewLabel_9 = new JLabel("Estimated Laps:");
		lblNewLabel_9.setBounds(6, 115, 134, 16);
		lblNewLabel_9.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		frame.getContentPane().add(lblNewLabel_9);

		estLaps.setBounds(137, 116, 61, 16);
		estLaps.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		frame.getContentPane().add(estLaps);

		separator = new JSeparator();
		separator.setBounds(5, 45, 790, 10);
		separator.setForeground(SystemColor.inactiveCaption);
		separator.setBackground(SystemColor.inactiveCaption);
		frame.getContentPane().add(separator);

		separator_1 = new JSeparator();
		separator_1.setBounds(5, 134, 790, 10);
		separator_1.setForeground(Color.GRAY);
		separator_1.setBackground(Color.GRAY);
		frame.getContentPane().add(separator_1);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 145, 790, 327);
		frame.getContentPane().add(scrollPane);

		leaderBoard = new JTable();
		leaderBoard.setModel(leaderBoardTable);
		scrollPane.setViewportView(leaderBoard);
		TableRowSorter<Table> sorter = new TableRowSorter<Table>(leaderBoardTable);
		leaderBoard.setRowSorter(sorter);
		sorter.setSortsOnUpdates(true);

		connectButton.addActionListener(this);
	}

	private void updateDisplay(PropertyChangeEvent evt)
	{
		System.out.println("Property Change Event: " + evt.getPropertyName());

		if (evt.getPropertyName().equals("name")) {
			raceName.setText((String) evt.getNewValue());
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
			@Override
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
}
