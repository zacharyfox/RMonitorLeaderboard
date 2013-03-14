package com.zacharyfox.rmonitor.utils;

import java.util.concurrent.TimeUnit;

public class Duration
{
	private long milliseconds;
	
	public Duration(float duration)
	{
		milliseconds = (long) (duration * 1000);
	}

	public Duration(int duration)
	{
		milliseconds = (long) (duration * 1000);
	}

	public Duration(String duration)
	{
		String[] tokens = duration.split(":");
		int hours = Integer.parseInt(tokens[0]);
		int minutes = Integer.parseInt(tokens[1]);
		float seconds = Float.parseFloat(tokens[2]);
		float durationFloat = (float) (3600 * hours + 60 * minutes + seconds);
		milliseconds = (int) (durationFloat * 1000);
	}

	@Override
	public boolean equals(Object other)
	{
		return (milliseconds == ((Duration) other).milliseconds);
	}

	public Float toFloat()
	{
		return (float) milliseconds / 1000;
	}

	public int toInt()
	{
		return (int) milliseconds / 1000;
	}

	@Override
	public String toString()
	{
		long hours = TimeUnit.MILLISECONDS.toHours(milliseconds);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds));
		long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds));
		long millis = TimeUnit.MILLISECONDS.toMillis(milliseconds) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(milliseconds));
		
		return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
	}
}
