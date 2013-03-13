package com.zacharyfox.rmonitor.message;

public class SettingInfo extends RMonitorMessage
{
	private String description;
	private String value;

	public SettingInfo(String[] tokens)
	{
		description = tokens[1];
		value = tokens[2];
	}

	public String getDescription()
	{
		return description;
	}

	public String getValue()
	{
		return value;
	}
}