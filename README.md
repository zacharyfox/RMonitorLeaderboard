RMonitor Leaderboard
====================

RMonitor Leaderboard is a swing application designed for displaying race summary information from an rmonitor feed over
tcp, such as the one provided by the MyLaps Orbits software.

![Screenshot](/docs/screenshot.png)

Protocol information was taken from here: http://www.imsatiming.com/software/Protocols/AMB%20RMonitor%20Timing%20Protocol.pdf

It displays summary information and a sortable table of the current competitors in the race. In addition, it will
provide an estimate of time or laps remaining (based on time to go / laps to go received from the rmonitor feed.)

In addition to the swing application, this repository contains library packages for handling rmonitor feed data.

Building the Application
------------------------

// TODO

LIbrary Packages
----------------

### com.zacharyfox.rmonitor.entities

This package contains the models for the race and competitors.

### com.zacharyfox.rmonitor.message

This package contains classes for each type of message provided in the protocol, and a factory for creating the objects
from an ascii string. Example usage below (returns a Heartbeat):

	import com.zacharyfox.rmonitor.message.*
	
	String line = "$F,14,\"00:12:45\",\"13:34:23\",\"00:09:47\",\"Green\"";
	RMonitorMessage message = Factory.getMessage(line);

### com.zacharyfox.rmonitor.utils

#### Duration

Duration takes time values as strings ("00:01:23.456"), integers (milliseconds), or floats (seconds) supplied by the
feed and stores them as milliseconds.

	import com.zacharyfox.rmonitor.utils.Duration
	
	Duration duration = new Duration("00:01:23.456");

#### Connection

Connection extends Socket and contains a BufferedLineReader.

	import com.zacharyfox.rmonitor.utils.Connection;
	
	String ip = "127.0.0.1";
	Integer port = 50000;
	Connection connection = new Connection(ip, port);
	
	while ((line = connection.readLine()) != null) {
		System.out.println(line);
	}

