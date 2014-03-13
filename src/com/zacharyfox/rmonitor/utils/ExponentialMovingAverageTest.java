package com.zacharyfox.rmonitor.utils;

import java.util.TreeMap;

import org.junit.Test;

public class ExponentialMovingAverageTest
{

	@Test
	public void test()
	{
		TreeMap<Integer, Long> laps = new TreeMap<Integer, Long>();
		laps.put(1, (long) 2227);
		laps.put(2, (long) 2219);
		laps.put(3, (long) 2208);
		laps.put(4, (long) 2217);
		laps.put(5, (long) 2213);
		laps.put(6, (long) 2223);
		laps.put(7, (long) 2243);
		laps.put(8, (long) 2224);
		laps.put(9, (long) 2229);

		System.out.println(ExponentialMovingAverage.predictNext(laps));
	}
}
