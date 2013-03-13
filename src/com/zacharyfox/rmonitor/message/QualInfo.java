package com.zacharyfox.rmonitor.message;

import com.zacharyfox.rmonitor.utils.Duration;

public class QualInfo extends RMonitorMessage
{
	private int bestLap;
	private Duration bestLapTime;
	private int position;
	private String regNumber;

	public QualInfo(String[] tokens)
	{
		position = Integer.parseInt(tokens[1]);
		regNumber = tokens[2];
		bestLap = (tokens[3].equals("")) ? 0 : Integer.parseInt(tokens[3]);
		bestLapTime = new Duration(tokens[4]);
	}

	public int getBestLap()
	{
		return bestLap;
	}

	public Duration getBestLapTime()
	{
		return bestLapTime;
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
}
