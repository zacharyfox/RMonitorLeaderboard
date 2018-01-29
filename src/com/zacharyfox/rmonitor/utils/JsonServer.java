package com.zacharyfox.rmonitor.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.SwingWorker;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.google.gson.Gson;
import com.zacharyfox.rmonitor.entities.Race;
import com.zacharyfox.rmonitor.entities.RaceTO;
import com.zacharyfox.rmonitor.leaderboard.RaceProvider;

public class JsonServer   extends SwingWorker<Integer, String>
{
	private class JsonHandler extends AbstractHandler
	{
		private RaceProvider raceProvider;
		private Gson gson;
		private RaceTO lastRaceTO;
		
		public JsonHandler(RaceProvider raceProvider){
			this.raceProvider = raceProvider;
			gson = new Gson();
		}
		
		@Override
	    public void handle( String target,
	                        Request baseRequest,
	                        HttpServletRequest request,
	                        HttpServletResponse response ) throws IOException,
	                                                      ServletException
	    {
	        // Declare response encoding and types
	        response.setContentType("application/json");

	        // Declare response status code
	        response.setStatus(HttpServletResponse.SC_OK);
	        
	        Object resultTO;
	        
	        System.out.println("Path Info:" + request.getPathInfo());
	        // We split the path of the request
	        String [] pathInfoParts = request.getPathInfo().split("/");
	        if (pathInfoParts.length > 1 &&  pathInfoParts[1].equals("race")) {
	        	// object selected is race
	        	//System.out.println("Path is race:" + pathInfoParts[1]);
	        	// check for an ID as second part
	        	if (pathInfoParts.length > 2 &&  pathInfoParts[2].matches("\\d+")) {
	        		int raceID = Integer.parseInt(pathInfoParts[2]);
	        		//System.out.println("Returning race with ID:" + raceID);
	        		resultTO = getRaceToReturn(raceID);
	        	}else{
	        		//System.out.println("Returning current race");
	        		resultTO = getRaceToReturn();
	        	}
	        } else if (pathInfoParts.length > 1 &&  pathInfoParts[1].equals("races")){
	        	//default we return the race
	        	resultTO = getRacesToReturn();
	        } else {
	        	resultTO = getRaceToReturn();
	        }
	        
	        

	        
	        // Write back response
	        response.getWriter().println(gson.toJson(resultTO));
	        
	        
	        // Inform jetty that this request has now been handled
	        baseRequest.setHandled(true);
	    }
		
		private RaceTO getRaceToReturn(){
			RaceTO currentRaceTO = raceProvider.getRace().getRaceTO();
			 // if the currentRaceTO has ID = 0 we try to get the lastRaceTO from the history  
	        if (lastRaceTO != null && currentRaceTO.raceID == 0){
	        	this.raceProvider.getRace();
				currentRaceTO = Race.getToByID(lastRaceTO.raceID);
	        } else {
	        	lastRaceTO = currentRaceTO;
	        }
	        return currentRaceTO;
		}
		
		private RaceTO getRaceToReturn(int id){
			
	        raceProvider.getRace();
			return Race.getToByID(id);
		}
		
		private RaceTO[] getRacesToReturn(){
			
	        return Race.getAllRaceTOs();
			
		}
	}
	
	Server jettyServer;
	
	private int port;
	


	public JsonServer(int port, RaceProvider raceProvider)
	{
		this.port = port;  
		jettyServer = new Server(this.port);
		jettyServer.setHandler(new JsonHandler(raceProvider));

		
		

	}

	
	public void stopServer()
	{
		try {
			jettyServer.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Override
	protected Integer doInBackground() throws Exception
	{
		try {
			jettyServer.start();
			jettyServer.join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
