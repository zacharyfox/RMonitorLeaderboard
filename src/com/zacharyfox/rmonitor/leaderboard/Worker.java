package com.zacharyfox.rmonitor.leaderboard;

import java.util.List;

import javax.swing.JButton;
import javax.swing.SwingWorker;

import com.zacharyfox.rmonitor.entities.Race;
import com.zacharyfox.rmonitor.message.Factory;
import com.zacharyfox.rmonitor.utils.Connection;

public class Worker extends SwingWorker<Integer, String>
{
	private JButton connectButton;
	private Connection connection;
	private String ip;
	private int port;
	private Race race;

	public Worker(String ip, int port, JButton connectButton, Race race)
	{
		this.ip = ip;
		this.port = port;
		this.connectButton = connectButton;
		this.race = race;
	}

	@Override
	protected Integer doInBackground() throws Exception
	{
		String line;

		try {
			publish("connecting");
			connection = new Connection(ip, port);
			publish("connected");
			while ((line = connection.readLine()) != null && !isCancelled()) {
				publish(line);
			}

			if (isCancelled()) {
				publish("disconnecting");
				connection.close();
				publish("disconnected");
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			publish("connect error");
		}
		return null;
	}

	@Override
	protected void process(List<String> chunks)
	{
		for (String message : chunks) {
			if ("connecting".equals(message)) {
				connectButton.setText("Connecting...");
				connectButton.setEnabled(false);
			}

			if ("connected".equals(message)) {
				connectButton.setText("Disconnect");
				connectButton.setEnabled(true);
			}

			if ("disconnecting".equals(message)) {
				connectButton.setText("Disonnecting...");
				connectButton.setEnabled(false);
			}

			if ("disconnected".equals(message)) {
				connectButton.setText("Connect");
				connectButton.setEnabled(true);
			}

			if (message.substring(0, 1).equals("$")) {
				race.update(Factory.getMessage(message));
			}
		}
	}
}
