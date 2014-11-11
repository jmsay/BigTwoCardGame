package controller;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import controller.listeners.GameListener;
import controller.listeners.MainFrameListener;
import controller.listeners.MenuListener;
import model.*;
import view.*;

public class MainController {
	private Menu menu;
	private Game game;
	private EndGame endGame;
	private EndListener endListener;	
	private MenuListener menuListener;
	private GameListener gameListener;
	private GameController gameController;
	private MainFrameListener jFrameListener;
	private ClientController clientController;
	private ServerController serverController;
	private ArrayList<Player> players = new ArrayList<Player>();
	
	private MusicController bgMusic = new MusicController();
	private JFrame frame;
	
	public MainController(Menu m, Game g, EndGame e, JFrame f){
		menu =m;
		game =g;
		endGame =e;
		frame = f;
		initClass();
		initListeners();
		bgMusic.playIntro();
	}
	
	private void initClass(){
		serverController = new ServerController(menu, game);
		gameController = new GameController(menu, game, serverController);
		clientController = new ClientController(game, menu, gameController);
		

		gameListener = new GameListener(menu, game, bgMusic, endGame, clientController, gameController);
		menuListener = new MenuListener(menu, game, gameListener, bgMusic,serverController, clientController, gameController);
		jFrameListener = new MainFrameListener(frame, bgMusic, clientController, serverController, gameController);
		endListener = new EndListener();
	}
	
	private void initListeners(){
		menu.getPlay().addMouseListener(menuListener);
		menu.getHelp().addMouseListener(menuListener);
		menu.getCredits().addMouseListener(menuListener);
		menu.getBack().addMouseListener(menuListener);
		menu.getMode().addMouseListener(menuListener);
		menu.getColor().addMouseListener(menuListener);
		menu.getAI().addMouseListener(menuListener);
		menu.getStartButton().addMouseListener(menuListener);
		menu.getQuit().addMouseListener(menuListener);
		menu.getmm().addMouseListener(menuListener);
		menu.getConnectionButton().addMouseListener(menuListener);
		menu.getCreateGame().addMouseListener(menuListener);
		menu.getJoinGame().addMouseListener(menuListener);
		menu.getCreateButton().addMouseListener(menuListener);
		menu.getNextButton().addMouseListener(menuListener);
		menu.getJoinButton().addMouseListener(menuListener);
		
		game.getBackMenuButton().addMouseListener(gameListener);
		game.getPassButton().addMouseListener(gameListener);
		game.getSubmitButton().addMouseListener(gameListener);
		for(int i = 0; i < game.getDeckCardButtons().size(); i++){
			game.getDeckCardButtons().get(i).addMouseListener(gameListener);
		}
		endGame.getBackMenuButton().addMouseListener(endListener);
		frame.addWindowListener(jFrameListener);
		
	}
	
	
	public class EndListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if(arg0.getSource()==endGame.getBackMenuButton()){
				bgMusic.playIntro();
				gameListener.resetGame();
				endGame.setVisible(false);
				menu.setVisible(true);
				menu.setMenu();
				
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		
	}

}
