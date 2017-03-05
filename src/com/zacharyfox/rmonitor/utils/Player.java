package com.zacharyfox.rmonitor.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.SwingWorker;

public class Player extends SwingWorker<Integer, String>
{
	private BufferedReader bufferedReader;
	private Socket clientSocket;
	private FileReader fileReader;
	private ServerSocket serverSocket;
	private int speedup = 2;

	public Player(String fileName)
	{
		try {
			serverSocket = new ServerSocket(50000);
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public void close()
	{
		try {
			clientSocket.close();
			bufferedReader.close();
			fileReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected Integer doInBackground() throws Exception
	{
		String line;
		long lastTs = 0;

		try {
			System.out.println("Player waiting for client");
			clientSocket = serverSocket.accept();
			System.out.println("Player client connected");
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			while ((line = bufferedReader.readLine()) != null) {
				String[] tokens = line.split(" ", 2);
				long tS = Integer.parseInt(tokens[0]);
				System.out.println(tokens[1]);
				out.println(tokens[1]);
				Thread.sleep((int) ((tS - lastTs) / speedup));
				lastTs = tS;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void setPlayerSpeedup(int newSpeedup){
		speedup = newSpeedup;
	}
}
