package com.zacharyfox.rmonitor.message.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zacharyfox.rmonitor.message.CompInfo;

public class CompInfoTest
{

	@Test
	public void test()
	{
		testAType();
		testCompType();
	}

	private void testAType()
	{
		String[] tokens = {
			"$A", "1234BE", "12X", "52474", "John", "Johnson", "USA", "5"
		};

		CompInfo message = new CompInfo(tokens);

		assertEquals("1234BE", message.getRegNumber());
		assertEquals("12X", message.getNumber());
		assertEquals(52474, message.getTransNumber());
		assertEquals("John", message.getFirstName());
		assertEquals("Johnson", message.getLastName());
		assertEquals("USA", message.getNationality());
		assertEquals(5, message.getClassId());
		assertEquals(null, message.getAddInfo());
	}

	private void testCompType()
	{
		String[] tokens = {
			"$COMP", "1234BE", "12X", "5", "John", "Johnson", "USA", "CAMEL"
		};

		CompInfo message = new CompInfo(tokens);

		assertEquals("1234BE", message.getRegNumber());
		assertEquals("12X", message.getNumber());
		assertEquals(0, message.getTransNumber());
		assertEquals("John", message.getFirstName());
		assertEquals("Johnson", message.getLastName());
		assertEquals("USA", message.getNationality());
		assertEquals(5, message.getClassId());
		assertEquals("CAMEL", message.getAddInfo());
	}
}
