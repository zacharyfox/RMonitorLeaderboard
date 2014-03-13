package com.zacharyfox.rmonitor.utils;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

/*
 * SMA: 10 period sum / 10
 * Multiplier: (2 / (Time periods + 1) ) = (2 / (10 + 1) ) = 0.1818 (18.18%)
 * EMA: {Close - EMA(previous day)} x multiplier + EMA(previous day).
 */
abstract public class ExponentialMovingAverage
{

	public static long getAverage(TreeMap<Integer, Long> laps)
	{
		return laps.get(laps.lastKey()) / laps.lastKey();
	}

	public static long predictNext(TreeMap<Integer, Long> laps)
	{
		float smoothing = (((float) 2) / (laps.size() + 1));
		long previousEMA = laps.get(laps.firstKey());

		Set<Integer> keys = laps.keySet();

		for (Iterator<Integer> i = keys.iterator(); i.hasNext();) {
			Integer key = i.next();
			previousEMA = (long) ((laps.get(key) - previousEMA) * smoothing + previousEMA);
		}

		return (long) ((previousEMA - previousEMA) * smoothing + previousEMA);
	}
}
