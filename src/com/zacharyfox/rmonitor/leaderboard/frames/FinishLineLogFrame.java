package com.zacharyfox.rmonitor.leaderboard.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

import com.zacharyfox.rmonitor.leaderboard.FinishLineLogTable;
import com.zacharyfox.rmonitor.leaderboard.FinishlineLogTableModel;
import com.zacharyfox.rmonitor.utils.Duration;

public class FinishLineLogFrame extends JFrame implements ActionListener
{
	private final JLabel elapsedTime;
	private final JLabel lblNewLabel_1;
	private final JLabel lblNewLabel_2;
	private final FinishLineLogTable finishLineLogTable;
	private final JPanel resultsTablePanel;
	private final JLabel runName;
	private final JSeparator separator;
	private final JPanel timeBar;
	private final JLabel timeToGo;
	private final JPanel titleBar;
	private final JLabel trackName;
	private MainFrame mainFrame;
	
	private final PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			updateDisplay(evt);
		}
	};

	private static final long serialVersionUID = -743830529485841322L;

	public FinishLineLogFrame(MainFrame mainFrame, int rowHeight)
	{
		this.mainFrame = mainFrame;

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Font systemLabelFont = UIManager.getFont("Label.font");
		this.setBounds(100, 100, 870, 430);
		this.getContentPane().setLayout(new MigLayout("", "[grow][grow]", "[][10:10:10][][][grow]"));

		titleBar = new JPanel();
		this.getContentPane().add(titleBar, "cell 0 0 2 1,grow");
		titleBar.setLayout(new GridLayout(1, 0, 0, 0));

		runName = new JLabel("-");
		runName.setFont(new Font(systemLabelFont.getName(), Font.BOLD, systemLabelFont.getSize() + 3));
		titleBar.add(runName);

		trackName = new JLabel("-");
		trackName.setFont(new Font(systemLabelFont.getName(), Font.BOLD, systemLabelFont.getSize() + 3));
		trackName.setHorizontalAlignment(SwingConstants.RIGHT);
		titleBar.add(trackName);

		separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBorder(null);
		getContentPane().add(separator, "cell 0 1 2 1,growx,aligny top");

		timeBar = new JPanel();
		getContentPane().add(timeBar, "cell 0 3 2 1,growx");
		timeBar.setLayout(new MigLayout("", "[][grow]50[][grow]", "[]"));

		lblNewLabel_1 = new JLabel("Elapsed:");
		lblNewLabel_1.setHorizontalTextPosition(JLabel.RIGHT);
		lblNewLabel_1.setHorizontalAlignment(JLabel.RIGHT);
		timeBar.add(lblNewLabel_1, "cell 1 0");

		lblNewLabel_2 = new JLabel("To Go:");
		lblNewLabel_2.setHorizontalAlignment(JLabel.RIGHT);
		timeBar.add(lblNewLabel_2, "cell 3 0");

		elapsedTime = new JLabel(new Duration().toString());
		elapsedTime.setHorizontalTextPosition(JLabel.LEFT);
		elapsedTime.setHorizontalAlignment(JLabel.LEFT);
		elapsedTime.setFont(new Font(systemLabelFont.getName(), Font.BOLD, systemLabelFont.getSize() + 3));
		timeBar.add(elapsedTime, "cell 2 0");

		timeToGo = new JLabel(new Duration().toString());
		timeToGo.setHorizontalTextPosition(JLabel.LEFT);
		timeToGo.setHorizontalAlignment(JLabel.LEFT);
		timeToGo.setFont(new Font(systemLabelFont.getName(), Font.BOLD, systemLabelFont.getSize() + 3));
		timeBar.add(timeToGo, "cell 4 0");

		resultsTablePanel = new JPanel();
		getContentPane().add(resultsTablePanel, "cell 0 4 2 1,grow");
//		resultsTablePanel.setLayout(new GridLayout(1, 0, 0, 0));
		resultsTablePanel.setLayout(new BorderLayout());
		finishLineLogTable = new FinishLineLogTable(rowHeight);
		finishLineLogTable.setIntercellSpacing(new Dimension(10, 1));
		finishLineLogTable.setFillsViewportHeight(true);
		finishLineLogTable.setShowVerticalLines(false);
		finishLineLogTable.setShowHorizontalLines(false);
		finishLineLogTable.setShowGrid(false);
		finishLineLogTable.setRowMargin(2);
		finishLineLogTable.setRowHeight(rowHeight);
		finishLineLogTable.setFont(new Font("Lucida Console", Font.BOLD, rowHeight));
		finishLineLogTable.setBackground(Color.BLACK);
		finishLineLogTable.setForeground(Color.YELLOW);
		finishLineLogTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		finishLineLogTable.setRowSelectionAllowed(false);
		finishLineLogTable.getTableHeader().setFont(new Font("Lucida Console", Font.BOLD, rowHeight));
		finishLineLogTable.getTableHeader().setOpaque(false);
		finishLineLogTable.getTableHeader().setBackground(Color.BLACK);
		finishLineLogTable.getTableHeader().setForeground(Color.YELLOW);
		resultsTablePanel.add(finishLineLogTable.getTableHeader(),BorderLayout.NORTH);
		resultsTablePanel.add(finishLineLogTable,BorderLayout.CENTER);
		
		mainFrame.getRace().addPropertyChangeListener(propertyChangeListener);

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("Connect")) {
/*			race = new Race();
			race.addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt)
				{
					updateDisplay(evt);
				}
			});
			ConnectFrame.getInstance(this).getIP();
			ConnectFrame.getInstance(this).getPort();
			storeIniFile();
			
			worker = new Worker(ConnectFrame.getInstance(this), race);
			
			if (recorder != null) {
				worker.setRecorder(recorder);
			}
			worker.execute();*/
		} else if (e.getActionCommand().equals("Disconnect")) {
			//worker.cancel(true);
		}

		return;
	}
	
	public void goFullScreen()
	{
		final Cursor oldCursor = getContentPane().getCursor();
		final Rectangle oldBounds = getBounds();
		final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

		if (gd.isFullScreenSupported()) {
			try {
				setCursor(getToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
					new Point(0, 0), "null"));
				dispose();
				setUndecorated(true);
				pack();
				setVisible(true);
				gd.setFullScreenWindow(this);

				InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
				final ActionMap actionMap = getRootPane().getActionMap();

				inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "escAction");
				actionMap.put("escAction", new AbstractAction() {
					private static final long serialVersionUID = -2399289576909037389L;

					@Override
					public void actionPerformed(ActionEvent evt)
					{
						actionMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true));
						FinishLineLogFrame.this.setCursor(oldCursor);
						gd.setFullScreenWindow(null);
						FinishLineLogFrame.this.dispose();
						FinishLineLogFrame.this.setUndecorated(false);
						FinishLineLogFrame.this.pack();
						FinishLineLogFrame.this.setBounds(oldBounds);
						FinishLineLogFrame.this.setVisible(true);
					}
				});
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			/*
			 * finally { gd.setFullScreenWindow(null); }
			 */
		} else {
			setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		}
	}


	
	private void updateDisplay(PropertyChangeEvent evt)
	{
		if (evt.getPropertyName().equals("raceName")) {
			runName.setText((String) evt.getNewValue());
		}

		if (evt.getPropertyName().equals("elapsedTime")) {
			elapsedTime.setText(((Duration) evt.getNewValue()).toString());
		}

		if (evt.getPropertyName().equals("timeToGo")) {
			timeToGo.setText(((Duration) evt.getNewValue()).toString());
		}

		if (evt.getPropertyName().equals("lapsToGo")) {
			timeToGo.setText(String.valueOf(((int) evt.getNewValue())));
		}

		
		if (evt.getPropertyName().equals("competitorsVersion")) {
			((FinishlineLogTableModel) finishLineLogTable.getModel()).updateData();
		}


		if (evt.getPropertyName().equals("trackName")) {
			trackName.setText(evt.getNewValue().toString());
		}

		if (evt.getPropertyName().equals("trackLength")) {
			// TODO: Handle Track Length
			// trackLength.setText(evt.getNewValue().toString());
		}
	}
	
	
	
}