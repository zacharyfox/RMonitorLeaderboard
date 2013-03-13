RMonitor Leaderboard
====================

RMonitor Leaderboard is a swing application designed for displaying race summary information from an rmonitor feed over
tcp, such as the one provided by the MyLaps Orbits software.

![Screenshot](/screenshot.png)

Protocol information was taken from here: http://www.imsatiming.com/software/Protocols/AMB%20RMonitor%20Timing%20Protocol.pdf

It displays summary information and a sortable table of the current competitors in the race. In addition, it will
provide an estimate of time or laps remaining (based on time to go / laps to go received from the rmonitor feed.)

In addition to the swing application, this repository contains library packages for handling rmonitor feed data.

Building the Application
------------------------

// TODO

Packages
--------

### com.zacharyfox.rmonitor.entities

This package contains the models for the race and competitors.

### com.zacharyfox.rmonitor.entities.test

Tests for the above.

### com.zacharyfox.rmonitor.leaderboard

This package contains the swing application and related UI classes.

### com.zacharyfox.rmonitor.message

This package contains classes for each type of message provided in the protocol, and a factory for creating the objects
from an ascii string. Example usage below (returns a Heartbeat):

	import com.zacharyfox.rmonitor.message.*
	
	String line = "$F,14,\"00:12:45\",\"13:34:23\",\"00:09:47\",\"Green\"";
	RMonitorMessage message = Factory.getMessage(line);

### com.zacharyfox.rmonitor.message.test

Tests for the above.

### com.zacharyfox.rmonitor.utils

Contains the utility classes Connection and Duration.

### com.zacharyfox.rmonitor.utils.test

Tests for the above