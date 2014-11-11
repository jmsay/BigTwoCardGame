package controller;
import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Player;
import model.GameState;


public class ServerThread extends Thread{
	private ServerController serverController;
	private Socket socket;
	Scanner input;
	PrintWriter output;
	private int portID = 0;
	ObjectInputStream receiveStream;
	ObjectOutputStream sendStream;
	Player player;
	GameState gameState;
	
	
	private static int INITIALIZE = 0;
	private static int RUNNING_GAME = 1;
	private boolean exit;
	
	int ctr = 0;
	int clientID;
	
	public ServerThread(ServerController tcpES, Socket s, GameState gS, int ID){
		serverController = tcpES;
		socket = s;
		portID = socket.getPort();
		gameState = gS;
		clientID = ID;
	}	

	
	public void openStream() {
		// TODO Auto-generated method stub
		try {
			receiveStream = new ObjectInputStream(socket.getInputStream());
			sendStream = new ObjectOutputStream(socket.getOutputStream());
			//tcpEchoServer.sendGameStateToAll(1234, gameState);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//step 3
	}
	
	public void run(){
		while(exit == false){
			receiveGameState();
			gameState = serverController.evaluateGameState(gameState);
			System.out.println("ServerThread-> gameState.getTurn(): " + gameState.getTurn());
			serverController.sendGameStateToAll(portID, gameState, ServerController.NORMAL);
		}
	}
	
	
	
	private void receiveGameState(){
		try{
			gameState = (GameState)receiveStream.readObject();
		}
		catch(EOFException e){
			serverController.removeThread(portID);
		}
		catch(SocketException e){
			e.printStackTrace();	
		}
		catch (IOException | ClassNotFoundException ex){
			ex.printStackTrace();
		}			
	}	
	
	
	public void closeConnection(){
		try{
//			System.out.println("* Closing COnnection...*" );
			exit = true;
			socket.close();//Step 5
		}
		catch(IOException ioEx){
			System.out.println("Unable to disconnect!");
			System.exit(1);
		}
	}
	
	public ObjectOutputStream getSendStream(){
		return sendStream;
	}
	public int getPortID(){
		return portID;
	}
	
	public void setExit(boolean e){
		exit = e;
	}
	
}