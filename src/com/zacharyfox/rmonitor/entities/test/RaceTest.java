package com.zacharyfox.rmonitor.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.Test;

import com.zacharyfox.rmonitor.entities.Race;
import com.zacharyfox.rmonitor.message.Heartbeat;
import com.zacharyfox.rmonitor.message.RaceInfo;
import com.zacharyfox.rmonitor.message.RunInfo;
import com.zacharyfox.rmonitor.utils.Duration;

public class RaceTest
{
	private boolean competitorsVersionFired = false;
	private boolean elapsedTimeFired = false;
	private boolean flagStatusFired = false;
	private boolean idFired = false;
	private boolean lapsToGoFired = false;
	private boolean nameFired = false;
	private boolean scheduledTimeFired = false;
	private boolean timeOfDayFired = false;
	private boolean timeToGoFired = false;

	@Test
	public void testUpdateHeartbeat()
	{
		Heartbeat message = new Heartbeat(new String[] {
			"$F", "14", "00:12:45", "13:34:23", "00:09:47", "Green"
		});

		Race race = new Race();

		race.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if ("elapsedTime".equals(evt.getPropertyName())) {
					elapsedTimeFired = true;
					assertEquals(new Duration("00:09:47"), evt.getNewValue());
				}

				if ("lapsToGo".equals(evt.getPropertyName())) {
					lapsToGoFired = true;
					assertEquals(14, evt.getNewValue());
				}

				if ("timeToGo".equals(evt.getPropertyName())) {
					timeToGoFired = true;
					assertEquals(new Duration("00:12:45"), evt.getNewValue());
				}

				if ("scheduledTime".equals(evt.getPropertyName())) {
					scheduledTimeFired = true;
					assertEquals(new Duration("00:22:32"), evt.getNewValue());
				}

				if ("timeOfDay".equals(evt.getPropertyName())) {
					timeOfDayFired = true;
					assertEquals(new Duration("13:34:23"), evt.getNewValue());
				}

				if ("flagStatus".equals(evt.getPropertyName())) {
					flagStatusFired = true;
					assertEquals("Green", evt.getNewValue());
				}
			}
		});

		race.update(message);

		assertTrue(elapsedTimeFired);
		assertTrue(lapsToGoFired);
		assertTrue(timeToGoFired);
		assertTrue(scheduledTimeFired);
		assertTrue(timeOfDayFired);
		assertTrue(flagStatusFired);
	}

	@Test
	public void testUpdateRaceInfo()
	{
		RaceInfo message = new RaceInfo(new String[] {
			"$G", "3", "1234BE", "14", "01:12:47.872"
		});

		Race race = new Race();

		race.addPropertyChangeListener("competitorsVersion", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				competitorsVersionFired = true;
				assertEquals(1, evt.getNewValue());
			}
		});

		race.update(message);

		assertTrue(competitorsVersionFired);
	}

	@Test
	public void testUpdateRunInfo()
	{
		RunInfo message = new RunInfo(new String[] {
			"$B", "5", "Friday free practice"
		});

		Race race = new Race();

		race.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if ("name".equals(evt.getPropertyName())) {
					nameFired = true;
					assertEquals("Friday free practice", evt.getNewValue());
				}

				if ("id".equals(evt.getPropertyName())) {
					idFired = true;
					assertEquals(5, evt.getNewValue());
				}
			}
		});

		race.update(message);

		assertTrue(nameFired);
		assertTrue(idFired);
	}
}
