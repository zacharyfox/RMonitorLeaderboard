package com.zacharyfox.rmonitor.leaderboard.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.zacharyfox.rmonitor.utils.Player;

public class PlayerFrame extends JFrame implements ActionListener
{
	private JFileChooser chooser;
	private final MainFrame mainFrame;
	private final JTextField playerFile;
	private final JButton selectFileButton;
	private final JButton startStop;
	private static PlayerFrame instance;
	private static Player player;
	private static final long serialVersionUID = -9179041103033981780L;

	private PlayerFrame(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;

		getContentPane().setLayout(new MigLayout("", "[grow][][]", "[][]"));
		setBounds(100, 100, 400, 150);

		playerFile = new JTextField();
		getContentPane().add(playerFile, "cell 0 0,growx");
		playerFile.setColumns(10);

		selectFileButton = new JButton("Open");
		selectFileButton.addActionListener(this);
		getContentPane().add(selectFileButton, "cell 1 0");

		startStop = new JButton("Start");
		startStop.setEnabled(false);
		startStop.addActionListener(this);
		getContentPane().add(startStop, "cell 2 0");
	}

	@Override
	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getActionCommand().equals("Open")) {
			chooser = new JFileChooser();
			chooser.setSelectedFile(new File("leaderboard-recording.txt"));

			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				playerFile.setText(chooser.getSelectedFile().toString());
				startStop.setEnabled(true);
			}
		} else if (evt.getActionCommand().equals("Start")) {
			startStop.setText("Stop");
			playerFile.setEnabled(false);
			selectFileButton.setEnabled(false);
			player = new Player(playerFile.getText());
			player.execute();

		} else if (evt.getActionCommand().equals("Stop")) {
			player.close();
			startStop.setText("Start");
			playerFile.setEnabled(true);
			selectFileButton.setEnabled(true);
		}
	}

	public static PlayerFrame getInstance(MainFrame mainFrame)
	{
		if (instance == null) {
			instance = new PlayerFrame(mainFrame);
		}

		return instance;
	}
}
