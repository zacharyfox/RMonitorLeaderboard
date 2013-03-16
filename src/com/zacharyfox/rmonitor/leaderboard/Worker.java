package com.zacharyfox.rmonitor.leaderboard;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import com.zacharyfox.rmonitor.entities.Race;
import com.zacharyfox.rmonitor.leaderboard.frames.ConnectFrame;
import com.zacharyfox.rmonitor.message.Factory;
import com.zacharyfox.rmonitor.utils.Connection;
import com.zacharyfox.rmonitor.utils.Recorder;

public class Worker extends SwingWorker<Integer, String>
{
	private final JButton connectButton;
	private Connection connection;
	private final JTextField ip;
	private final JTextField port;
	private final Race race;
	private Recorder recorder;

	public Worker(ConnectFrame connectFrame, Race race)
	{
		this.ip = connectFrame.ip;
		this.port = connectFrame.port;
		this.connectButton = connectFrame.connectButton;
		this.race = race;
	}

	public void removeRecorder()
	{
		this.recorder = null;
	}

	public void setRecorder(Recorder recorder)
	{
		this.recorder = recorder;
	}

	@Override
	protected Integer doInBackground() throws Exception
	{
		String line;

		try {
			publish("connecting");
			connection = new Connection(ip.getText(), Integer.parseInt(port.getText()));
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
				ip.setEnabled(false);
				port.setEnabled(false);
				connectButton.setEnabled(true);
				SwingUtilities.windowForComponent(connectButton).setVisible(false);
			}

			if ("disconnecting".equals(message)) {
				connectButton.setText("Disonnecting...");
				connectButton.setEnabled(false);
			}

			if ("disconnected".equals(message)) {
				connectButton.setText("Connect");
				ip.setEnabled(true);
				port.setEnabled(true);
				connectButton.setEnabled(true);
			}

			if (message.substring(0, 1).equals("$")) {
				race.update(Factory.getMessage(message));
				if (recorder != null) {
					recorder.push(message);
				}
			}
		}
	}
}
