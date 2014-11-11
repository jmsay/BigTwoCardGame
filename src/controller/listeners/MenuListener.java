package controller.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JOptionPane;

import controller.ClientController;
import controller.GameController;
import controller.MusicController;
import controller.ServerController;
import model.*;
import view.*;

public class MenuListener implements MouseListener{
	private Menu menu;
	private Game game;
	private GameListener gameListener;
	private int mode = 0, color =0, ai=0, mm=0;
	private MusicController bgMusic;
	private ClientController clientController;
	private GameController gameController;
	private int connectionStatus;
	private InetAddress host;
	
	private ArrayList<Player> players;
	private int HOME_PLAYER;
	private String homePlayer;
	private ServerController serverController;
	public MenuListener(Menu m, Game g, GameListener gL, MusicController bgM, ServerController tcpS, ClientController tcpC, GameController gS){
		menu =m;
		game =g;		
		gameListener =gL;
		bgMusic = bgM;
		serverController = tcpS;
		clientController = tcpC;
		gameController = gS;
		connectionStatus = ClientController.DISCONNECTED;
	}
	@Override
	public void mouseClicked(MouseEvent event) {
		if(event.getSource()==menu.getPlay()){
			bgMusic.playClick();
			//new design. not yet settings frame
			//menu.setSettings();
			menu.setGameConnection();
			if(serverController.getConnectionStatus() == ServerController.CONNECTED){
				menu.getNextButton().setVisible(true);
			}
			
			
		}
		else if(event.getSource() == menu.getCreateGame()){
			bgMusic.playClick();
			String ipAddress = serverController.getIPAddress() + "";
			menu.getIPAddressTextField().setText(ipAddress);
			String serverName = serverController.getServerName() + "";
			menu.getServerNameTextField().setText(serverName);
			menu.setCreateGamePanel();
		}
		else if(event.getSource() == menu.getJoinGame()){
			bgMusic.playClick();
			menu.setJoinGamePanel();
			
			//tcpServer.displayAvailableServers();
		}
		else if(event.getSource() == menu.getCreateButton()){
			bgMusic.playClick();
			serverController.openConnection();	
			if(serverController.getConnectionStatus() == ServerController.CONNECTED){
				clientController.openConnection();	
				if(clientController.getConnectionStatus() == ClientController.CONNECTED ){
					menu.getStatusLabel().setText("Status: Connected	Server: " + serverController.getServerName());
					clientController.receiveGameState();
					gameController.setHomeName(menu.getNameTextField().getText());
					gameController.initHomePlayer();
					if(gameController.getWarningStatus() == GameController.NO_WARNING){
						clientController.sendGameState();
						clientController.openReceiveThread();
						menu.getStatusLabel().setVisible(true);
						menu.setSettings();	
						menu.getStartButton().setVisible(true);
					}
					else{
						JOptionPane.showMessageDialog(null, "Player Name already in use.");
						clientController.closeConnection();
					}
				}
			}
		}
		else if(event.getSource()==menu.getJoinButton()){
			bgMusic.playClick();
            //host = menu.getHostTextField().getText();
            //tcpClient.setHost(host);
			//System.out.println("host: " + host);
			System.out.println("connectionStatus: " + clientController.getConnectionStatus());
			if(clientController.getConnectionStatus() != ClientController.CONNECTED){
				if(gameController.getGameState().getPlayers().size() <= 4 ){
					System.out.println("getSource() joinButton()");
					clientController.openConnection();	//try to connect
					if(clientController.getConnectionStatus() == ClientController.CONNECTED){
						clientController.receiveGameState();
						gameController.setHomeName(menu.getNameTextField().getText());
						gameController.initHomePlayer();
						if(gameController.getWarningStatus() == GameController.NO_WARNING){
							clientController.sendGameState();
							clientController.openReceiveThread();
							menu.getStatusLabel().setVisible(true);
							menu.setSettings();
							menu.getStartButton().setVisible(false);
						}	
						else{
							JOptionPane.showMessageDialog(null, "Player Name already in use.");
							clientController.closeConnection();
						}			
					}
					else if(clientController.getConnectionStatus() == ClientController.DUPLICATE_CLIENT){
						JOptionPane.showMessageDialog(null, "Cannot Create another player on the same Program");
					}
					else if(clientController.getConnectionStatus() == ClientController.UNKNOWN_HOST){
						JOptionPane.showMessageDialog(null, "Unknown Host");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Cannot Join the game because players are full.");
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "Cannot Create another player on the same Program");
			}
			
		}
		else if(event.getSource()==menu.getHelp()){
			bgMusic.playClick();
			menu.setHelp();
		}
		else if(event.getSource()==menu.getCredits()){
			bgMusic.playClick();
			menu.setCredits();
		}
		else if(event.getSource()==menu.getBack()){
			bgMusic.playClick();
			menu.setGameConnection();
			if(serverController.getConnectionStatus() == ServerController.CONNECTED || clientController.getConnectionStatus() == ClientController.CONNECTED){
				menu.getNextButton().setVisible(true);
			}
		}
		else if(event.getSource()==menu.getNextButton()){
			bgMusic.playClick();
			menu.setSettings();
			if(serverController.getConnectionStatus() == ServerController.CONNECTED){
				menu.getStartButton().setVisible(true);
			}
		}
		else if(event.getSource()==menu.getMode()){
			bgMusic.playClick();
			if(mode==0) mode++;
			else mode=0;
		}
		else if(event.getSource()==menu.getColor()){
			bgMusic.playClick();
			if(color==0) color++;
			else color=0;
			if(color==0) menu.getColor().setIcon(menu.getColor1Image());
			else  menu.getColor().setIcon(menu.getColor2Image());
		}
		else if(event.getSource()==menu.getAI()){
			bgMusic.playClick();
			if(ai<2) ai++;
			else ai=0;
			if(ai==0) menu.getAI().setIcon(menu.getAi1Image());
			else if(ai==1) menu.getAI().setIcon(menu.getAi2Image());
			else menu.getAI().setIcon(menu.getAi3Image());
		}else if(event.getSource()==menu.getmm()){
			bgMusic.playClick();
			if(mm==0) mm++;
			else mm=0;
			if(mm==0) menu.getmm().setIcon(menu.getMinMax());
			else  menu.getmm().setIcon(menu.getMinMaxAB());
		}		
		else if(event.getSource()==menu.getStartButton()){
			bgMusic.playClick();
			bgMusic.stopIntro();
			if(gameController.getGameState().getPlayers().size() >= 3 && gameController.getGameState().getPlayers().size() <= 4 ){
				gameController.getGameState().setPanelSwitch(true);
				gameController.getGameState().setPanel(GameState.GAME);
				clientController.sendGameState();			
			}
			else{
				JOptionPane.showMessageDialog(null, "Cannot start without sufficient players");
			}
		}
		else if(event.getSource()==menu.getQuit()){
			bgMusic.playClick();
			//put this in the quit button
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
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public void setAi(int ai) {
		this.ai = ai;
	}

}
