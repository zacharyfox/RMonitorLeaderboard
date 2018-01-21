package com.zacharyfox.rmonitor.message;

public class CompInfo extends RMonitorMessage
{
	private String addInfo;
	private int classId;
	private String firstName;
	private String lastName;
	private String nationality;
	private String number;
	private String regNumber;
	private String transNumber;

	public CompInfo(String[] tokens)
	{
		if (tokens[0].equals("$A")) {
			regNumber = tokens[1];
			number = tokens[2];
			transNumber = tokens[3];
			firstName = tokens[4];
			lastName = tokens[5];
			nationality = tokens[6];
			classId = Integer.parseInt(tokens[7]);
		} else if (tokens[0].equals("$COMP")) {
			regNumber = tokens[1];
			number = tokens[2];
			classId = Integer.parseInt(tokens[3]);
			firstName = tokens[4];
			lastName = tokens[5];
			nationality = tokens[6];
			addInfo = tokens[7];
		}
	}

	public String getAddInfo()
	{
		return addInfo;
	}

	public int getClassId()
	{
		return classId;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getNationality()
	{
		return nationality;
	}

	public String getNumber()
	{
		return number;
	}

	@Override
	public String getRegNumber()
	{
		return regNumber;
	}

	public String getTransNumber()
	{
		return transNumber;
	}
}
