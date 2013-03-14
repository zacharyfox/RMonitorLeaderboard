package com.zacharyfox.rmonitor.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

import com.zacharyfox.rmonitor.message.CompInfo;
import com.zacharyfox.rmonitor.message.LapInfo;
import com.zacharyfox.rmonitor.message.RMonitorMessage;
import com.zacharyfox.rmonitor.message.RaceInfo;
import com.zacharyfox.rmonitor.utils.Duration;

public class Competitor
{
	private String addData = "";
	private Duration bestLap = new Duration(0);
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	private int classId = 0;
	private String firstName = "";
	private HashMap<Integer, Object[]> laps = new HashMap<Integer, Object[]>();
	private int lapsComplete = 0;
	private Duration lastLap = new Duration(0);
	private String lastName = "";
	private String nationality = "";
	private String number = "";
	private int position = 0;
	private String regNumber = "";
	private Duration totalTime = new Duration(0);
	private int transNumber = 0;
	private static HashMap<String, Competitor> instances = new HashMap<String, Competitor>();

	private Competitor()
	{

	}

	public void addPropertyChangeListener(PropertyChangeListener l)
	{
		changeSupport.addPropertyChangeListener(l);
	}

	public void addPropertyChangeListener(String property, PropertyChangeListener l)
	{
		changeSupport.addPropertyChangeListener(property, l);
	}

	public String getAddData()
	{
		return addData;
	}

	public Object getAvgLap()
	{
		Float avgLap = (Float) (totalTime.toFloat() / lapsComplete);
		System.out.println(avgLap);
		return new Duration((Float) (totalTime.toFloat() / lapsComplete));
	}

	public Duration getBestLap()
	{
		return bestLap;
	}

	public int getClassId()
	{
		return classId;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public HashMap<Integer, Object[]> getLaps()
	{
		return laps;
	}

	public int getLapsComplete()
	{
		return lapsComplete;
	}

	public Duration getLastLap()
	{
		return lastLap;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getNationality()
	{
		return nationality;
	}

	public String getNumber()
	{
		return number;
	}

	public int getPosition()
	{
		return position;
	}

	public String getRegNumber()
	{
		return regNumber;
	}

	public Duration getTotalTime()
	{
		return totalTime;
	}

	public int getTransNumber()
	{
		return transNumber;
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

		string = "First Name: " + firstName + "\n";
		string += "Last Name: " + lastName + "\n";
		string += "Registration Number: " + regNumber + "\n";
		string += "Number: " + number + "\n";
		string += "Transponder Number: " + transNumber + "\n";
		string += "Class ID: " + classId + "\n";
		string += "Position: " + position + "\n";
		string += "Laps: " + lapsComplete + "\n";
		string += "Total Time: " + totalTime + "\n";
		string += "Best Time: " + bestLap + "\n";

		string += "Laps: \n";

		for (Object[] lap : laps.values()) {
			string += lap[0] + " " + lap[1] + " " + lap[2] + "\n";
		}

		return string;
	}

	private void messageUpdate(CompInfo message)
	{
		this.setRegNumber(message.getRegNumber());
		this.setNumber(message.getNumber());
		this.setTransNumber(message.getTransNumber());
		this.setFirstName(message.getFirstName());
		this.setLastName(message.getLastName());
		this.setClassId(message.getClassId());

		if (!"".equals(message.getNationality()))
			this.setNationality(message.getNationality());
		if (!"".equals(message.getAddInfo()))
			this.setAddData(message.getAddInfo());
	}

	private void messageUpdate(LapInfo message)
	{
		// System.out.println("Got Lap Info " + this.regNumber + " " +
		// message.getLapNumber());
		this.laps.put(message.getLapNumber(), new Object[] {
			message.getLapNumber(), message.getPosition(), message.getLapTime()
		});
		
		if (message.getLapNumber() == lapsComplete) {
			setLastLap(message.getLapTime());
		}

		setBestLap(message.getLapTime());

		System.out.println(this);
	}

	private void messageUpdate(RaceInfo message)
	{
		this.setRegNumber(message.getRegNumber());
		this.setPosition(message.getPosition());
		this.setLapsComplete(message.getLaps());
		this.setTotalTime(message.getTotalTime());
	}

	private void setAddData(String addData)
	{
		changeSupport.firePropertyChange("addData", this.addData, addData);
		this.addData = addData;
	}

	private void setBestLap(Duration bestLap)
	{
		if (this.bestLap.toInt() == 0 || bestLap.toFloat() < this.bestLap.toFloat()) {
			Duration oldBestLap = this.bestLap;
			this.bestLap = bestLap;
			changeSupport.firePropertyChange("bestLap", oldBestLap, this.bestLap);
		}
	}

	private void setClassId(int classId)
	{
		changeSupport.firePropertyChange("classId", this.classId, classId);
		this.classId = classId;
	}

	private void setFirstName(String firstName)
	{
		String oldName = this.firstName;
		this.firstName = firstName;
		changeSupport.firePropertyChange("firstName", oldName, firstName);
	}

	private void setLapsComplete(int lapsComplete)
	{
		int oldLapsComplete = this.lapsComplete;
		this.lapsComplete = lapsComplete;
		changeSupport.firePropertyChange("laps", oldLapsComplete, this.lapsComplete);
	}

	private void setLastLap(Duration lastLap)
	{
		Duration oldLastLap = this.lastLap;
		this.lastLap = lastLap;
		changeSupport.firePropertyChange("lastLap", oldLastLap, this.lastLap);
	}

	private void setLastName(String lastName)
	{
		changeSupport.firePropertyChange("lastName", this.lastName, lastName);
		this.lastName = lastName;
	}

	private void setNationality(String nationality)
	{
		changeSupport.firePropertyChange("nationality", this.nationality, nationality);
		this.nationality = nationality;
	}

	private void setNumber(String string)
	{
		changeSupport.firePropertyChange("number", this.number, string);
		this.number = string;
	}

	private void setPosition(int position)
	{
		changeSupport.firePropertyChange("position", this.position, position);
		this.position = position;
	}

	private void setRegNumber(String regNumber)
	{
		changeSupport.firePropertyChange("regNumber", this.regNumber, regNumber);
		this.regNumber = regNumber;
	}

	private void setTotalTime(Duration totalTime)
	{
		Duration oldTotalTime = this.totalTime;
		this.totalTime = totalTime;
		changeSupport.firePropertyChange("totalTime", oldTotalTime, this.totalTime);
	}

	private void setTransNumber(int transNumber)
	{
		changeSupport.firePropertyChange("transNumber", this.transNumber, transNumber);
		this.transNumber = transNumber;
	}

	public static Competitor getByPosition(int position)
	{
		for (Competitor competitor : instances.values()) {
			if (competitor.position == position) {
				return competitor;
			}
		}
		return null;
	}

	public static Competitor getInstance(String regNumber)
	{
		if (instances.containsKey(regNumber)) {
			return instances.get(regNumber);
		}
		return null;
	}

	public static HashMap<String, Competitor> getInstances()
	{
		return instances;
	}

	public static void updateOrCreate(RMonitorMessage message)
	{
		Competitor instance = Competitor.getInstance(message.getRegNumber());

		instance = (instance == null) ? new Competitor() : instance;

		if (message.getClass() == RaceInfo.class) {
			instance.messageUpdate((RaceInfo) message);
		}

		if (message.getClass() == CompInfo.class) {
			instance.messageUpdate((CompInfo) message);
		}

		if (message.getClass() == LapInfo.class) {
			instance.messageUpdate((LapInfo) message);
		}

		instances.put(instance.getRegNumber(), instance);
	}
}
