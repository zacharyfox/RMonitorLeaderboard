package com.zacharyfox.rmonitor.message;

import com.zacharyfox.rmonitor.utils.Duration;

public class PassingInfo extends RMonitorMessage
{
	private Duration lapTime;
	private String regNumber;
	private Duration totalTime;

	public PassingInfo(String[] tokens)
	{
		regNumber = tokens[1];
		lapTime = new Duration(tokens[2]);
		totalTime = new Duration(tokens[3]);
	}

	public Duration getLapTime()
	{
		return lapTime;
	}

	@Override
	public String getRegNumber()
	{
		return regNumber;
	}

	public Duration getTotalTime()
	{
		return totalTime;
	}
}
