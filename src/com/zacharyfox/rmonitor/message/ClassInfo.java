package com.zacharyfox.rmonitor.message;

public class ClassInfo extends RMonitorMessage
{
	private String description;
	private int unique;

	public ClassInfo(String[] tokens)
	{
		unique = Integer.parseInt(tokens[1]);
		description = tokens[2];
	}

	public String getDescription()
	{
		return description;
	}

	public int getUniqueId()
	{
		return unique;
	}
}
