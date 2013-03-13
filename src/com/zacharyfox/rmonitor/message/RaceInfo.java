package com.zacharyfox.rmonitor.message;

import com.zacharyfox.rmonitor.utils.Duration;

public class RaceInfo extends RMonitorMessage
{
	private int laps;
	private int position;
	private String regNumber;
	private Duration totalTime;

	public RaceInfo(String[] tokens)
	{
		position = Integer.parseInt(tokens[1]);
		regNumber = tokens[2];
		laps = (tokens[3].equals("")) ? 0 : Integer.parseInt(tokens[3]);
		totalTime = new Duration(tokens[4]);
	}

	public int getLaps()
	{
		return laps;
	}

	public int getPosition()
	{
		return position;
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
