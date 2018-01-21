package com.zacharyfox.rmonitor.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;

import com.zacharyfox.rmonitor.message.CompInfo;
import com.zacharyfox.rmonitor.message.LapInfo;
import com.zacharyfox.rmonitor.message.PassingInfo;
import com.zacharyfox.rmonitor.message.QualInfo;
import com.zacharyfox.rmonitor.message.RMonitorMessage;
import com.zacharyfox.rmonitor.message.RaceInfo;
import com.zacharyfox.rmonitor.utils.Duration;

public class Competitor
{
	public class Lap
	{
		public int lapNumber;
		public Duration lapTime;
		public int position;
		public Duration totalTime;
	}

	private String addData = "";
	private Duration bestLap = new Duration();
	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	private int classId = 0;
	private String firstName = "";
	private final ArrayList<Competitor.Lap> laps = new ArrayList<Competitor.Lap>();
	private int lapsComplete = 0;
	private Duration lastLap = new Duration();
	private String lastName = "";
	private String nationality = "";
	private String number = "";
	private int position = 0;
	private String regNumber = "";
	private Duration totalTime = new Duration();
	private String transNumber = "";
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

	public Duration getAvgLap()
	{
		float totalTime = 0;
		int count = 0;

		if (laps.size() > 0) {
			for (Competitor.Lap lap : laps) {
				if (lap.lapTime != null && lap.lapNumber > 0) {
					totalTime = totalTime + lap.lapTime.toFloat();
					count++;
				}
			}

			if (count > 0) {
				return new Duration(totalTime / count);
			}
		}
		return new Duration();
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

	public ArrayList<Competitor.Lap> getLaps()
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

	public int getPositionInClass()
	{
		int positionInClass = 1;

		for (Competitor competitor : instances.values()) {
			if (competitor.classId == classId && competitor.position < position) {
				positionInClass += 1;
			}
		}

		return positionInClass;
	}

	public String getRegNumber()
	{
		return regNumber;
	}

	public Duration getTotalTime()
	{
		return totalTime;
	}

	public String getTransNumber()
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

		for (Competitor.Lap lap : laps) {
			string += lap.lapNumber + " " + lap.position + " " + lap.lapTime + " " + lap.totalTime + "\n";
		}

		return string;
	}

	private void addLap(LapInfo message)
	{
		Boolean found = false;

		for (Competitor.Lap lap : this.laps) {
			if (lap.lapNumber == message.getLapNumber()) {
				found = true;
				lap.lapTime = message.getLapTime();
				lap.position = message.getPosition();
			}
		}

		if (found == false) {
			Competitor.Lap lap = new Competitor.Lap();
			lap.lapNumber = message.getLapNumber();
			lap.position = message.getPosition();
			lap.lapTime = message.getLapTime();

			this.laps.add(lap);
		}
	}

	private void addLap(PassingInfo message)
	{
		Boolean found = false;

		for (Competitor.Lap lap : this.laps) {
			if (lap != null && lap.totalTime != null && lap.totalTime.equals(message.getTotalTime())) {
				found = true;
				lap.lapTime = message.getLapTime();
			}
		}

		if (found == false) {
			Competitor.Lap lap = new Competitor.Lap();
			lap.lapTime = message.getLapTime();
			lap.totalTime = message.getTotalTime();
			this.laps.add(lap);
		}
	}

	private void addLap(RaceInfo message)
	{
		Boolean found = false;
		for (Competitor.Lap lap : this.laps) {
			if (lap != null && lap.totalTime != null && lap.totalTime.equals(message.getTotalTime())) {
				found = true;
				lap.totalTime = message.getTotalTime();
				lap.lapNumber = message.getLaps();
				lap.position = message.getPosition();
			}
		}

		if (found == false) {
			Competitor.Lap lap = new Competitor.Lap();
			lap.totalTime = message.getTotalTime();
			lap.lapNumber = message.getLaps();
			lap.position = message.getPosition();
			this.laps.add(lap);
		}
	}

	private void messageUpdate(CompInfo message)
	{
		this.setRegNumber(message.getRegNumber());
		this.setNumber(message.getNumber());
		this.setTransNumber(message.getTransNumber());
		this.setFirstName(message.getFirstName());
		this.setLastName(message.getLastName());
		this.setClassId(message.getClassId());

		if (!"".equals(message.getNationality())) {
			this.setNationality(message.getNationality());
		}

		if (!"".equals(message.getAddInfo())) {
			this.setAddData(message.getAddInfo());
		}
	}

	private void messageUpdate(LapInfo message)
	{
		this.addLap(message);

		if (message.getLapNumber() == lapsComplete) {
			setLastLap(message.getLapTime());
		}

		setBestLap(message.getLapTime());
	}

	private void messageUpdate(PassingInfo message)
	{
		this.addLap(message);

		this.setLastLap(message.getLapTime());
		this.setBestLap(message.getLapTime());
		this.setTotalTime(message.getTotalTime());
	}

	private void messageUpdate(QualInfo message)
	{
		this.setRegNumber(message.getRegNumber());
		this.setBestLap(message.getBestLapTime());
	}

	private void messageUpdate(RaceInfo message)
	{
		this.addLap(message);

		this.setRegNumber(message.getRegNumber());
		this.setPosition(message.getPosition());
		this.setLapsComplete(message.getLaps());
		this.setTotalTime(message.getTotalTime());
	}

	private void setAddData(String addData)
	{
		String oldAddData = this.addData;
		this.addData = addData;
		changeSupport.firePropertyChange("addData", oldAddData, this.addData);
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
		int oldClassId = this.classId;
		this.classId = classId;
		changeSupport.firePropertyChange("classId", oldClassId, this.classId);
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
		changeSupport.firePropertyChange("lapsComplete", oldLapsComplete, this.lapsComplete);
	}

	private void setLastLap(Duration lastLap)
	{
		Duration oldLastLap = this.lastLap;
		this.lastLap = lastLap;
		changeSupport.firePropertyChange("lastLap", oldLastLap, this.lastLap);
	}

	private void setLastName(String lastName)
	{
		String oldLastName = this.lastName;
		this.lastName = lastName;
		changeSupport.firePropertyChange("lastName", oldLastName, this.lastName);
	}

	private void setNationality(String nationality)
	{
		String oldNationality = this.nationality;
		this.nationality = nationality;
		changeSupport.firePropertyChange("nationality", oldNationality, this.nationality);
	}

	private void setNumber(String number)
	{
		String oldNumber = this.number;
		this.number = number;
		changeSupport.firePropertyChange("number", oldNumber, this.number);
	}

	private void setPosition(int position)
	{
		int oldPosition = this.position;
		this.position = position;
		changeSupport.firePropertyChange("position", oldPosition, this.position);
	}

	private void setRegNumber(String regNumber)
	{
		String oldRegNumber = this.regNumber;
		this.regNumber = regNumber;
		changeSupport.firePropertyChange("regNumber", oldRegNumber, this.regNumber);
	}

	private void setTotalTime(Duration totalTime)
	{
		Duration oldTotalTime = this.totalTime;
		this.totalTime = totalTime;
		changeSupport.firePropertyChange("totalTime", oldTotalTime, this.totalTime);
	}

	private void setTransNumber(String transNumber)
	{
		String oldTransNumber = this.transNumber;
		this.transNumber = transNumber;
		changeSupport.firePropertyChange("transNumber", oldTransNumber, this.transNumber);
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

	public static Duration getFastestLap()
	{
		Duration fastestLap = new Duration();
		Duration competitorBestLap;

		for (Competitor competitor : instances.values()) {
			competitorBestLap = competitor.getBestLap();
			if (competitorBestLap.toInt() == 0)
				continue;

			if (fastestLap.isEmpty() || competitorBestLap.lt(fastestLap)) {
				fastestLap = competitorBestLap;
			}
		}

		return fastestLap;
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

	public static void reset()
	{
		instances = new HashMap<String, Competitor>();
	}

	public static void updateOrCreate(RMonitorMessage message)
	{
		Competitor instance = Competitor.getInstance(message.getRegNumber());

		instance = (instance == null) ? new Competitor() : instance;

		if (message.getClass() == RaceInfo.class) {
			instance.messageUpdate((RaceInfo) message);
		} else if (message.getClass() == CompInfo.class) {
			instance.messageUpdate((CompInfo) message);
		} else if (message.getClass() == LapInfo.class) {
			instance.messageUpdate((LapInfo) message);
		} else if (message.getClass() == QualInfo.class) {
			instance.messageUpdate((QualInfo) message);
		} else if (message.getClass() == PassingInfo.class) {
			instance.messageUpdate((PassingInfo) message);
		}

		instances.put(instance.getRegNumber(), instance);
	}
	
	public static void setCompetitorTO(RaceTO raceTO){
		RaceTO.CompetitorTO[] competitors = new RaceTO.CompetitorTO[instances.size()];
		int i = 0;
		for (Competitor competitor : instances.values()) {
			RaceTO.CompetitorTO competitorTO = raceTO.new CompetitorTO(competitor.number,competitor.position, competitor.lapsComplete, competitor.firstName, competitor.lastName, competitor.totalTime.toString(), competitor.bestLap.toString(), competitor.lastLap.toString());
			competitors[i++] = competitorTO;
		}
		raceTO.competitors = competitors;	
	}
}
