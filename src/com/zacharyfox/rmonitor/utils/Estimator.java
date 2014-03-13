package com.zacharyfox.rmonitor.utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.zacharyfox.rmonitor.entities.Race;

public class Estimator
{
	public int estimatedLaps;

	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener l)
	{
		changeSupport.addPropertyChangeListener(l);
	}

	public void addPropertyChangeListener(String property, PropertyChangeListener l)
	{
		changeSupport.addPropertyChangeListener(property, l);
	}

	public void update(Race race)
	{
		int oldEstimatedLaps = estimatedLaps;
		estimatedLaps = race.getEstimatedLaps();
		changeSupport.firePropertyChange("estimatedLaps", oldEstimatedLaps, Integer.toString(estimatedLaps));
	}
}
