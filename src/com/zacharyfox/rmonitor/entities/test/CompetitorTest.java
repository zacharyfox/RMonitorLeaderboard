package com.zacharyfox.rmonitor.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.junit.Test;

import com.zacharyfox.rmonitor.entities.Competitor;
import com.zacharyfox.rmonitor.message.CompInfo;
import com.zacharyfox.rmonitor.message.LapInfo;
import com.zacharyfox.rmonitor.message.PassingInfo;
import com.zacharyfox.rmonitor.message.QualInfo;
import com.zacharyfox.rmonitor.message.RaceInfo;
import com.zacharyfox.rmonitor.utils.Duration;

public class CompetitorTest
{

	protected boolean bestLapFired = false;
	protected boolean classIdFired = false;
	protected boolean firstNameFired = false;
	protected boolean lapsCompleteFired = false;
	protected boolean lapsFired = false;
	protected boolean lastLapFired = false;
	protected boolean lastNameFired = false;
	protected boolean nationalityFired = false;
	protected boolean numberFired = false;
	protected boolean positionFired = false;
	protected boolean totalTimeFired = false;
	protected boolean transNumberFired = false;

	@Test
	public void testAddLap()
	{
		final Competitor competitor = getCompetitor();

		this.playLapMessages();

		ArrayList<Competitor.Lap> laps = competitor.getLaps();

		assertEquals(1, laps.get(0).lapNumber);
		assertEquals(3, laps.get(0).position);
		assertEquals(new Duration("00:01:47.872"), laps.get(0).lapTime);
		assertEquals(new Duration("00:01:47.872"), laps.get(0).totalTime);

		assertEquals(2, laps.get(1).lapNumber);
		assertEquals(2, laps.get(1).position);
		assertEquals(new Duration("00:01:46.749"), laps.get(1).lapTime);
		assertEquals(new Duration("00:03:34.621"), laps.get(1).totalTime);
	}

	@Test
	public void testAvgLap()
	{
		final Competitor competitor = getCompetitor();

		assertEquals(new Duration(), competitor.getAvgLap());

		this.playLapMessages();

		assertEquals(new Duration("00:01:47.310"), competitor.getAvgLap());
	}

	@Test
	public void testGetByPosition()
	{
		final Competitor competitor = getCompetitor();
		this.playLapMessages();

		assertEquals(competitor, Competitor.getByPosition(2));
	}

	@Test
	public void testGetFastestLap()
	{
		this.playLapMessages();
		assertEquals(new Duration("00:01:46.749"), Competitor.getFastestLap());
	}

	@Test
	public void testGetPositionInClass()
	{
		final Competitor competitor = getCompetitor();

		this.playLapMessages();
		assertEquals(1, competitor.getPositionInClass());
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
	public void testUpdateLapInfo()
	{
		final Competitor competitor = getCompetitor();

		String[] tokens = {
			"$SP", "3", "1234BE", "2", "00:01:33.894", "76682"
		};

		LapInfo message = new LapInfo(tokens);

		Competitor.updateOrCreate(message);

		ArrayList<Competitor.Lap> laps = competitor.getLaps();

		for (Competitor.Lap lap : laps) {
			if (lap.lapNumber == 2) {
				assertEquals(lap.lapTime, new Duration("00:01:33.894"));
			}
		}

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
			"$SP", "3", "1234BE", "3", "00:01:31.123", "76682"
		};

		LapInfo message_1 = new LapInfo(tokens_1);

		Competitor.updateOrCreate(message_1);

		assertTrue(bestLapFired);
	}

	@Test
	public void testUpdatePassingInfo()
	{
		final Competitor competitor = getCompetitor();

		String[] tokens = {
			"$J", "1234BE", "01:12:47.872", "01:12:47.872"
		};

		PassingInfo message = new PassingInfo(tokens);

		competitor.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if ("lastLap".equals(evt.getPropertyName())) {
					lastLapFired = true;
					assertEquals(new Duration("01:12:47.872"), evt.getNewValue());
					assertEquals(new Duration("01:12:47.872"), competitor.getLastLap());
				}

				if ("totalTime".equals(evt.getPropertyName())) {
					totalTimeFired = true;
					assertEquals(new Duration("01:12:47.872"), evt.getNewValue());
					assertEquals(new Duration("01:12:47.872"), competitor.getTotalTime());
				}
			}
		});

		Competitor.updateOrCreate(message);

		assertTrue(lastLapFired);
		assertTrue(totalTimeFired);
	}

	@Test
	public void testUpdateQualInfo()
	{
		final Competitor competitor = getCompetitor();

		String[] tokens = {
			"$H", "1", "1234BE", "1", "01:12:47.872"
		};

		QualInfo message = new QualInfo(tokens);

		competitor.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if ("bestLap".equals(evt.getPropertyName())) {
					bestLapFired = true;
					assertEquals(new Duration("01:12:47.872"), evt.getNewValue());
					assertEquals(new Duration("01:12:47.872"), competitor.getBestLap());
				}
			}
		});

		Competitor.updateOrCreate(message);

		assertTrue(bestLapFired);
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

	private void playLapMessages()
	{
		String[] tokens = {
			"$G", "3", "1234BE", "1", "00:01:47.872"
		};

		RaceInfo message = new RaceInfo(tokens);

		String[] tokens2 = {
			"$J", "1234BE", "00:01:47.872", "00:01:47.872"
		};

		PassingInfo message2 = new PassingInfo(tokens2);

		String[] tokens3 = {
			"$J", "1234BE", "00:01:46.749", "00:03:34.621"
		};

		PassingInfo message3 = new PassingInfo(tokens3);

		String[] tokens4 = {
			"$G", "2", "1234BE", "2", "00:03:34.621"
		};

		RaceInfo message4 = new RaceInfo(tokens4);

		Competitor.updateOrCreate(message);
		Competitor.updateOrCreate(message2);
		Competitor.updateOrCreate(message3);
		Competitor.updateOrCreate(message4);
	}

}
