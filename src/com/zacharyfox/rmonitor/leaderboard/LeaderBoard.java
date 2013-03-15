package com.zacharyfox.rmonitor.leaderboard;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import net.miginfocom.swing.MigLayout;

import com.zacharyfox.rmonitor.entities.Race;
import javax.swing.JSeparator;
import javax.swing.JTable;
import java.awt.Component;

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
	private LeaderBoardTable leaderBoardTable;
	private JLabel trackName;
	private Worker worker;
	private JLabel elapsedTime;
	private JLabel scheduledTime;
	private JLabel lblNewLabel_3;
	private JSeparator separator;
	private JPanel flagColor;
	private JPanel flagColor_1;
	private JPanel flagColor_3;
	private JPanel flagColor_2;
	private JPanel flagColor_4;

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
			worker.cancel(true);
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
		frame.setBounds(100, 100, 870, 430);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
			new MigLayout("", "[grow][grow]", "[:40.00:40px,grow][:31.00:30.00,grow][][::25.00,grow][240.00,grow]"));
		
		flagColor = new JPanel();
		flagColor.setBackground(null);
		flagColor.setBorder(null);
		frame.getContentPane().add(flagColor, "cell 0 0,grow");
		flagColor.setLayout(new GridLayout(2, 2, 0, 0));
		
		flagColor_1 = new JPanel();
		flagColor_1.setBackground(new Color(220, 220, 220));
		flagColor_1.setBorder(null);
		flagColor.add(flagColor_1);
		
		flagColor_2 = new JPanel();
		flagColor_2.setBackground(new Color(220, 220, 220));
		flagColor_2.setBorder(null);
		flagColor.add(flagColor_2);
		
		flagColor_3 = new JPanel();
		flagColor_3.setBackground(new Color(220, 220, 220));
		flagColor_3.setBorder(null);
		flagColor.add(flagColor_3);
		
		flagColor_4 = new JPanel();
		flagColor_4.setBackground(new Color(220, 220, 220));
		flagColor_4.setBorder(null);
		flagColor.add(flagColor_4);

		panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBackground(new Color(220, 220, 220));
		frame.getContentPane().add(panel, "cell 1 0,alignx right,aligny top");
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
		frame.getContentPane().add(panel_1, "cell 0 1 2 1,grow");
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));

		runName = new JLabel("Unknown Run");
		panel_1.add(runName);
		runName.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		trackName = new JLabel("Unknown Track");
		panel_1.add(trackName);
		trackName.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		trackName.setHorizontalAlignment(SwingConstants.RIGHT);
		
		separator = new JSeparator();
		separator.setPreferredSize(new Dimension(32767, 12));
		separator.setForeground(new Color(169, 169, 169));
		frame.getContentPane().add(separator, "cell 0 2 2 1");

		panel_2 = new JPanel();
		panel_2.setBackground(new Color(220, 220, 220));
		frame.getContentPane().add(panel_2, "cell 0 3 2 1,grow");
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
		tabbedPane.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		frame.getContentPane().add(tabbedPane, "cell 0 4 2 1,grow");

		resultsScrollPane = new JScrollPane();
		tabbedPane.addTab("Race", null, resultsScrollPane, null);

		leaderBoardTable = new LeaderBoardTable();
		leaderBoardTable.setRowMargin(2);
		leaderBoardTable.setRowHeight(18);
		leaderBoardTable.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		leaderBoardTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		leaderBoardTable.setRowSelectionAllowed(false);
		resultsScrollPane.setViewportView(leaderBoardTable);
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
			((LeaderBoardTableModel) leaderBoardTable.getModel()).updateData();
		}
		
		if (evt.getPropertyName().equals("flagStatus")) {
			setFlagColor(evt.getNewValue().toString());
		}
		
		if (evt.getPropertyName().equals("trackName")){
			trackName.setText(evt.getNewValue().toString());
		}
		
		if (evt.getPropertyName().equals("trackLength")) {
			// TODO: Handle Track Length
			// trackLength.setText(evt.getNewValue().toString());
		}
	}

	private void setFlagColor(String status)
	{
		// TODO: this is expedient, but not elegant.
		if (status.equals("Green")) {
			flagColor_1.setBackground(new Color(0, 255, 0));
			flagColor_2.setBackground(new Color(0, 255, 0));
			flagColor_3.setBackground(new Color(0, 255, 0));
			flagColor_4.setBackground(new Color(0, 255, 0));
		} else if (status.equals("Yellow")) {
			flagColor_1.setBackground(new Color(255, 255, 0));
			flagColor_2.setBackground(new Color(255, 255, 0));
			flagColor_3.setBackground(new Color(255, 255, 0));
			flagColor_4.setBackground(new Color(255, 255, 0));
		} else if (status.equals("Red")) {
			flagColor_1.setBackground(new Color(255, 0 ,0));
			flagColor_2.setBackground(new Color(255, 0 ,0));
			flagColor_3.setBackground(new Color(255, 0 ,0));
			flagColor_4.setBackground(new Color(255, 0 ,0));
		} else if (status.equals("Finish")) {
			flagColor_1.setBackground(new Color(0, 0, 0));
			flagColor_2.setBackground(new Color(255, 255, 255));
			flagColor_3.setBackground(new Color(255, 255, 255));
			flagColor_4.setBackground(new Color(0, 0, 0));
		}
		
	}
}
