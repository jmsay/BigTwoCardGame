package controller;

import java.awt.Component;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.rmi.ServerException;
import java.rmi.server.ServerCloneException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import model.Card;
import model.GameState;
import model.Player;
import view.*;

public class ClientController {
	private InetAddress ipAddress;
	private int port;
	private InetAddress serverName;
	//temporary
	private String ipAddressString;
	
	public static final int DISCONNECTED = 0;
	public static final int CONNECTED = 1;
	
	
	public static final int DUPLICATE_CLIENT = 4;
	public static final int UNKNOWN_HOST = 5;
	public static final int INVALID_PORT = 6;
	public int homePlayerID;
	private String homePlayer;
	private GameController gameController;
	private ArrayList<Player> players = new ArrayList<Player>();
	
	private static final int PORT = 1234;
	private ClientThread clientThread;
	private boolean isExit = false;
	Socket socket; //Step 1
	//socket = new Socket("192.168.173.60", PORT);//Step 1		change this if server computer changes
	Scanner clientScanner;
	ObjectInputStream receiveStream;
	ObjectOutputStream sendStream;

	int connectionStatus = DISCONNECTED;
	int previousPanel;
	
	Game game;
	Menu menu;
	
	public ClientController(Game g, Menu m, GameController gC){
		game = g;
		menu = m;
		gameController = gC;
	}

	public void openConnection(){
		isExit = false;
		// TODO Auto-generated method stub
		System.out.println("*********************CLIENT**********************");
		if(connectionStatus == ClientController.CONNECTED || connectionStatus == ClientController.DUPLICATE_CLIENT){
			connectionStatus = DUPLICATE_CLIENT;
			return;
		}		
		try{
			port = Integer.parseInt(menu.getPortTextField().getText());
			//ipAddress = InetAddress.getByName(menu.getIPAddressTextField().getText());
			serverName =  InetAddress.getByName(menu.getServerNameTextField().getText());
			
		}
		catch (UnknownHostException e) {
			connectionStatus = UNKNOWN_HOST;
			return;
		}
		catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Invalid Port");
			connectionStatus = INVALID_PORT;
			return;
		}
			try {
				socket = new Socket(serverName, port); //Step 1
				sendStream = new ObjectOutputStream(socket.getOutputStream());
				receiveStream = new ObjectInputStream(socket.getInputStream());	
				gameController.setClientController(this);
				connectionStatus = CONNECTED;
			}
			catch(UnknownHostException e){
				e.printStackTrace();
			}
			catch(ServerException e){
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//System.out.println("Connection Refused. Server not ready.");
				connectionStatus =  DISCONNECTED;
			}			
	}
	
	public void openReceiveThread(){		
		clientThread = new ClientThread(this, socket);
		clientThread.start();
	}

	public void sendGameState(){
		try{
			sendStream.writeObject(gameController.getGameState());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public synchronized void receiveGameState(){
		try {
			gameController.setGameState((GameState)receiveStream.readObject());
			System.out.println("ClientController-> gameController.getGameState().getTurn(): " + gameController.getGameState().getTurn() );
			gameController.updateGameStateInterface();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(SocketException e){
			System.out.println("socket closed");
			isExit = true;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	
	public void closeConnection(){
		try{
			isExit = true;
			socket.close();
		}
		catch(IOException ioEx){
			//System.out.println("Unable to Disconnect!");
			connectionStatus = CONNECTED;
		}
		connectionStatus = DISCONNECTED;
	}
	public boolean getIsExit(){
		return isExit;
	}
	
	public int getConnectionStatus(){
		return connectionStatus;
	}
	
	
	
	public void setPort(int p){
		port = p;
	}
	
}