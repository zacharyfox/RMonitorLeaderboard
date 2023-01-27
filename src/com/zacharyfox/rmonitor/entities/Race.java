package com.zacharyfox.rmonitor.entities;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

import com.zacharyfox.rmonitor.message.ClassInfo;
import com.zacharyfox.rmonitor.message.CompInfo;
import com.zacharyfox.rmonitor.message.Heartbeat;
import com.zacharyfox.rmonitor.message.InitRecord;
import com.zacharyfox.rmonitor.message.LapInfo;
import com.zacharyfox.rmonitor.message.PassingInfo;
import com.zacharyfox.rmonitor.message.QualInfo;
import com.zacharyfox.rmonitor.message.RMonitorMessage;
import com.zacharyfox.rmonitor.message.RaceInfo;
import com.zacharyfox.rmonitor.message.RunInfo;
import com.zacharyfox.rmonitor.message.SettingInfo;
import com.zacharyfox.rmonitor.utils.Duration;

public class Race
{
	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	private int competitorsVersion = 0;
	private Duration elapsedTime = new Duration();
	private String flagStatus = "";
	public enum FlagState {PURPLE, GREEN, YELLOW, RED, FINISH, NONE};
	private FlagState currentFlagState; 
	private int id = 0;
	private int lapsComplete = 0;
	private int lapsToGo = 0;
	private String name = "";
	private Duration scheduledTime = new Duration();
	private Duration timeOfDay = new Duration();
	private Duration timeToGo = new Duration();
	private Float trackLength = (float) 0.0;
	private String trackName = "";
	
	private static HashMap<Integer, RaceTO> allRaces = new HashMap<Integer, RaceTO>();

	public void addPropertyChangeListener(PropertyChangeListener l)
	{
		changeSupport.addPropertyChangeListener(l);
	}

	public void addPropertyChangeListener(String property, PropertyChangeListener l)
	{
		changeSupport.addPropertyChangeListener(property, l);
	}

	public int getLapsComplete()
	{
		return lapsComplete;
	}

	public int getLapsToGo()
	{
		return lapsToGo;
	}

	public int getScheduledLaps()
	{
		return lapsComplete + lapsToGo;
	}

	public Duration getScheduledTime()
	{
		return scheduledTime;
	}

	public Float getTrackLength()
	{
		return trackLength;
	}

	public String getTrackName()
	{
		return trackName;
	}

	public String getRaceName()
	{
		return name;
	}

	/**
	 * @return the elapsedTime
	 */
	public synchronized Duration getElapsedTime() {
		return elapsedTime;
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
			string += "Leader Laps: " + leader.getLapsComplete() + "\n";
			string += "Leader Total Time: " + leader.getTotalTime() + "\n";
		}

		return string;
	}

	public void update(RMonitorMessage message)
	{
		if (message != null) {
			if (message.getClass() == Heartbeat.class) {
				this.messageUpdate((Heartbeat) message);
			} else if (message.getClass() == RunInfo.class) {
				this.messageUpdate((RunInfo) message);
			} else if (message.getClass() == SettingInfo.class) {
				this.messageUpdate((SettingInfo) message);
			} else if (message.getClass() == RaceInfo.class) {
				this.messageUpdate((RaceInfo) message);
				Competitor.updateOrCreate(message);
				setCompetitorsVersion();
			} else if (message.getClass() == CompInfo.class) {
				Competitor.updateOrCreate(message);
				setCompetitorsVersion();
			} else if (message.getClass() == LapInfo.class) {
				Competitor.updateOrCreate(message);
				setCompetitorsVersion();
			} else if (message.getClass() == QualInfo.class) {
				Competitor.updateOrCreate(message);
				setCompetitorsVersion();
			} else if (message.getClass() == ClassInfo.class) {
				RaceClass.update((ClassInfo) message);
			} else if (message.getClass() == PassingInfo.class) {
				Competitor.updateOrCreate(message);
			} else if (message.getClass() == InitRecord.class) {
				this.messageUpdate((InitRecord) message);
			} else {
				System.out.println("Message not processed by Race: " + message);
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

	private void messageUpdate(RaceInfo message)
	{
		if (message.getPosition() == 1) {
			setLapsComplete(message.getLaps());
		}
	}

	private void messageUpdate(RunInfo message)
	{
		//System.out.println("RunInfo: Current Race " + raceName + " new Race " + message.getRaceName());
		if (id != message.getUniqueId() && name != message.getRaceName()) {
			competitorsVersion = 0;
			elapsedTime = new Duration();
			id = 0;
			lapsComplete = 0;
			lapsToGo = 0;
			name = "";
			scheduledTime = new Duration();
			timeOfDay = new Duration();
			timeToGo = new Duration();
			trackLength = (float) 0.0;
			trackName = "";

			Competitor.reset();
		}

		setName(message.getRaceName());
		setId(message.getUniqueId());
		setFlagStatus("");
	}

	private void messageUpdate(InitRecord message)
	{
		// before initializing the race we FINISH the last one and store the status of the old race
		setFlagStatus("FINISH");
		this.getRaceTO(); // we fetch the TO so that the current race gets stored
		
		setElapsedTime(new Duration());
		setLapsToGo(0);
		setTimeToGo(new Duration());
		setScheduledTime(new Duration());
		setTimeOfDay(new Duration());
		Competitor.reset();
		competitorsVersion = 0;
		setCompetitorsVersion();
		setLapsComplete(0);
		setTrackLength((float) 0.0);

		setTrackName("");
		setName("");
		setId(0);
		setFlagStatus("");
		
	}
	private void messageUpdate(SettingInfo message)
	{
		if (message.getDescription().equals("TRACKNAME")) {
			setTrackName(message.getValue());
		}

		if (message.getDescription().equals("TRACKLENGTH")) {
			setTrackLength(Float.parseFloat(message.getValue()));
		}
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
		FlagState oldCurrentFlagState = this.currentFlagState;
		
		this.flagStatus = flagStatus;
		
		switch (flagStatus.toUpperCase()){
		case "RED":
			currentFlagState = FlagState.RED;
			break;
		case "YELLOW":
			currentFlagState = FlagState.YELLOW;
			break;
		case "GREEN":
			currentFlagState = FlagState.GREEN;
			break;
		case "FINISH":
			currentFlagState = FlagState.FINISH;
			break;
		default:
			// If Race Name is empty and raceID is 0 we have no active Race. 
			if ("".equals(this.name) && this.id == 0 ) {
				currentFlagState= FlagState.NONE;
			} else {
				currentFlagState = FlagState.PURPLE;
			}
		}
		
		changeSupport.firePropertyChange("flagStatus", oldFlagStatus, this.flagStatus);
		changeSupport.firePropertyChange("currentFlagState", oldCurrentFlagState, this.currentFlagState);
	}

	private void setId(int id)
	{
		int oldId = this.id;
		this.id = id;
		changeSupport.firePropertyChange("raceID", oldId, this.id);
	}

	private void setLapsComplete(int lapsComplete)
	{
		int oldLapsComplete = this.lapsComplete;
		this.lapsComplete = lapsComplete;
		changeSupport.firePropertyChange("lapsComplete", oldLapsComplete, this.lapsComplete);
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
		changeSupport.firePropertyChange("raceName", oldName, this.name);
	}

	/**
	 * @return the flagStatus
	 */
	public synchronized String getFlagStatus() {
		return flagStatus;
	}

	/**
	 * @return the currentFlagState
	 */
	public synchronized FlagState getCurrentFlagState() {
		return currentFlagState;
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

	private void setTrackLength(Float trackLength)
	{
		Float oldTrackLength = this.trackLength;
		this.trackLength = trackLength;
		changeSupport.firePropertyChange("trackLength", oldTrackLength, this.trackLength);
	}

	private void setTrackName(String trackName)
	{
		String oldTrackName = this.trackName;
		this.trackName = trackName;
		changeSupport.firePropertyChange("trackName", oldTrackName, this.trackName);
	}
	
	
	public RaceTO getRaceTO(){
		RaceTO raceTO = new RaceTO(elapsedTime.toString(), currentFlagState.toString(), id, lapsToGo, lapsComplete,name,timeOfDay.toString(),trackName);
		Competitor.setCompetitorTO(raceTO);
		
		if (id != 0) {
			allRaces.put(id, raceTO);
		}
		return raceTO;
	}
	
	public static RaceTO getToByID(int raceID)
	{
		Integer key = new Integer(raceID);
		if (allRaces.containsKey(key)) {
			return allRaces.get(key);
		}
		return null;
	}
	
	public static RaceTO[] getAllRaceTOs(){
		return (RaceTO[]) allRaces.values().toArray(new RaceTO[0]);
	}
	
}


