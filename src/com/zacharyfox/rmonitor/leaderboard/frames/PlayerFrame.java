package com.zacharyfox.rmonitor.leaderboard.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.ini4j.IniPreferences;

import net.miginfocom.swing.MigLayout;

import com.zacharyfox.rmonitor.utils.Player;

public class PlayerFrame extends JFrame implements ActionListener
{
	private JFileChooser chooser;
	private final MainFrame mainFrame;
	private final JTextField playerFile;
	private final JTextField playerSpeedup;
	
	private final JButton selectFileButton;
	private final JButton startStop;
	private final JButton pause;
	private static PlayerFrame instance;
	private static Player player;
	private static final long serialVersionUID = -9179041103033981780L;
	private Preferences playerPrefs;

	private PlayerFrame(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		
		playerPrefs = new IniPreferences(mainFrame.getIni()).node("Player");
		
		getContentPane().setLayout(new MigLayout("", "[grow][][][][]", "[][]"));
		setBounds(100, 100, 600, 150);

		playerFile = new JTextField();
		getContentPane().add(playerFile, "cell 0 0,growx");
		playerFile.setText(playerPrefs.get("LastFile", ""));
		playerFile.setColumns(10);

		selectFileButton = new JButton("Open");
		selectFileButton.addActionListener(this);
		getContentPane().add(selectFileButton, "cell 1 0");

		playerSpeedup = new JTextField();
		getContentPane().add(playerSpeedup, "cell 2 0");
		playerSpeedup.setText(playerPrefs.get("Speedup", "2"));
		playerSpeedup.setColumns(3);

		startStop = new JButton("Start");
		startStop.setEnabled(false);
		startStop.addActionListener(this);
		getContentPane().add(startStop, "cell 3 0");

		pause = new JButton("Pause");
		pause.setEnabled(false);
		pause.addActionListener(this);
		getContentPane().add(pause, "cell 4 0");

		if (playerFile.getText() != null && !"".equals(playerFile.getText())){
			File file = new File(playerFile.getText());
			if (file.canWrite()) startStop.setEnabled(true);
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getActionCommand().equals("Open")) {
			chooser = new JFileChooser();
			chooser.setSelectedFile(new File(playerPrefs.get("LastFile", "leaderboard-recording.txt")));

			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				playerFile.setText(chooser.getSelectedFile().toString());
				playerPrefs.put("LastFile", chooser.getSelectedFile().toString());
				startStop.setEnabled(true);
			}
		} else if (evt.getActionCommand().equals("Start")) {
			startStop.setText("Stop");
			pause.setEnabled(true);
			playerFile.setEnabled(false);
			selectFileButton.setEnabled(false);
			player = new Player(playerFile.getText());
			player.setPlayerSpeedup(Integer.parseInt(playerSpeedup.getText()));
			playerPrefs.put("Speedup", playerSpeedup.getText());
			player.execute();

		} else if (evt.getActionCommand().equals("Stop")) {
			player.close();
			startStop.setText("Start");
			pause.setEnabled(false);
			playerFile.setEnabled(true);
			selectFileButton.setEnabled(true);
			
		} else if (evt.getActionCommand().equals("Pause")) {
			pause.setText("Resume");
			player.pause();
				
		} else if (evt.getActionCommand().equals("Resume")) {
			pause.setText("Pause");
			player.resume();
		
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
