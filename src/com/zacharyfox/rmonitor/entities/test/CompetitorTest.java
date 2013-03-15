package com.zacharyfox.rmonitor.entities.test;

import static org.junit.Assert.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

import org.junit.Test;

import com.zacharyfox.rmonitor.entities.Competitor;
import com.zacharyfox.rmonitor.message.CompInfo;
import com.zacharyfox.rmonitor.message.LapInfo;
import com.zacharyfox.rmonitor.message.RaceInfo;
import com.zacharyfox.rmonitor.utils.Duration;

public class CompetitorTest
{

	protected boolean numberFired = false;
	protected boolean transNumberFired = false;
	protected boolean firstNameFired = false;
	protected boolean lastNameFired = false;
	protected boolean nationalityFired = false;
	protected boolean classIdFired = false;
	protected boolean lapsFired = false;
	protected boolean bestLapFired = false;
	protected boolean positionFired = false;
	protected boolean lapsCompleteFired = false;
	protected boolean totalTimeFired = false;
	
	private Competitor getCompetitor()
	{
		Competitor.reset();
		
		String[] tokens = {
			"$A", "1234BE", "12X", "52474", "John", "Johnson", "USA", "5"
		};
		
		CompInfo message = new CompInfo(tokens);
		Competitor.updateOrCreate(message);
		
		return Competitor.getInstance("1234BE");
	}
	
	@Test
	public void testUpdateCompInfo()
	{
		final Competitor competitor = getCompetitor();
		
		String[] tokens = {
			"$A", "1234BE", "123", "54321", "Jack", "Jackson", "MX", "6"
		};

		CompInfo message = new CompInfo(tokens);
		
		competitor.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if ("number".equals(evt.getPropertyName())) {
					numberFired = true;
					assertEquals("123", evt.getNewValue());
					assertEquals("123", competitor.getNumber());
				}

				if ("transNumber".equals(evt.getPropertyName())) {
					transNumberFired = true;
					assertEquals(54321, evt.getNewValue());
					assertEquals(54321, competitor.getTransNumber());
				}

				if ("firstName".equals(evt.getPropertyName())) {
					firstNameFired = true;
					assertEquals("Jack", evt.getNewValue());
					assertEquals("Jack", competitor.getFirstName());
				}

				if ("lastName".equals(evt.getPropertyName())) {
					lastNameFired = true;
					assertEquals("Jackson", evt.getNewValue());
					assertEquals("Jackson", competitor.getLastName());
				}

				if ("nationality".equals(evt.getPropertyName())) {
					nationalityFired = true;
					assertEquals("MX", evt.getNewValue());
					assertEquals("MX", competitor.getNationality());
				}

				if ("classId".equals(evt.getPropertyName())) {
					classIdFired = true;
					assertEquals(6, evt.getNewValue());
					assertEquals(6, competitor.getClassId());
				}
			}
		});

		Competitor.updateOrCreate(message);

		assertTrue(numberFired);
		assertTrue(transNumberFired);
		assertTrue(firstNameFired);
		assertTrue(lastNameFired);
		assertTrue(nationalityFired);
		assertTrue(classIdFired);
	}
	
	@Test
	public void testUpdateRaceInfo()
	{
		final Competitor competitor = getCompetitor();
		
		String[] tokens = {
			"$G", "3", "1234BE", "14", "01:12:47.872"
		};

		RaceInfo message = new RaceInfo(tokens);
		
		competitor.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if ("position".equals(evt.getPropertyName())) {
					positionFired = true;
					assertEquals(3, evt.getNewValue());
					assertEquals(3, competitor.getPosition());
				}

				if ("lapsComplete".equals(evt.getPropertyName())) {
					lapsCompleteFired = true;
					assertEquals(14, evt.getNewValue());
					assertEquals(14, competitor.getLapsComplete());
				}

				if ("totalTime".equals(evt.getPropertyName())) {
					totalTimeFired = true;
					assertEquals(new Duration("01:12:47.872"), evt.getNewValue());
					assertEquals(new Duration("01:12:47.872"), competitor.getTotalTime());
				}
			}
		});

		Competitor.updateOrCreate(message);

		assertTrue(positionFired);
		assertTrue(lapsCompleteFired);
		assertTrue(totalTimeFired);
	}
	
	@Test
	public void testUpdateLapInfo()
	{
		final Competitor competitor = getCompetitor();
		
		String[] tokens = {
			"$SP","3","1234BE","2","00:01:33.894","76682"
		};

		LapInfo message = new LapInfo(tokens);
		
		Competitor.updateOrCreate(message);
		
		HashMap<Integer, Object[]> laps = competitor.getLaps();
		
		assertEquals(laps.get(2)[0], 2);
		assertEquals(laps.get(2)[1], 3);
		assertEquals(laps.get(2)[2], new Duration("00:01:33.894"));
		
		competitor.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if ("bestLap".equals(evt.getPropertyName())) {
					bestLapFired = true;
					assertEquals(new Duration("00:01:31.123"), evt.getNewValue());
					assertEquals(new Duration("00:01:31.123"), competitor.getBestLap());
				}
			}
		});
		
		String[] tokens_1 = {
			"$SP","3","1234BE","3","00:01:31.123","76682"
		};

		LapInfo message_1 = new LapInfo(tokens_1);
		
		Competitor.updateOrCreate(message_1);
		
		assertTrue(bestLapFired);
	}

}
