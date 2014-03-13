package com.zacharyfox.rmonitor.leaderboard;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import com.zacharyfox.rmonitor.leaderboard.frames.AboutFrame;
import com.zacharyfox.rmonitor.leaderboard.frames.ConnectFrame;
import com.zacharyfox.rmonitor.leaderboard.frames.EstimatorFrame;
import com.zacharyfox.rmonitor.leaderboard.frames.MainFrame;
import com.zacharyfox.rmonitor.leaderboard.frames.PlayerFrame;
import com.zacharyfox.rmonitor.leaderboard.frames.RecorderFrame;

public class LeaderBoardMenuBar extends JMenuBar
{
	private final JMenuItem aboutMenuItem;
	private final JMenuItem connectMenuItem;
	private final JMenuItem estimatorMenuItem;
	private final JMenu fileMenu;
	private final JMenuItem fullScreenMenuItem;
	private final JMenu helpMenu;
	private final JMenuItem playerMenuItem;
	private final JMenuItem recorderMenuItem;
	private final JMenu toolsMenu;
	private final JMenu viewMenu;
	private static final long serialVersionUID = -4625321135214323710L;

	public LeaderBoardMenuBar(final MainFrame mainFrame)
	{
		setBackground(SystemColor.menu);
		setBorder(UIManager.getBorder("Menu.border"));

		fileMenu = new JMenu("File");
		add(fileMenu);

		connectMenuItem = new JMenuItem("Connection");
		connectMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				ConnectFrame newFrame = ConnectFrame.getInstance(mainFrame);
				newFrame.setVisible(true);
			}
		});

		fileMenu.add(connectMenuItem);

		viewMenu = new JMenu("View");
		add(viewMenu);

		fullScreenMenuItem = new JMenuItem("Full Screen");
		fullScreenMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainFrame.goFullScreen();
			}
		});
		viewMenu.add(fullScreenMenuItem);

		toolsMenu = new JMenu("Tools");
		add(toolsMenu);

		recorderMenuItem = new JMenuItem("Recorder");
		recorderMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				RecorderFrame newFrame = RecorderFrame.getInstance(mainFrame);
				newFrame.setVisible(true);
			}
		});

		toolsMenu.add(recorderMenuItem);

		playerMenuItem = new JMenuItem("Player");
		playerMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				PlayerFrame newFrame = PlayerFrame.getInstance(mainFrame);
				newFrame.setVisible(true);
			}
		});

		toolsMenu.add(playerMenuItem);

		estimatorMenuItem = new JMenuItem("Estimator");
		estimatorMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				EstimatorFrame newFrame = EstimatorFrame.getInstance(mainFrame);
				newFrame.setVisible(true);
			}
		});

		toolsMenu.add(estimatorMenuItem);

		helpMenu = new JMenu("Help");
		helpMenu.setBorder(UIManager.getBorder("MenuItem.border"));
		helpMenu.setBackground(SystemColor.menu);
		add(helpMenu);

		aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.setBorder(UIManager.getBorder("MenuItem.border"));
		aboutMenuItem.setBackground(SystemColor.menu);
		aboutMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				AboutFrame newFrame = new AboutFrame();
				newFrame.setVisible(true);
			}
		});

		helpMenu.add(aboutMenuItem);

		setVisible(true);
	}
}
