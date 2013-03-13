package com.zacharyfox.rmonitor.message.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zacharyfox.rmonitor.message.ClassInfo;
import com.zacharyfox.rmonitor.message.CompInfo;
import com.zacharyfox.rmonitor.message.Factory;
import com.zacharyfox.rmonitor.message.Heartbeat;
import com.zacharyfox.rmonitor.message.InitRecord;
import com.zacharyfox.rmonitor.message.PassingInfo;
import com.zacharyfox.rmonitor.message.QualInfo;
import com.zacharyfox.rmonitor.message.RMonitorMessage;
import com.zacharyfox.rmonitor.message.RaceInfo;
import com.zacharyfox.rmonitor.message.RunInfo;
import com.zacharyfox.rmonitor.message.SettingInfo;

public class FactoryTest
{

	@Test
	public void testClassInfo()
	{
		String line = "$C,5,\"Formula 300\"";
		RMonitorMessage message = Factory.getMessage(line);

		assertEquals(ClassInfo.class, message.getClass());
	}

	@Test
	public void testCompInfo()
	{
		String line = "$A,\"1234BE\",\"12X\",52474,\"John\",\"Johnson\",\"USA\",5";
		RMonitorMessage message = Factory.getMessage(line);

		assertEquals(CompInfo.class, message.getClass());

		String line_1 = "$COMP,\"1234BE\",\"12X\",5,\"John\",\"Johnson\",\"USA\",\"CAMEL\"";
		RMonitorMessage message_1 = Factory.getMessage(line_1);

		assertEquals(CompInfo.class, message_1.getClass());
	}

	@Test
	public void testHeartbeat()
	{
		String line = "$F,14,\"00:12:45\",\"13:34:23\",\"00:09:47\",\"Green\"";
		RMonitorMessage message = Factory.getMessage(line);

		assertEquals(Heartbeat.class, message.getClass());
	}

	@Test
	public void testInitRecord()
	{
		String line = "$I,\"16:36:08.000\",\"12 jan 01\"";
		RMonitorMessage message = Factory.getMessage(line);

		assertEquals(InitRecord.class, message.getClass());
	}

	@Test
	public void testPassingInfo()
	{
		String line = "$J,\"1234BE\",\"00:02:03.826\",\"01:42:17.672\"";
		RMonitorMessage message = Factory.getMessage(line);

		assertEquals(PassingInfo.class, message.getClass());
	}

	@Test
	public void testQualInfo()
	{
		String line = "$H,2,\"1234BE\",3,\"00:02:17.872\"";
		RMonitorMessage message = Factory.getMessage(line);

		assertEquals(QualInfo.class, message.getClass());
	}

	@Test
	public void testRaceInfo()
	{
		String line = "$G,3,\"1234BE\",14,\"01:12:47.872\"";
		RMonitorMessage message = Factory.getMessage(line);

		assertEquals(RaceInfo.class, message.getClass());
	}

	@Test
	public void testRunInfo()
	{
		String line = "$B,5,\"Friday free practice\"";
		RMonitorMessage message = Factory.getMessage(line);

		assertEquals(RunInfo.class, message.getClass());
	}

	@Test
	public void testSettingInfo()
	{
		String line = "$E,\"TRACKNAME\",\"Indianapolis Motor Speedway\"";
		RMonitorMessage message = Factory.getMessage(line);

		assertEquals(SettingInfo.class, message.getClass());
	}
}
