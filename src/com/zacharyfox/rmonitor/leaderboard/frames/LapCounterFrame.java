package com.zacharyfox.rmonitor.leaderboard.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.ini4j.IniPreferences;

import com.zacharyfox.rmonitor.entities.Race;
import com.zacharyfox.rmonitor.entities.Race.FlagState;
import com.zacharyfox.rmonitor.utils.Duration;

import javax.swing.JLabel;

public class LapCounterFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7957459184007408065L;

	private static LapCounterFrame instance;
	private final MainFrame mainFrame;
	
	static GraphicsDevice device = GraphicsEnvironment
	        .getLocalGraphicsEnvironment().getScreenDevices()[0];

	private final JPanel contentPanel = new JPanel();
	private JTextField tfLaps;

	private JButton cancelButton;
	private int lastLapCount;
	private Duration lastLapCountChangeTime;
	private int lapSwitchDelay;
	private Preferences lapCounterPrefs;
	
	private static final String TIME_FORMAT = "HH:mm:ss";
	public static final SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());

	private final PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			updateDisplay(evt);
		}
	};
	private JTextField tfElapsedTime;
	private JTextField tfFlag;
	private JPanel infoPanel;
	private JLabel lblElapsedTime;
	private JLabel lblDelay;
	private JTextField tfDelay;
	
	
	/**
	 * Create the dialog.
	 */
	public LapCounterFrame(final MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		lapCounterPrefs = new IniPreferences(mainFrame.getIni()).node("LapCounter");
		lapSwitchDelay = Integer.parseInt(lapCounterPrefs.get("LapSwitchDelay", "5"));
		
		mainFrame.storeIniFile();
		
		setBounds(100, 100, 1446, 840);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			tfLaps = new JTextField();
			Font theFont = new Font("Tahoma", Font.PLAIN, (int) (getHeight()*.85));//400
		    //System.out.println("Font Height of " + Integer.toString((int) (getHeight()*.8)) + " = " + Integer.toString( tfLaps.getFontMetrics(theFont).getHeight()));
			//System.out.println("Frame Height = " + Integer.toString( getHeight()));
			
			tfLaps.setFont(theFont);
			tfLaps.setEditable(false);
			tfLaps.setHorizontalAlignment(SwingConstants.CENTER);
			tfLaps.setForeground(Color.WHITE);
			tfLaps.setText("999");
			tfLaps.setBackground(Color.BLACK);
			contentPanel.add(tfLaps, BorderLayout.CENTER);
			tfLaps.setColumns(3);

		}
		
		contentPanel.addComponentListener(new resizeListener());
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.BLACK);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BorderLayout(0, 0));
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

				buttonPane.add(cancelButton, BorderLayout.EAST);
			}
			{
				tfFlag = new JTextField();
				tfFlag.setBackground(Color.BLACK);
				buttonPane.add(tfFlag, BorderLayout.CENTER);
				tfFlag.setColumns(30);
			}
			{
				infoPanel = new JPanel();
				infoPanel.setBackground(Color.BLACK);
				FlowLayout flowLayout = (FlowLayout) infoPanel.getLayout();
				flowLayout.setAlignment(FlowLayout.LEFT);
				buttonPane.add(infoPanel, BorderLayout.WEST);
				{
					lblElapsedTime = new JLabel("Elapsed Time:");
					lblElapsedTime.setForeground(Color.WHITE);
					infoPanel.add(lblElapsedTime);
				}
				{
					tfElapsedTime = new JTextField();
					infoPanel.add(tfElapsedTime);
					tfElapsedTime.setEditable(false);
					tfElapsedTime.setEnabled(false);
					tfElapsedTime.setColumns(8);
				}
				{
					lblDelay = new JLabel("Delay:");
					lblDelay.setForeground(Color.WHITE);
					infoPanel.add(lblDelay);
				}
				{
					tfDelay = new JTextField();
					infoPanel.add(tfDelay);
					tfDelay.setColumns(4);
					tfDelay.setText(Integer.toString(lapSwitchDelay));
					
					tfDelay.getDocument().addDocumentListener(new DocumentListener() {
				            @Override
				            public void insertUpdate(DocumentEvent e) {
				            	if (tfDelay.getText().matches("\\d+")){
				            		if (!Integer.toString(lapSwitchDelay).equals(tfDelay.getText())){
				            		
				            			lapSwitchDelay = Integer.parseInt(tfDelay.getText());
				            			lapCounterPrefs.put("LapSwitchDelay", tfDelay.getText());
				            			mainFrame.storeIniFile();
				            		}
				            	} 
				            }
				            @Override public void removeUpdate(DocumentEvent e) { } // do nothing
				            @Override public void changedUpdate(DocumentEvent e) { } // do nothing
			        });
				}
			}
	
		}
		
		
		lastLapCountChangeTime = new Duration();
		lastLapCount = 0;
		
		mainFrame.getRace().addPropertyChangeListener(propertyChangeListener);
		
	}

	
	class resizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            //Recalculate the variable you mentioned
        	Font theFont = new Font("Tahoma", Font.PLAIN, (int) (e.getComponent().getHeight()*.85));//400
		    //System.out.println("Font Height of " + Integer.toString((int) (getHeight()*.8)) + " = " + Integer.toString( tfLaps.getFontMetrics(theFont).getHeight()));
			//System.out.println("Size changed to " + Integer.toString( e.getComponent().getHeight()));
			
			tfLaps.setFont(theFont);
			//tfLaps.setText(Integer.toString((int) (e.getComponent().getHeight()*.85)));
        }
	}		
	
	public void addCancelButtonListener(ActionListener listener) {
		cancelButton.addActionListener(listener);
	  }

	
	public void updateDisplay(PropertyChangeEvent evt){
		
		if (evt.getPropertyName().equals("lapsToGo") ) {
			lastLapCount = ((int) evt.getNewValue());
			lastLapCountChangeTime = mainFrame.getRace().getElapsedTime();
		}
		
		if (evt.getPropertyName().equals("lapsToGo") || evt.getPropertyName().equals("elapsedTime")) {
			tfElapsedTime.setText(mainFrame.getRace().getElapsedTime().toString());
			int secondsSinceLastLapCountUpdate = mainFrame.getRace().getElapsedTime().toInt()-lastLapCountChangeTime.toInt();
			
			Race.FlagState currentFlagState = mainFrame.getRace().getCurrentFlagState();
			
			// For Purple the LapToGo are shown instantly
			if ( currentFlagState == Race.FlagState.PURPLE || currentFlagState == Race.FlagState.NONE){
				if (lastLapCount > 0){
					tfLaps.setText(Integer.toString(lastLapCount));
				} else {
					tfLaps.setText("-");
				}
			// for other flags we show the Laps to GO only after the lapSwitchDelay
			} else if (secondsSinceLastLapCountUpdate > lapSwitchDelay ){
				
				
				if (lastLapCount > 0){
					tfLaps.setText(Integer.toString(lastLapCount-1));
				} else {
					if (currentFlagState == Race.FlagState.NONE) {
						tfLaps.setText("-");
					} else {
						tfLaps.setText("0");
					}
				}
			}
		}
			
		if (evt.getPropertyName().equals("currentFlagState")){
			setFlagColor((FlagState) evt.getNewValue());
			tfElapsedTime.setText(mainFrame.getRace().getElapsedTime().toString());
			
			//After switch to green we have to trigger the decrease the lap counter
			if ((FlagState) evt.getNewValue() == Race.FlagState.GREEN){
				lastLapCountChangeTime = mainFrame.getRace().getElapsedTime();
			}
		}
			
				
	
		
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


	public static LapCounterFrame getInstance(MainFrame mainFrame)
	{
		if (instance == null) {
			instance = new LapCounterFrame(mainFrame);
		}

		return instance;
	}

	
}
