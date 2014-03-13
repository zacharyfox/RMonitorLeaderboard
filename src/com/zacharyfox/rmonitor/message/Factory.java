package com.zacharyfox.rmonitor.message;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public abstract class Factory
{
	// private HashMap<String, String> = new HashMap<String, String>();
	private static HashMap<String, Class<?>> classMap = new HashMap<String, Class<?>>() {
		private static final long serialVersionUID = -4460006007376415842L;

		{
			put("$F", Heartbeat.class);
			put("$B", RunInfo.class);
			put("$G", RaceInfo.class);
			put("$A", CompInfo.class);
			put("$C", ClassInfo.class);
			put("$H", QualInfo.class);
			put("$E", SettingInfo.class);
			put("$I", InitRecord.class);
			put("$J", PassingInfo.class);
			put("$SP", LapInfo.class);
			put("$SR", LapInfo.class);
			put("$COMP", CompInfo.class);
		}
	};

	@SuppressWarnings("unchecked")
	public static <M extends RMonitorMessage> M getMessage(String line)
	{
		// xSystem.out.println(line);
		// TODO: better tokenizing here - doesn't handle values with commas
		String[] tokens = line.split(",");

		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = tokens[i].replaceAll("\"", "");
		}
		Class<?> messageClass = classMap.get(tokens[0]);

		try {
			Constructor<?> constructor = messageClass.getDeclaredConstructor(String[].class);
			return (M) constructor.newInstance(new Object[] {
				tokens
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
