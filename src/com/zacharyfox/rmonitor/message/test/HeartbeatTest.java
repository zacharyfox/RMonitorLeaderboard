package com.zacharyfox.rmonitor.message.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zacharyfox.rmonitor.message.Heartbeat;
import com.zacharyfox.rmonitor.utils.Duration;

public class HeartbeatTest
{

	@Test
	public void test()
	{
		String[] tokens = {
			"$F", "14", "00:12:45", "13:34:23", "00:09:47", "Green"
		};

		Heartbeat message = new Heartbeat(tokens);

		assertEquals("Laps to go", 14, message.getLapsToGo());
		assertEquals(new Duration("00:12:45"), message.getTimeToGo());
		assertEquals(new Duration("13:34:23"), message.getTimeOfDay());
		assertEquals(new Duration("00:09:47"), message.getRaceTime());
		assertEquals("Green", message.getFlagStatus());
	}
}
