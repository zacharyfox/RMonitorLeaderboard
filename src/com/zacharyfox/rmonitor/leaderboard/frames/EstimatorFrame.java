package com.zacharyfox.rmonitor.leaderboard.frames;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

import com.zacharyfox.rmonitor.utils.Estimator;

public class EstimatorFrame extends JFrame
{
	private final JLabel estimatedLaps;
	private final Estimator estimator;
	private final JLabel lblNewLabel;
	private final MainFrame mainFrame;
	private static EstimatorFrame instance;
	private static final long serialVersionUID = 207100859328952666L;

	private EstimatorFrame(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;

		getContentPane().setLayout(new MigLayout("", "[grow][]", "[100.00,grow][]"));

		lblNewLabel = new JLabel("Estimated Laps:");
		getContentPane().add(lblNewLabel, "cell 0 0");

		estimatedLaps = new JLabel("");
		getContentPane().add(estimatedLaps, "cell 1 0");
		setBounds(100, 100, 450, 175);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		estimator = new Estimator();
		estimator.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				updateDisplay(evt);
			}
		});

		this.mainFrame.setEstimator(estimator);
	}

	public JLabel getEstimatedLaps()
	{
		return estimatedLaps;
	}

	public void setEstimatedLaps(int laps)
	{
		estimatedLaps.setText(Integer.toString(laps));
	}

	private void updateDisplay(PropertyChangeEvent evt)
	{
		if (evt.getPropertyName().equals("estimatedLaps")) {
			estimatedLaps.setText((String) evt.getNewValue());
		}
	}

	public static EstimatorFrame getInstance(MainFrame mainFrame)
	{
		if (instance == null) {
			instance = new EstimatorFrame(mainFrame);
		}

		return instance;
	}
}
