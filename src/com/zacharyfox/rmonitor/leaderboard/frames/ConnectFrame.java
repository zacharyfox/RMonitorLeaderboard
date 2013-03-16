package com.zacharyfox.rmonitor.leaderboard.frames;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

public class ConnectFrame extends JFrame
{
	public JButton connectButton;
	public JTextField ip;
	public JTextField port;
	private JLabel ipLabel;
	private JLabel portLabel;
	private static ConnectFrame instance;
	private static final long serialVersionUID = 3848021032174790659L;
	
	private ConnectFrame(MainFrame mainFrame)
	{
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][]"));
		
		ipLabel = new JLabel("Scoreboard IP:");
		ipLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(ipLabel, "cell 0 0,alignx trailing");
		setBounds(100, 100, 400, 150);
		
		ip = new JTextField();
		ip.setText("192.168.1.4");
		getContentPane().add(ip, "cell 1 0,growx");
		ip.setColumns(10);
		
		portLabel = new JLabel("Scoreboard Port:");
		portLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(portLabel, "cell 0 1,alignx trailing");
		
		port = new JTextField();
		port.setText("50000");
		getContentPane().add(port, "cell 1 1,growx");
		port.setColumns(10);
		
		connectButton = new JButton("Connect");
		connectButton.setHorizontalAlignment(SwingConstants.RIGHT);
		connectButton.addActionListener(mainFrame);
		getContentPane().add(connectButton, "cell 1 2,alignx right");
	}
	
	public String getIP()
	{
		return ip.getText();
	}
	
	public Integer getPort()
	{
		return Integer.parseInt(port.getText());
	}
	
	public static ConnectFrame getInstance(MainFrame mainFrame)
	{
		if (instance == null) {
			instance = new ConnectFrame(mainFrame);
		}
		
		return instance;
	}
}