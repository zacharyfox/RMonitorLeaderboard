package com.zacharyfox.rmonitor.utils;

import java.util.concurrent.TimeUnit;

public class Duration
{
	private final long milliseconds;

	public Duration()
	{
		milliseconds = 0;
	}

	public Duration(float duration)
	{
		milliseconds = (long) (duration * 1000);
	}

	public Duration(int duration)
	{
		milliseconds = (duration * 1000);
	}

	public Duration(String duration)
	{
		String[] tokens = duration.split(":");
		int hours = Integer.parseInt(tokens[0]);
		int minutes = Integer.parseInt(tokens[1]);
		float seconds = Float.parseFloat(tokens[2]);
		float durationFloat = ((3600 * hours) + (60 * minutes) + seconds);
		milliseconds = (int) (durationFloat * 1000);
	}

	@Override
	public boolean equals(Object other)
	{
		return (milliseconds == ((Duration) other).milliseconds);
	}

	public boolean isEmpty()
	{
		return (milliseconds == 0);
	}

	public boolean lt(Object other)
	{
		return (milliseconds < ((Duration) other).milliseconds);
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
		String string = "";

		long hours = TimeUnit.MILLISECONDS.toHours(milliseconds);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
			- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds));
		long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
			- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds));
		long millis = TimeUnit.MILLISECONDS.toMillis(milliseconds)
			- TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(milliseconds));

		if (hours != 0)
			string += String.format("%d:", hours);

		if (!string.equals("")) {
			string += String.format("%02d:%02d", minutes, seconds);
		} else {
			string = String.format("%d:%02d", minutes, seconds);
		}

		if (millis != 0)
			string += String.format(".%03d", millis);

		return string;
	}
	// @Override
	// public String toString()
	// {
	// if (milliseconds == 0) {
	// return "00:00:00.000";
	// }
	// long hours = TimeUnit.MILLISECONDS.toHours(milliseconds);
	// long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
	// - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds));
	// long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
	// - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds));
	// long millis = TimeUnit.MILLISECONDS.toMillis(milliseconds)
	// - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(milliseconds));
	//
	// return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
	// }

}
