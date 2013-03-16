package com.zacharyfox.rmonitor.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Recorder
{
	private BufferedWriter bufferedWriter;
	private FileWriter fileWriter;
	private long startTime = 0;

	public Recorder(String fileName)
	{
		try {
			fileWriter = new FileWriter(fileName);
			bufferedWriter = new BufferedWriter(fileWriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close()
	{
		try {
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void push(String message)
	{
		if (startTime == 0) {
			startTime = new Date().getTime();
		}
		long timeStamp = new Date().getTime() - startTime;
		try {
			bufferedWriter.write(timeStamp + " " + message + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
