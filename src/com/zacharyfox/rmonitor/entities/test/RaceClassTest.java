package com.zacharyfox.rmonitor.entities.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zacharyfox.rmonitor.entities.RaceClass;
import com.zacharyfox.rmonitor.message.ClassInfo;
import com.zacharyfox.rmonitor.message.Factory;
import com.zacharyfox.rmonitor.message.RMonitorMessage;

public class RaceClassTest
{
	@Test
	public void testUpdateRaceClass()
	{
		assertEquals("", RaceClass.getClassName(5));

		String line = "$C,5,\"Formula 300\"";
		RMonitorMessage message = Factory.getMessage(line);

		RaceClass.update((ClassInfo) message);

		assertEquals("Formula 300", RaceClass.getClassName(5));

		String line2 = "$C,5,\"Formula 1000\"";
		RMonitorMessage message2 = Factory.getMessage(line2);

		RaceClass.update((ClassInfo) message2);

		assertEquals("Formula 1000", RaceClass.getClassName(5));
	}
}
