package com.zacharyfox.rmonitor.message.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zacharyfox.rmonitor.message.RaceInfo;
import com.zacharyfox.rmonitor.utils.Duration;

public class RaceInfoTest
{
	@Test
	public void test()
	{
		String[] tokens = {
			"$G", "3", "1234BE", "14", "01:12:47.872"
		};

		RaceInfo message = new RaceInfo(tokens);

		assertEquals(3, message.getPosition());
		assertEquals("1234BE", message.getRegNumber());
		assertEquals(14, message.getLaps());
		assertEquals(new Duration("01:12:47.872"), message.getTotalTime());
	}
}
