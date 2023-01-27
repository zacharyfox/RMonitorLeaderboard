package com.zacharyfox.rmonitor.leaderboard;

import java.awt.EventQueue;

import javax.swing.UIManager;

import com.zacharyfox.rmonitor.leaderboard.frames.ConnectFrame;
import com.zacharyfox.rmonitor.leaderboard.frames.MainFrame;

public class LeaderBoard
{
	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args)
	{
		try {
			System.setProperty("apple.awt.fullscreenhidecursor","true");
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "RMonitorLeaderboard");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run()
			{
				try {
				
					MainFrame window = new MainFrame("LeaderBoard.ini");
					window.setVisible(true);
					ConnectFrame newFrame = ConnectFrame.getInstance(window);
					newFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
