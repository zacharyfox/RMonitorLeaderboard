package com.zacharyfox.rmonitor.message.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zacharyfox.rmonitor.message.QualInfo;
import com.zacharyfox.rmonitor.utils.Duration;

public class QualInfoTest
{
	@Test
	public void test()
	{
		String[] tokens = {
			"$H", "2", "1234BE", "3", "00:02:17.872"
		};

		QualInfo message = new QualInfo(tokens);

		assertEquals(2, message.getPosition());
		assertEquals("1234BE", message.getRegNumber());
		assertEquals(3, message.getBestLap());
		assertEquals(new Duration("00:02:17.872"), message.getBestLapTime());
	}
}
