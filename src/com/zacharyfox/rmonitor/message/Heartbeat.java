package com.zacharyfox.rmonitor.message;

import com.zacharyfox.rmonitor.utils.Duration;

public class Heartbeat extends RMonitorMessage
{
	private String flagStatus;
	private int lapsToGo;
	private Duration raceTime;
	private Duration timeOfDay;
	private Duration timeToGo;

	public Heartbeat(String[] tokens)
	{
		lapsToGo = Integer.parseInt(tokens[1]);
		timeToGo = new Duration(tokens[2]);
		timeOfDay = new Duration(tokens[3]);
		raceTime = new Duration(tokens[4]);
		flagStatus = tokens[5].trim();
	}

	public String getFlagStatus()
	{
		return flagStatus;
	}

	public int getLapsToGo()
	{
		return lapsToGo;
	}

	public Duration getRaceTime()
	{
		return raceTime;
	}

	public Duration getTimeOfDay()
	{
		return timeOfDay;
	}

	public Duration getTimeToGo()
	{
		return timeToGo;
	}
}
