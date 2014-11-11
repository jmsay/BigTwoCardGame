package view;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;

import model.*;
import view.*;
import controller.ClientController;
import controller.MainController;

public class MainFrame {
	private JFrame frame;
	private Menu menu;
	private Game game;
	private EndGame endGame;
	private MainController controller;
	
	public final static void main(String args[]){
		setLookAndFeel();
		new MainFrame();
	}

	private MainFrame(){
		initClass();
		init369();
		showMenu();
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//		}
		menu.setMenu();
	}

	private void initClass(){
		
		frame = new JFrame();
		menu = new Menu();
		game = new Game();
		endGame = new EndGame();
		controller = new MainController(menu, game, endGame, frame);
	}

	private void init369(){
		frame.setSize(1000, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);		
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
		frame.setTitle("Big Two");
		frame.setVisible(true);
		frame.getContentPane().add(menu);
		frame.getContentPane().add(game);
		frame.getContentPane().add(endGame);
	}

	private void showMenu(){
		menu.setVisible(true);
		game.setVisible(false);
		endGame.setVisible(false);
	}
	
	private static void setLookAndFeel() {
		try {
			for (UIManager.LookAndFeelInfo laf : UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(laf.getName())) {
					UIManager.setLookAndFeel(laf.getClassName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!("Nimbus".equals(UIManager.getLookAndFeel().getName()))) {
				System.err.println("Could not find/install Nimbus LAF!");
				System.exit(0);
			}
		}
	}


	public MainController getController() {
		return controller;
	}

}
