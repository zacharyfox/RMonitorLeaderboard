package com.zacharyfox.rmonitor.leaderboard.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

public class AboutFrame extends JFrame
{
	private JTextPane aboutText;
	private JButton okButton;
	private static final long serialVersionUID = -2464354886129453047L;

	public AboutFrame()
	{
		getContentPane().setLayout(new MigLayout("", "[grow][]", "[100.00,grow][]"));
		setBounds(100, 100, 450, 175);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		aboutText = new JTextPane();
		aboutText.setBorder(null);
		aboutText.setBackground(null);
		aboutText.setContentType("text/html");
		aboutText
			.setText("<strong>RMonitorLeaderboard</strong>\n\n<p>copyright &copy; 2013 Zachary Fox</p>\n\n<p>Project Located at: <a href=\"https://github.com/zacharyfox/RMonitorLeaderboard\">https://github.com/zacharyfox/RMonitorLeaderboard</a></p>");
		getContentPane().add(aboutText, "cell 0 0 2 1,grow");

		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				setVisible(false); // you can't see me!
				dispose();
			}
		});

		getContentPane().add(okButton, "cell 1 1");
	}
}
