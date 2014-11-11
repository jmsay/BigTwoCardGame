package controller;
import java.util.*;
import java.io.*;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import model.*;
import view.*;



public class ServerController implements Runnable{
	private static ServerSocket serverSocket;
	private static final int PORT = 1234;
	public static final int DISCONNECTED = 0;
	public static final int CONNECTED = 1;
	public static final int DUPLICATE_SERVER = 2;
	public static final int DUPLICATE_PORT = 3;
	public static final int INVALID_PORT = 4;
	private static final int NEW_THREAD = 5;
	public static final int NORMAL = 6;
	private static final int THREAD_REMOVED = 7;
	private static int CONTINUE = 8;
	
	private int port;
	private String serverName;
	private InetAddress ipAddress;
	
	private Thread thread = null;
	private ArrayList<ServerThread> clientList = new ArrayList<ServerThread>();  
	private int clientID = -1;
	ArrayList<Player> players = new ArrayList<Player>();
	GameState gameState;
	private int connectionStatus = DISCONNECTED;
	private Menu menu;
	private Game game;
	private GameController gameController;
	
	
	public ServerController(Menu m, Game g){
		menu = m;
		game = g;
		gameState = new GameState();
		try {
			ipAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    serverName = ipAddress.getHostName();

	}
	
	public void openConnection(){
		System.out.println("**********************SERVER*************************");
		//System.out.println("Opening Port....");
		if(connectionStatus == CONNECTED || connectionStatus == DUPLICATE_SERVER){
			JOptionPane.showMessageDialog(null, "Computer already used as Server.");
			connectionStatus = DUPLICATE_SERVER;
			return;
		}
		try{
			port = Integer.parseInt(menu.getPortTextField().getText());
		}
		catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Invalid Port");
			connectionStatus = INVALID_PORT;
			return;
		}
		
				
		try{
			serverSocket = new ServerSocket(port);
		}catch(BindException e){
			JOptionPane.showMessageDialog(null, "Address already in use.");
			return;
		}
		
		catch(IOException ioEx){
			ioEx.printStackTrace();
			return;
		}
		connectionStatus = CONNECTED;
		if (thread == null){
			thread = new Thread(this); 
			thread.start();
	    }
	}

	public void run(){
		   while (thread != null){
			   try{
//	                        System.out.println("Waiting for a client ..."); 
	                        addThread(serverSocket.accept());
	                    }
	                    catch(IOException ioe){
	                        //System.out.println("Server accept error: " + ioe); stop();
	                    }
			   catch(NullPointerException ex){}
	      }
	   }
	  
	
	 private void addThread(Socket socket){ 
		   clientID = clientList.size();
		   clientList.add(new ServerThread(this, socket, gameState, clientID));
//		   System.out.println("addThread() clientList.size(): " + clientList.size());
		   clientList.get(clientID).openStream(); 
		   sendGameStateToAll( 1234 , gameState, NEW_THREAD);
		   clientList.get(clientID).start();
	 }
	 
	
	public synchronized void sendGameStateToAll(int portID, GameState gS, int sender){
		gameState = gS;
//		System.out.println("sendGameStateToAll(): gameState.getPlayers().size(): " + gameState.getPlayers().size());
//		System.out.println("sendGameStateToAll(): clientList.size(): " + clientList.size());
		try{
			if(clientID > -1 && sender != NEW_THREAD){
				gameState.getPlayers().get(clientID).setPortID(portID);
				clientID = -1;
			}
		}
		catch(IndexOutOfBoundsException e){
			removeThread(portID);
		}
		for(int i = 0; i< clientList.size(); i++){
			int action = checkPlayer(clientList.get(i).getPortID(), sender);
			if(action == THREAD_REMOVED){
				action = CONTINUE;
				i--;
				continue;
			}
			try {				
				clientList.get(i).getSendStream().writeObject(gameState);	
			}
			catch(SocketException e){
				//System.out.println("sendGameState()->socket closed PortID: " + clientList.get(i).getPortID());
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
	
	public int checkPlayer(int portID, int sender){
		if(clientList.size() > gameState.getPlayers().size() && sender !=NEW_THREAD){
			for(int i = 0; i<gameState.getPlayers().size();i++){
				System.out.println("portID: " + portID);
				if(gameState.getPlayers().get(i).getPortID() ==  portID){
					return CONTINUE;
				}
			}
			removeThread(portID);
			System.out.println("<-removeThread() portID: " + portID);
			return THREAD_REMOVED;
		}
		return CONTINUE;
	}
	
	public synchronized void removeThread(int portID){
		for(int i = 0; i< clientList.size(); i++){
			if(clientList.get(i).getPortID() == portID){
				clientList.get(i).closeConnection();
				clientList.remove(i);
			}
		}
	}

	public void removePlayer(int portID){
		for(int i = 0; i< gameState.getPlayers().size(); i++){
			if(gameState.getPlayers().get(i).getPortID() == portID){
				gameState.getPlayers().remove(i);
			}
		}
	}
	

	public GameState evaluateGameState(GameState gameState) {
		if(gameState.getNewGameSwitch() == true){
			gameState = gameController.distributeCardsToPlayers(gameState);
		}
		
		boolean evaluate = true;
		for(int i = 0; i < gameState.getPlayers().size(); i++){
			if(gameState.getPlayers().get(i).getMove() == Player.NO_MOVE){
 				evaluate = false;
 				break;
			}
		}
		if(evaluate){
			sendGameStateToAll(1234, gameState, NORMAL);
			int winnerID = 1;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(true){
					gameState.setWinner(winnerID);
					gameState.setTurn(winnerID);
					gameState.setNewTurn(true);
					for(int i = 0; i < gameState.getPlayers().size(); i++){
						gameState.getPlayers().get(i).setMove(Player.NO_MOVE);
					}
			}
			System.out.println("ServerController(evaluator)->Winner!!: " + gameState.getTurn());
			for(int i = 0; i< gameState.getPlayers().size(); i++){
				gameState.getPlayers().get(i).setOldMoveCards(gameState.getPlayers().get(i).getMoveCards());
				gameState.getPlayers().get(i).getMoveCards().clear();
			}
			
		}
		return gameState;
	}
	
	public void setConnectionStatus(int cS){
		connectionStatus = cS;
	}
	
	public void setPort(int p){
		port = p;
	}
	
	public void setServerName(String sN){
		serverName = sN;
	}
	public String getServerName(){
		return serverName;
	}

	public InetAddress getIPAddress() {
		// TODO Auto-generated method stub
		return ipAddress;
	}

	public int getConnectionStatus() {
		// TODO Auto-generated method stub
		return connectionStatus;
	}

	public void sendGameController(GameController gC) {
		gameController = gC;
		
	}

	
}