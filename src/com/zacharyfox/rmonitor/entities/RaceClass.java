package com.zacharyfox.rmonitor.entities;

import java.util.HashMap;

import com.zacharyfox.rmonitor.message.ClassInfo;

public class RaceClass
{
	private static HashMap<Integer, String> instances = new HashMap<Integer, String>();
	
	private RaceClass()
	{
	}
	
	public static void update(ClassInfo message)
	{
		String className = instances.get(message.getUniqueId());
		
		if (className == null) {
			instances.put(message.getUniqueId(), message.getDescription());
		}
	}
	
	public static String getClassName(Integer classId)
	{
		String className = instances.get(classId);
		
		return (className == null) ? "" : className;
	}
}
