package com.zacharyfox.rmonitor.message.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zacharyfox.rmonitor.message.ClassInfo;

public class ClassInfoTest
{
	@Test
	public void test()
	{
		String[] tokens = {
			"$C", "5", "Formula 300"
		};

		ClassInfo message = new ClassInfo(tokens);

		assertEquals("Class ID 5", 5, message.getUniqueId());
		assertEquals("Class Description is Formula 300", "Formula 300", message.getDescription());
	}
}
