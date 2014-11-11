package controller;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import view.Game;
import view.Menu;
import model.Card;
import model.GameState;
import model.Player;

public class GameController {
	public static final int NO_WARNING = 2;
	public static final int DUPLICATE_NAME = 3;
	
	
	private GameState gameState;
	private Menu menu;
	private Game game;
	private String homeName;
	private int homeID;
	private int warningStatus = NO_WARNING;
	private ClientController clientController;
	private Random random = new Random();
	private ServerController serverController;
	private int xCenter, yCenter;
	
	GameController(Menu m, Game g, ServerController sC){
		menu = m;
		game = g;
		gameState = new GameState();
		serverController = sC;
		serverController.sendGameController(this);
	}

	public GameState distributeCardsToPlayers(GameState gameState) {
		int playerCtr = gameState.getPlayers().size();
		int deckCardSize = game.getDeckCards().size();
		for(int i = 0, j = 0; i < deckCardSize ; i++, j++){
			Card randomCard = game.getDeckCards().get(random.nextInt(game.getDeckCards().size()));
			if(randomCard.getType() == Card.SPADE && randomCard.getNumber() == 2){
				gameState.setTurn(j % playerCtr);
				gameState.setWinner(j % playerCtr);
			}
			gameState.getPlayers().get(j % playerCtr).getCards().add(randomCard);
			game.removeCard(randomCard.getDeckCardNumber());
		}
		return gameState;
	}
	
	public void initHomePlayer(){
		warningStatus = NO_WARNING;
		for(int i = 0; i < gameState.getPlayers().size(); i++){
			if(gameState.getPlayers().get(i).getName().equals(homeName)){
				warningStatus = DUPLICATE_NAME;
				break;
			}
		}
		if(warningStatus == NO_WARNING){
			homeID = gameState.getPlayers().size();
			gameState.getPlayers().add(new Player(homeName));
			
		}
		
	}

	public void updateGameStateInterface(){
		ArrayList<Player> players = gameState.getPlayers();
		int playerSize = gameState.getPlayers().size();
		if(gameState.getPanel() == GameState.MENU){
			for(int i = 0; i<playerSize;i++){
				menu.getPlayersLabelList().get(i).setBounds(410, 110 + i * 30, 150, 300);
				menu.getPlayersLabelList().get(i).setText(players.get(i).getName() + " | " + players.get(i).getPortID());
			}
			for(int i = playerSize; i < 4;i++){
				menu.getPlayersLabelList().get(i).setBounds(410, 110 + i * 30, 150, 300);
				menu.getPlayersLabelList().get(i).setText("empty");
			}
		}		
		if(gameState.getPanel() == GameState.GAME && gameState.getNewGameSwitch() == true){
			gameState.setPanelSwitch(false);
			game.setVisible(true);
			menu.setVisible(false);
		}
		if(gameState.getPanel() == GameState.GAME){	
			int x = 0;
			int labelCoordinates[][] = {
					{20, 480},
					{700, 480},
					{300, 50}
			};
			int playerCtr = 0;
			System.out.println("GameController->gameState.getTurn(): " + gameState.getTurn());
			if(gameState.getTurn() == homeID){
				game.getPassButton().setEnabled(true);
				game.getSubmitButton().setEnabled(true);
			}
			else{
				game.getPassButton().setEnabled(false);
				game.getSubmitButton().setEnabled(false);
			}	
			displayMoveCards();
			for(int i = 0; i< playerSize; i++){
				Player iPlayer = gameState.getPlayers().get(i);
				game.getPlayersLabelList().get(i).setText(iPlayer.getName() + " | " + iPlayer.getPortID());
				if(gameState.getTurn() == i){
					//setColor
					//game.getPlayersLabelList().get(i).setColor(Color.YELLOW);
				}
				if(iPlayer.getName().equals(homeName)){
					game.getPlayersLabelList().get(i).setBounds(300, 510, 400, 50);
					displayCards(iPlayer, 200, 400, 30, 0);
					//if(all selected cards are valid)
					//	game.getSubmitButton().setEnabled(true);
				}
				else if(playerCtr == 0){
					game.getPlayersLabelList().get(i).setBounds(labelCoordinates[playerCtr][x], 480, 400, 50);
					displayCards(iPlayer, 50, 100, 0 , 30);
					playerCtr++;
				}
				else if(playerCtr == 1){
					game.getPlayersLabelList().get(i).setBounds(labelCoordinates[playerCtr][x] + 50, 480, 400, 50);
					displayCards(iPlayer, 700, 100, 0, 30);
					playerCtr++;
				}
				else if(playerCtr == 2){
					game.getPlayersLabelList().get(i).setBounds(labelCoordinates[playerCtr][x], 480, 400, 50);
					displayCards(iPlayer, 200, 100, 30, 0);
					playerCtr++;
				}
			}
		}	
	}
	
	public void displayMoveCards(){
		yCenter = 100;
		int playerSize = gameState.getPlayers().size();
		for(int i = 0; i< playerSize; i++){
			Player iPlayer = gameState.getPlayers().get(i);
			for(int j = 0; j<iPlayer.getOldMoveCards().size(); j++){
				Card jCard = iPlayer.getMoveCards().get(j);
				game.getDeckCardButtons().get(jCard.getDeckCardNumber()).setVisible(false);
			}
			for(int j = 0; j<iPlayer.getMoveCards().size(); j++){
				Card jCard = iPlayer.getMoveCards().get(j);
				game.getDeckCardButtons().get(jCard.getDeckCardNumber()).setBounds(150 +(i * 120), 100 + (j * 30), 80, 120);
				game.getDeckCardButtons().get(jCard.getDeckCardNumber()).setIcon(game.getCardIcon(jCard.getDeckCardNumber(), jCard.getStatus(), Card.FACE_UP));		
			}
		}
	}
	
	public void displayCards(Player iPlayer, int x, int y, int xPosition, int yPosition){
		int position;
		if(iPlayer.getName().equals(homeName)){
			position = Card.FACE_UP;
		}
		else{
			position = Card.FACE_DOWN;
		}
		for(int j = 0; j<iPlayer.getCards().size(); j++){
			Card jCard = iPlayer.getCards().get(j);
			game.getDeckCardButtons().get(jCard.getDeckCardNumber()).setBounds(x + (j * xPosition), y + (j * yPosition), 80, 120);
			game.getDeckCardButtons().get(jCard.getDeckCardNumber()).setIcon(game.getCardIcon(jCard.getDeckCardNumber(), jCard.getStatus(), position));
		}
	}
	public void removeHomePlayer(){
		for(int i = 0; i< gameState.getPlayers().size(); i++){
			if(gameState.getPlayers().get(i).getName().equals(homeName)){
				gameState.getPlayers().remove(i);
			}
		}
	}

	public void setGameState(GameState gS) {
		gameState = gS;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public String getHomeName(){
		return homeName;
	}
	public void setHomeName(String hM){
		homeName = hM;
	}

	public int getHomeID(){
		return homeID;
	}
	public void setHomeID(int hID){
		homeID = hID;
	}
	
	public int getWarningStatus() {
		return warningStatus;
	}
	
	public void setClientController(ClientController cC){
		clientController = cC;
	}
} 