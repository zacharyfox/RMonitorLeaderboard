package com.zacharyfox.rmonitor.message.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zacharyfox.rmonitor.message.InitRecord;
import com.zacharyfox.rmonitor.utils.Duration;

public class InitRecordTest
{
	@Test
	public void test()
	{
		String[] tokens = {
			"$I", "16:36:08.000", "12 jan 01"
		};

		InitRecord message = new InitRecord(tokens);

		assertEquals(new Duration("16:36:08.000"), message.getTimeOfDay());
	}
}
