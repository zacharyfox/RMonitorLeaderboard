package com.zacharyfox.rmonitor.utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.zacharyfox.rmonitor.entities.Competitor;
import com.zacharyfox.rmonitor.entities.Race;

public class Estimator
{
	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	private int estimatedLapsAvg = 0;
	private int estimatedLapsBest = 0;
	private int estimatedLapsLast = 0;
	private Duration estimatedTimeAvg = new Duration(0);
	private Duration estimatedTimeBest = new Duration(0);
	private Duration estimatedTimeLast = new Duration(0);
	private int lapsComplete = 0;
	private int lapsToGo = 0;
	private int scheduledLaps = 0;

	private Duration scheduledTime = new Duration(0);

	public void addPropertyChangeListener(PropertyChangeListener l)
	{
		changeSupport.addPropertyChangeListener(l);
	}

	public void addPropertyChangeListener(String property, PropertyChangeListener l)
	{
		changeSupport.addPropertyChangeListener(property, l);
	}

	public int getEstimatedLapsAvg()
	{
		return this.estimatedLapsAvg;
	}

	public int getEstimatedLapsBest()
	{
		return this.estimatedLapsBest;
	}

	public Duration getEstimatedTimeAvg()
	{
		return estimatedTimeAvg;
	}

	public Duration getEstimatedTimeBest()
	{
		return estimatedTimeBest;
	}

	public int getLapsComplete()
	{
		return lapsComplete;
	}

	public int getScheduledLaps()
	{
		return this.scheduledLaps;
	}

	public Duration getScheduledTime()
	{
		return this.scheduledTime;
	}

	public void update(Race race)
	{
		this.setScheduledTime(race.getScheduledTime());
		this.setScheduledLaps(race.getScheduledLaps());
		this.setLapsComplete(race.getLapsComplete());
		this.setLapsToGo(race.getLapsToGo());
		this.setEstimatedLapsByBest();
		this.setEstimatedLapsByAvg();
	}

	private void setEstimatedLapsByAvg()
	{
		int oldEstimatedLapsAvg = this.estimatedLapsAvg;
		Duration oldEstimatedTimeAvg = this.estimatedTimeAvg;
		Competitor competitor = Competitor.getByPosition(1);

		if (competitor == null) {
			this.estimatedLapsAvg = lapsToGo;
			return;
		}

		int laps = competitor.getLapsComplete();

		if (laps == 0) {
			this.estimatedLapsAvg = lapsToGo;
			return;
		}

		int time = competitor.getTotalTime().toInt();
		int avgLapTime = competitor.getAvgLap().toInt();

		do {
			time += avgLapTime;
			laps += 1;
		} while (time < scheduledTime.toInt());

		if (lapsToGo == 0 || laps < lapsToGo + competitor.getLapsComplete()) {
			this.estimatedLapsAvg = laps;
		} else {
			this.estimatedLapsAvg = scheduledLaps;
		}

		this.estimatedTimeAvg = new Duration(time);

		changeSupport.firePropertyChange("estimatedLapsAvg", oldEstimatedLapsAvg,
			Integer.toString(this.estimatedLapsAvg));
		changeSupport.firePropertyChange("estimatedTimeAvg", oldEstimatedTimeAvg, estimatedTimeAvg);
	}

	private void setEstimatedLapsByBest()
	{
		int oldEstimatedLapsBest = this.estimatedLapsBest;
		Duration oldEstimatedTimeBest = this.estimatedTimeBest;
		Competitor competitor = Competitor.getByPosition(1);
		if (competitor == null) {
			this.estimatedLapsBest = lapsToGo;
			return;
		}

		int laps = competitor.getLapsComplete();
		if (laps == 0) {
			this.estimatedLapsBest = lapsToGo;
			return;
		}
		int time = competitor.getTotalTime().toInt();
		int bestLapTime = competitor.getBestLap().toInt();

		if (bestLapTime == 0) {
			this.estimatedLapsBest = lapsToGo;
			return;
		}

		do {
			time += bestLapTime;
			laps += 1;
		} while (time < scheduledTime.toInt());

		if (lapsToGo == 0 || laps < lapsToGo + competitor.getLapsComplete()) {
			this.estimatedLapsBest = laps;
		} else {
			this.estimatedLapsBest = scheduledLaps;
		}

		this.estimatedTimeBest = new Duration(time);

		changeSupport.firePropertyChange("estimatedLapsBest", oldEstimatedLapsBest,
			Integer.toString(this.estimatedLapsBest));
		changeSupport.firePropertyChange("estimatedTimeBest", oldEstimatedTimeBest, estimatedTimeBest);
	}

	private void setEstimatedLapsByLast()
	{
		int oldEstimatedLapsLast = this.estimatedLapsLast;
		Duration oldEstimatedTimeLast = this.estimatedTimeLast;
		Competitor competitor = Competitor.getByPosition(1);

		if (competitor == null) {
			this.estimatedLapsLast = lapsToGo;
			return;
		}

		int laps = competitor.getLapsComplete();

		if (laps == 0) {
			this.estimatedLapsLast = lapsToGo;
			return;
		}

		int time = competitor.getTotalTime().toInt();
		int avgLapTime = competitor.getLastLap().toInt();

		do {
			time += avgLapTime;
			laps += 1;
		} while (time < scheduledTime.toInt());

		if (lapsToGo == 0 || laps < lapsToGo + competitor.getLapsComplete()) {
			this.estimatedLapsLast = laps;
		} else {
			this.estimatedLapsLast = scheduledLaps;
		}

		this.estimatedTimeLast = new Duration(time);

		changeSupport.firePropertyChange("estimatedLapsLast", oldEstimatedLapsLast,
			Integer.toString(this.estimatedLapsLast));
		changeSupport.firePropertyChange("estimatedTimeLast", oldEstimatedTimeLast, estimatedTimeLast);
	}

	private void setLapsComplete(int lapsComplete)
	{
		int oldLapsComplete = this.lapsComplete;
		this.lapsComplete = lapsComplete;
		changeSupport.firePropertyChange("lapsComplete", oldLapsComplete, Integer.toString(lapsComplete));
	}

	private void setLapsToGo(int lapsToGo)
	{
		int oldLapsToGo = this.lapsToGo;
		this.lapsToGo = lapsToGo;
		changeSupport.firePropertyChange("lapsToGo", oldLapsToGo, Integer.toString(lapsToGo));
	}

	private void setScheduledLaps(int scheduledLaps)
	{
		int oldScheduledLaps = this.scheduledLaps;
		this.scheduledLaps = scheduledLaps;
		changeSupport.firePropertyChange("scheduledLaps", oldScheduledLaps, Integer.toString(scheduledLaps));
	}

	private void setScheduledTime(Duration scheduledTime)
	{
		Duration oldScheduledTime = this.scheduledTime;
		this.scheduledTime = scheduledTime;
		changeSupport.firePropertyChange("scheduledTime", oldScheduledTime, this.scheduledTime);
	}
}
