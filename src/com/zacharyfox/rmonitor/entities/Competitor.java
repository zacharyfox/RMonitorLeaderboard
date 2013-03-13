package com.zacharyfox.rmonitor.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

import com.zacharyfox.rmonitor.message.CompInfo;
import com.zacharyfox.rmonitor.message.RMonitorMessage;
import com.zacharyfox.rmonitor.message.RaceInfo;
import com.zacharyfox.rmonitor.utils.Duration;

public class Competitor
{
	private String addData = "";
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	private int classId = 0;
	private String firstName = "";
	private int laps = 0;
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

	public int getClassId()
	{
		return classId;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public int getLaps()
	{
		return laps;
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

	public void setLaps(int laps)
	{
		changeSupport.firePropertyChange("laps", this.laps, laps);
		this.laps = laps;
	}

	public void setTotalTime(Duration totalTime)
	{
		changeSupport.firePropertyChange("totalTime", this.totalTime, totalTime);
		this.totalTime = totalTime;
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
		string += "Laps: " + laps + "\n";
		string += "Total Time: " + totalTime + "\n";

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

	private void messageUpdate(RaceInfo message)
	{
		this.setRegNumber(message.getRegNumber());
		this.setPosition(message.getPosition());
		this.setLaps(message.getLaps());
		this.setTotalTime(message.getTotalTime());
	}

	private void setAddData(String addData)
	{
		changeSupport.firePropertyChange("addData", this.addData, addData);
		this.addData = addData;
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

		instances.put(instance.getRegNumber(), instance);
	}
}
