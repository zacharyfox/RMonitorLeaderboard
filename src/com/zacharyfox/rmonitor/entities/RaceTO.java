package com.zacharyfox.rmonitor.entities;


public class RaceTO {
	
	public class CompetitorTO{
		public String number;
		public int position;
		public int lapsComplete;
		public String firstName;
		public String lastName;
		public String totalTime;
		public String bestLap;
		public String lastLap;
		
		
		public CompetitorTO(String number, int position, int lapsComplete,
				String firstName, String lastName, String totalTime,
				String bestLap, String lastLap) {
			this.number = number;
			this.position = position;
			this.lapsComplete = lapsComplete;
			this.firstName = firstName;
			this.lastName = lastName;
			this.totalTime = totalTime;
			this.bestLap = bestLap;
			this.lastLap = lastLap;
		}
		
		
		
	}
	
	public String raceName;
	public int raceID;
	public String flagStatus;
	public String elapsedTime;
	public String timeOfDay;
	public int lapsComplete;
	public int lapsToGo;
	public String trackName;
	public CompetitorTO[] competitors;

	public RaceTO(String elapsedTime, String flagStatus, int id, int lapsToGo,
			int lapsComplete, String name, String timeOfDay, String trackName ) {
		this.elapsedTime = elapsedTime;
		this.flagStatus = flagStatus;
		this.raceID = id;
		this.lapsToGo = lapsToGo;
		this.lapsComplete = lapsComplete;
		this.raceName = name;
		this.timeOfDay = timeOfDay;
		this.trackName = trackName;
	}
	
	
	
	
	
	
	
}
