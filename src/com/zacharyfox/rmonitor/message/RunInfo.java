package com.zacharyfox.rmonitor.message;

public class RunInfo extends RMonitorMessage
{
	private String raceName;
	private int unique;

	public RunInfo(String[] tokens)
	{
		unique = Integer.parseInt(tokens[1]);
		raceName = tokens[2];
	}

	public String getRaceName()
	{
		return raceName;
	}

	public int getUniqueId()
	{
		return unique;
	}
}