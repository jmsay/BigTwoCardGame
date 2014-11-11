package controller.listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.ClientController;
import controller.GameController;
import controller.MusicController;
import controller.ServerController;
import model.*;
import view.*;


public class MainFrameListener implements WindowListener{
	private MusicController bgMusic;
	private JFrame frame;
	private ClientController clientController;
	private ServerController serverController;
	private GameController gameController;
	
	public MainFrameListener(JFrame f, MusicController bgM, ClientController tcpC, ServerController tcpS, GameController gS){
		frame = f;
		bgMusic = bgM;
		clientController = tcpC;
		serverController = tcpS;
		gameController = gS;
		
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		bgMusic.playClick();
		if (JOptionPane.showConfirmDialog(null,
				"Do you really want to quit?", null, 0, 1) == 0){
			if(clientController.getConnectionStatus() == ClientController.CONNECTED){
				System.out.println("windowClosing()");
				gameController.removeHomePlayer();
				gameController.updateGameStateInterface();
				clientController.sendGameState();
				clientController.closeConnection();
			}
			System.exit(0);
		}
			
			
			
			
			
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
