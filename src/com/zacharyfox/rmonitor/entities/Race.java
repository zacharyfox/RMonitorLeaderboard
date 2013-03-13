package com.zacharyfox.rmonitor.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.zacharyfox.rmonitor.message.CompInfo;
import com.zacharyfox.rmonitor.message.Heartbeat;
import com.zacharyfox.rmonitor.message.RMonitorMessage;
import com.zacharyfox.rmonitor.message.RaceInfo;
import com.zacharyfox.rmonitor.message.RunInfo;
import com.zacharyfox.rmonitor.utils.Duration;

public class Race
{
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	private int competitorsVersion = 0;
	private Duration elapsedTime = new Duration(0);

	private String flagStatus = "";
	private int id = 0;

	private int lapsToGo = 0;

	private String name = "";
	private Duration scheduledTime = new Duration(0);
	private Duration timeOfDay = new Duration(0);

	private Duration timeToGo = new Duration(0);

	public void addPropertyChangeListener(PropertyChangeListener l)
	{
		changeSupport.addPropertyChangeListener(l);
	}

	public void addPropertyChangeListener(String property, PropertyChangeListener l)
	{
		changeSupport.addPropertyChangeListener(property, l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l)
	{
		changeSupport.removePropertyChangeListener(l);
	}

	public void removePropertyChangeListener(String property, PropertyChangeListener l)
	{
		changeSupport.removePropertyChangeListener(property, l);
	}

	@Override
	public String toString()
	{
		String string;

		// Race Info
		string = "Race Name: " + name + "\n";
		string += "Time to go: " + timeToGo.toString() + "\n";
		string += "Elapsed Time: " + elapsedTime + "\n";
		string += "Race Duration: " + scheduledTime + "\n";

		// Leader Info
		Competitor leader = Competitor.getByPosition(1);

		if (leader != null) {
			string += "Leader: " + leader.getRegNumber() + "\n";
			string += "Leader Laps: " + leader.getLaps() + "\n";
			string += "Leader Total Time: " + leader.getTotalTime() + "\n";
		}

		return string;
	}

	public void update(RMonitorMessage message)
	{
		if (message != null) {
			if (message.getClass() == Heartbeat.class) {
				this.messageUpdate((Heartbeat) message);
			}

			if (message.getClass() == RunInfo.class) {
				this.messageUpdate((RunInfo) message);
			}

			if (message.getClass() == RaceInfo.class || message.getClass() == CompInfo.class) {
				Competitor.updateOrCreate(message);
				setCompetitorsVersion();
			}
		}
	}

	private void messageUpdate(Heartbeat message)
	{
		setElapsedTime(message.getRaceTime());
		setLapsToGo(message.getLapsToGo());
		setTimeToGo(message.getTimeToGo());
		setScheduledTime(new Duration(elapsedTime.toFloat() + timeToGo.toFloat()));
		setTimeOfDay(message.getTimeOfDay());
		setFlagStatus(message.getFlagStatus());
	}

	private void messageUpdate(RunInfo message)
	{
		setName(message.getRaceName());
		setId(message.getUniqueId());
	}

	private void setCompetitorsVersion()
	{
		this.competitorsVersion = this.competitorsVersion + 1;
		changeSupport.firePropertyChange("competitorsVersion", this.competitorsVersion - 1, this.competitorsVersion);
	}

	private void setElapsedTime(Duration elapsedTime)
	{
		Duration oldElapsedTime = this.elapsedTime;
		this.elapsedTime = elapsedTime;
		changeSupport.firePropertyChange("elapsedTime", oldElapsedTime, this.elapsedTime);
	}

	private void setFlagStatus(String flagStatus)
	{
		String oldFlagStatus = this.flagStatus;
		this.flagStatus = flagStatus;
		changeSupport.firePropertyChange("flagStatus", oldFlagStatus, this.flagStatus);
	}

	private void setId(int id)
	{
		int oldId = this.id;
		this.id = id;
		changeSupport.firePropertyChange("id", oldId, this.id);
	}

	private void setLapsToGo(int lapsToGo)
	{
		int oldLapsToGo = this.lapsToGo;
		this.lapsToGo = lapsToGo;
		changeSupport.firePropertyChange("lapsToGo", oldLapsToGo, this.lapsToGo);
	}

	private void setName(String name)
	{
		String oldName = this.name;
		this.name = name;
		changeSupport.firePropertyChange("name", oldName, this.name);
	}

	private void setScheduledTime(Duration scheduledTime)
	{
		Duration oldScheduledTime = this.scheduledTime;
		this.scheduledTime = scheduledTime;
		changeSupport.firePropertyChange("scheduledTime", oldScheduledTime, this.scheduledTime);
	}

	private void setTimeOfDay(Duration timeOfDay)
	{
		Duration oldTimeOfDay = this.timeOfDay;
		this.timeOfDay = timeOfDay;
		changeSupport.firePropertyChange("timeOfDay", oldTimeOfDay, this.timeOfDay);
	}

	private void setTimeToGo(Duration timeToGo)
	{
		Duration oldTimeToGo = this.timeToGo;
		this.timeToGo = timeToGo;
		changeSupport.firePropertyChange("timeToGo", oldTimeToGo, this.timeToGo);
	}
}