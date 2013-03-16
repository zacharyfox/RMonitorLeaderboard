package com.zacharyfox.rmonitor.leaderboard.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.zacharyfox.rmonitor.utils.Recorder;

public class RecorderFrame extends JFrame implements ActionListener
{
	private JFileChooser chooser;
	private final MainFrame mainFrame;
	private Recorder recorder;
	private final JTextField recorderFile;
	private final JButton selectFileButton;
	private final JButton startStop;
	private static RecorderFrame instance;
	private static final long serialVersionUID = -9179041103033981780L;

	private RecorderFrame(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;

		getContentPane().setLayout(new MigLayout("", "[grow][][]", "[][]"));
		setBounds(100, 100, 400, 150);

		recorderFile = new JTextField();
		getContentPane().add(recorderFile, "cell 0 0,growx");
		recorderFile.setColumns(10);

		selectFileButton = new JButton("Save As");
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
		if (evt.getActionCommand().equals("Save As")) {
			chooser = new JFileChooser();
			chooser.setSelectedFile(new File("leaderboard-recording.txt"));

			if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				recorderFile.setText(chooser.getSelectedFile().toString());
				startStop.setEnabled(true);
			}
		} else if (evt.getActionCommand().equals("Start")) {
			startStop.setText("Stop");
			recorderFile.setEnabled(false);
			selectFileButton.setEnabled(false);
			recorder = new Recorder(recorderFile.getText());
			mainFrame.setRecorder(recorder);

		} else if (evt.getActionCommand().equals("Stop")) {
			mainFrame.removeRecorder();
			recorder.close();
			startStop.setText("Start");
			recorderFile.setEnabled(true);
			selectFileButton.setEnabled(true);

		}
	}

	public static RecorderFrame getInstance(MainFrame mainFrame)
	{
		if (instance == null) {
			instance = new RecorderFrame(mainFrame);
		}

		return instance;
	}
}
