package controller;
import java.net.Socket;


public class ClientThread extends Thread{
	Socket socket;
	ClientController tcpClient;
	
	public ClientThread(ClientController tcpEC, Socket s){
		socket = s;
		tcpClient = tcpEC;
		
	}
	
	public void run(){
		while(tcpClient.getIsExit() == false){
			tcpClient.receiveGameState();
		}
	}
}
