package com.zacharyfox.rmonitor.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Zachary Fox <zachreligious@gmail.com>
 * 
 */
public class Connection extends Socket
{
	private BufferedReader clientReader;

	public Connection(String ip, int port) throws Exception
	{
		super(ip, port);
		clientReader = new BufferedReader(new InputStreamReader(this.getInputStream()));
	}

	@Override
	public void close() throws IOException
	{
		if (clientReader != null) clientReader.close();
		super.close();
	}

	public String readLine() throws IOException
	{
		String line = clientReader.readLine();
		return line;
	}
}
