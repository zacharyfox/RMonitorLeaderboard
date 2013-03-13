package com.zacharyfox.rmonitor.utils.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

import com.zacharyfox.rmonitor.utils.Connection;

public class ConnectionTest
{

	@Test
	public void test() throws Exception
	{
		Thread serverThread = new Thread(new Runnable() {
			@Override
			public void run()
			{
				try {
					ServerSocket serverSocket = new ServerSocket(12345);
					Socket clientSocket = serverSocket.accept();
					DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
					outToClient.writeBytes("Testing line 1\n");
					outToClient.writeBytes("Testing line 2\n");
					outToClient.writeBytes("Testing line 3\n");
					outToClient.close();
					clientSocket.close();
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		serverThread.start();

		Connection connection = new Connection("localhost", 12345);

		assertTrue(connection.isConnected());
		assertEquals("Testing line 1", connection.readLine());
		assertEquals("Testing line 2", connection.readLine());
		assertEquals("Testing line 3", connection.readLine());
		assertTrue(connection.isConnected());
		connection.close();
		assertTrue(connection.isClosed());
	}
}
