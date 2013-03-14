package com.zacharyfox.rmonitor.utils.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zacharyfox.rmonitor.utils.Duration;

public class DurationTest
{
	

	@Test
	public void testFloat()
	{
		Float floatVal = (float) 3600.123;
		int intVal = 3600;
		String stringVal = "01:00:00.123";
		
		Duration duration = new Duration(floatVal);

		assertEquals(intVal, duration.toInt());
		assertEquals(floatVal, duration.toFloat());
		assertEquals(stringVal, duration.toString());
	}

	@Test
	public void testInt()
	{
		Float floatVal = (float) 3600.0;
		int intVal = 3600;
		String stringVal = "01:00:00.000";
		
		Duration duration = new Duration(intVal);

		assertEquals(intVal, duration.toInt());
		assertEquals(floatVal, duration.toFloat());
		assertEquals(stringVal, duration.toString());
	}

	@Test
	public void testString()
	{
		Float floatVal = (float) 3600.123;
		int intVal = 3600;
		String stringVal = "01:00:00.123";
		
		Duration duration = new Duration(stringVal);

		assertEquals(intVal, duration.toInt());
		assertEquals(floatVal, duration.toFloat());
		assertEquals(stringVal, duration.toString());
	}
	
	@Test
	public void testString2()
	{
		Float floatVal = (float) 3600.0;
		int intVal = 3600;
		String stringVal = "01:00:00";
		
		Duration duration = new Duration(stringVal);

		assertEquals(intVal, duration.toInt());
		assertEquals(floatVal, duration.toFloat());
		assertEquals("01:00:00.000", duration.toString());
	}
	
	@Test
	public void testEquals()
	{
		assertEquals(new Duration("01:00:00.000"), new Duration("01:00:00"));
		assertEquals(new Duration("01:00:00.000"), new Duration(3600));
		assertEquals(new Duration("01:00:00.123"), new Duration((float) 3600.123));
	} 

}
