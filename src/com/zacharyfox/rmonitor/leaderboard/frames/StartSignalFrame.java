package com.zacharyfox.rmonitor.leaderboard.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.zacharyfox.rmonitor.entities.Competitor;
import com.zacharyfox.rmonitor.entities.Race;
import com.zacharyfox.rmonitor.entities.Race.FlagState;
import com.zacharyfox.rmonitor.utils.Duration;



public class StartSignalFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7957459184007408065L;
	
	private static StartSignalFrame instance;
	private final MainFrame mainFrame;
		
	static GraphicsDevice device = GraphicsEnvironment
	        .getLocalGraphicsEnvironment().getScreenDevices()[0];

	private final JPanel contentPanel = new JPanel();
	private JTextField tfRaceName;
	private JTextArea tfFlag;
	private JTextField tfRaceTime;

	private JButton cancelButton;
	private final PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			updateDisplay(evt);
		}
	};

	/**
	 * Create the dialog.
	 */
	public StartSignalFrame(final MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		setBounds(100, 100, 698, 590);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			tfRaceName = new JTextField();
			tfRaceName.setHorizontalAlignment(SwingConstants.CENTER);
			tfRaceName.setForeground(Color.WHITE);
			tfRaceName.setBackground(Color.BLACK);
				
			tfRaceName.setFont(new Font("Tahoma", Font.PLAIN, 80));
			contentPanel.add(tfRaceName, BorderLayout.NORTH);
			if (mainFrame.getRace() != null){
				tfRaceName.setText(mainFrame.getRace().getRaceName());
			} else {
				tfRaceName.setText("");
			}

			tfRaceName.setColumns(50);
		}
		{
			tfFlag = new JTextArea();
			tfFlag.setEditable(false);
			tfFlag.setWrapStyleWord(true);
			tfFlag.setLineWrap(true);
			tfFlag.setForeground(Color.WHITE);
			tfFlag.setFont(new Font("Tahoma", Font.PLAIN, 80));
			tfFlag.setRows(5);
			tfFlag.setBackground(Color.BLACK);
			contentPanel.add(tfFlag, BorderLayout.CENTER);
			
			tfFlag.setColumns(20);
			
			if (mainFrame.getRace() != null){
				setFlagColor(mainFrame.getRace().getCurrentFlagState());
			}
		}
		{
			tfRaceTime = new JTextField();
			tfRaceTime.setText("00:00:00");
			tfRaceTime.setHorizontalAlignment(SwingConstants.CENTER);
			tfRaceTime.setBackground(Color.BLACK);
			tfRaceTime.setForeground(Color.WHITE);
			tfRaceTime.setFont(new Font("Tahoma", Font.PLAIN, 100));
			contentPanel.add(tfRaceTime, BorderLayout.SOUTH);
			tfRaceTime.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.BLACK);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				cancelButton = new JButton("X");
				cancelButton.setFont(new Font("Tahoma", Font.BOLD, 11));
				cancelButton.setMnemonic('x');
				cancelButton.setBackground(Color.BLACK);
				cancelButton.setForeground(Color.RED);
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener (){

					@Override
					public void actionPerformed(ActionEvent evt) {
						instance.setVisible(false);
						mainFrame.getRace().removePropertyChangeListener(propertyChangeListener);
						instance.dispose();
						instance = null;
						
					}
					
				});
				
				buttonPane.add(cancelButton);
			}
		}
		
		mainFrame.getRace().addPropertyChangeListener(propertyChangeListener);
		
	}

	
	public void addCancelButtonListener(ActionListener listener) {
		cancelButton.addActionListener(listener);
	}

	
	
	private void updateDisplay(PropertyChangeEvent evt)
	{
		if (evt.getPropertyName().equals("raceName")) {
			tfRaceName.setText((String) evt.getNewValue());
		}

		if (evt.getPropertyName().equals("elapsedTime")) {
			tfRaceTime.setText(((Duration) evt.getNewValue()).toString());
		}
		
		if (evt.getPropertyName().equals("currentFlagState")) {
			setFlagColor((FlagState) evt.getNewValue());
			tfFlag.setText("");
		}
		
		if (evt.getPropertyName().equals("competitorsVersion")) {
			if (mainFrame.getRace().getCurrentFlagState() == Race.FlagState.PURPLE) {
				tfFlag.setText(getCompetitorsString(Competitor.getInstances().values()) );
			}
		}


	}
	
	
	private String getCompetitorsString(Collection<Competitor> competitors){
		String result = "";
		for (Competitor competitor : competitors) {
			result = result + competitor.getRegNumber() + ", ";
		}
		if (!"".equals(result)){
			result = result.substring(0, result.length()-3);
		}
		return result;
	}
	
	private void setFlagColor(Race.FlagState flagState){
		switch (flagState){
		case RED:
			tfFlag.setBackground(Color.red);
			break;
		case YELLOW:
			tfFlag.setBackground(Color.YELLOW);
			break;
		case GREEN:
			tfFlag.setBackground(Color.GREEN);
			break;
		case FINISH:
			tfFlag.setBackground(Color.LIGHT_GRAY);
			break;
		case PURPLE:
			tfFlag.setBackground(new Color(98,0,255));
			break;
		default:
			tfFlag.setBackground(Color.BLACK);
		}

	}
	
	public static StartSignalFrame getInstance(MainFrame mainFrame)
	{
		if (instance == null) {
			instance = new StartSignalFrame(mainFrame);
		}

		return instance;
	}
	
}
