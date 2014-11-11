package controller.listeners;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import controller.ClientController;
import controller.GameController;
import controller.MusicController;
import model.*;
import view.*;

public class GameListener implements MouseListener{
	private Menu menu;
	private Game game;
	private EndGame endGame;
	private int mode, color, ai,aiPlayer=2,player=1;
	private int index = -1;
	
	private Player minPlayer; //human
	private Player maxPlayer; //Artificial intelligence
	private Player clientPlayer;
	
	private boolean isImplementingAB;
	private MusicController bgMusic;
	private ClientController clientController;
	
	//Mode
	public final static int HUMAN = 0;
	public final static int ARTEFICIAL_INTELLIGENCE = 1;

	//Color
	public final static int BLACK = 0;
	public final static int WHITE = 1;

	//AI CATEGORIES
	public final static int EASY = 0;
	public final static int MODERATE = 1;
	public final static int HARD = 2;

	private Color playerColor=Color.WHITE, aiColor=Color.BLACK;
	private GameController gameController;
	private GameState gameState;

	public GameListener(Menu m, Game g, MusicController bgM, EndGame e, ClientController tcpC, GameController gC){
		menu =m;
		game =g;
		endGame =e;
		clientController = tcpC;
		gameController = gC;
		bgMusic = bgM;
		
		
	}
	public void setSettings(int m, int c, int a, boolean b){
		mode = m;
		color= c;
		ai =a;
		isImplementingAB = b;
	}

	public Color getColor(int colorInt){
		colorInt = colorInt%2;
		if(colorInt == WHITE) return Color.WHITE;
		else				  return Color.BLACK;
	}


	
	@Override
	public void mouseClicked(MouseEvent event) {
		String homeName = gameController.getHomeName();
		int homeID = gameController.getHomeID();
		if(event.getSource()==game.getBackMenuButton()){
			bgMusic.playClick();
			if (JOptionPane.showConfirmDialog(null,
					"Do you want to abandon the game?", null, 0, 1) == 0){
				bgMusic.playClick();
				bgMusic.playIntro();
				menu.setVisible(true);
				game.setVisible(false);
				menu.setMenu();
				resetGame();
			}
		}
		else if(event.getSource() == game.getPassButton()){
			bgMusic.playClick();
			gameState = gameController.getGameState();
			Player homePlayer = gameState.getPlayers().get(homeID);
			gameState.getPlayers().get(homeID).setMove(Player.PASS);
			for(int k = 0; k< homePlayer.getCards().size(); k++ ){
				homePlayer.getCards().get(k).setStatus(Card.NOT_SELECTED);
			}
			gameState.setTurn((homeID + 1) % gameState.getPlayers().size());
			gameController.setGameState(gameState);
			clientController.sendGameState();
		}
		
		else if(event.getSource() == game.getSubmitButton()){
			bgMusic.playClick();
			gameState = gameController.getGameState();
			Player homePlayer = gameState.getPlayers().get(homeID);
			gameState.getPlayers().get(homeID).setMove(Player.SUBMIT);
			int cardSize = homePlayer.getCards().size();
			for(int i = 0, index = 0; i< cardSize ; i++){
				Card iCard = homePlayer.getCards().get(index);
				//System.out.println("iCard.getStatus(): " + iCard.getStatus());
				if(iCard.getStatus() == Card.SELECTED){
					int topCtr = homePlayer.getMoveCards().size();
					homePlayer.getMoveCards().add(iCard);
					homePlayer.getMoveCards().get(topCtr).setStatus(Card.NOT_SELECTED);
					homePlayer.getCards().remove(index);
				}
				else{
					index++;
				}
			}			
			gameState.setTurn((homeID + 1) % gameState.getPlayers().size());
			gameController.setGameState(gameState);
			clientController.sendGameState();

		}
		gameState = gameController.getGameState();
		for(int i = 0; i<game.getDeckCardButtons().size(); i++){	
			if(event.getSource() == game.getDeckCardButtons().get(i)){
				//System.out.println("gameState.getPlayers().size(): " + gameState.getPlayers().size());
				if(gameState.getTurn() == homeID){
					Player homePlayer = gameState.getPlayers().get(homeID);
					System.out.println("GameListener->gameState.getPlayers().get(homeID).getCards().size(): " + gameState.getPlayers().get(homeID).getCards().size());
					for(int k = 0; k< homePlayer.getCards().size(); k++ ){
						if(homePlayer.getCards().get(k).getDeckCardNumber() == i){
							bgMusic.playClick();
							if(homePlayer.getCards().get(k).getStatus() == Card.NOT_SELECTED){
								homePlayer.getCards().get(k).setStatus(Card.SELECTED);
							}
							else if(homePlayer.getCards().get(k).getStatus() == Card.SELECTED){
								homePlayer.getCards().get(k).setStatus(Card.NOT_SELECTED);
							}
							
						gameController.setGameState(gameState);
						clientController.sendGameState();
						//break;
						}
					}
					break;
				}
			}
		}
	}
	private boolean isGameOver(){
		for(int k=0; k<81; k++){
			if(true) return false;
		}
		return true;
	}
	public void resetGame(){
		game.getPlayer1Label().setText(""+0);
		game.getPlayer2Label().setText(""+0);
		game.getTilesBorder().setIcon(game.getWhiteBorder());
	}

	@Override
	public void mouseEntered(MouseEvent event) {

	}

	@Override
	public void mouseExited(MouseEvent event) {

	}

	@Override
	public void mousePressed(MouseEvent event) {

	}

	@Override
	public void mouseReleased(MouseEvent event) {

	}
}
