package com.zacharyfox.rmonitor.message;

import com.zacharyfox.rmonitor.utils.Duration;

public class InitRecord extends RMonitorMessage
{
	private Duration timeOfDay;

	public InitRecord(String[] tokens)
	{
		timeOfDay = new Duration(tokens[1]);
	}

	public Duration getTimeOfDay()
	{
		return timeOfDay;
	}
}