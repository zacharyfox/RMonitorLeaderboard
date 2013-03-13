package com.zacharyfox.rmonitor.utils.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zacharyfox.rmonitor.utils.Duration;

public class DurationTest
{
	private Float floatVal = (float) 3600.000;
	private int intVal = 3600;
	private String stringVal = "01:00:00";

	@Test
	public void testFloat()
	{
		Duration duration = new Duration(floatVal);

		assertEquals(intVal, duration.toInt());
		assertEquals(floatVal, duration.toFloat());
		assertEquals(stringVal, duration.toString());
	}

	@Test
	public void testInt()
	{
		Duration duration = new Duration(intVal);

		assertEquals(intVal, duration.toInt());
		assertEquals(floatVal, duration.toFloat());
		assertEquals(stringVal, duration.toString());
	}

	@Test
	public void testString()
	{
		Duration duration = new Duration(stringVal);

		assertEquals(intVal, duration.toInt());
		assertEquals(floatVal, duration.toFloat());
		assertEquals(stringVal, duration.toString());
	}

}
