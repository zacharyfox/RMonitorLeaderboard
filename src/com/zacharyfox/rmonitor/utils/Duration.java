package com.zacharyfox.rmonitor.utils;

public class Duration
{
	private float durationFloat;

	private String durationString;

	public Duration(Float duration)
	{
		durationFloat = duration;
		durationString = formatIntoHHMMSS(duration);
	}

	public Duration(int duration)
	{
		durationFloat = duration;
		durationString = formatIntoHHMMSS(duration);
	}

	public Duration(String duration)
	{
		durationString = duration;

		String[] tokens = durationString.split(":");
		int hours = Integer.parseInt(tokens[0]);
		int minutes = Integer.parseInt(tokens[1]);
		Float seconds = Float.parseFloat(tokens[2]);
		durationFloat = (int) (3600 * hours + 60 * minutes + seconds);
	}

	@Override
	public boolean equals(Object other)
	{
		return (durationFloat == ((Duration) other).toFloat());
	}

	public Float toFloat()
	{
		return durationFloat;
	}

	public int toInt()
	{
		return (int) durationFloat;
	}

	@Override
	public String toString()
	{
		return durationString;
	}

	private static String formatIntoHHMMSS(float floatIn)
	{
		int secsIn = (int) floatIn;
		int hours = secsIn / 3600, remainder = secsIn % 3600, minutes = remainder / 60, seconds = remainder % 60;

		return ((hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":"
			+ (seconds < 10 ? "0" : "") + seconds);
	}

	private static String formatIntoHHMMSS(int secsIn)
	{
		int hours = secsIn / 3600, remainder = secsIn % 3600, minutes = remainder / 60, seconds = remainder % 60;

		return ((hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":"
			+ (seconds < 10 ? "0" : "") + seconds);

	}
}
