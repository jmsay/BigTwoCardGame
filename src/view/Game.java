package view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.MainController;
import model.Card;
import model.Player;

@SuppressWarnings("serial")
public class Game extends JPanel{

	private boolean player1Turn = true;
	private JLabel player1ScoreLabel, player2ScoreLabel, gameBG, border;
	private Font centuryGothic = new Font("Century Gothic", Font.PLAIN, 30);
	private JButton backToMenu, submit, pass;
	
	private ImageIcon tileImage = new ImageIcon("images/buttons/tile.png");
	private ImageIcon blackBorder = new ImageIcon("images/bg/blackBorder.png");
	private ImageIcon whiteBorder = new ImageIcon("images/bg/whiteBorder.png");
	
	
	private ArrayList<JLabel> playersLabelList; 
	
	private ArrayList<JButton> deckCardButtons = new ArrayList<JButton>();
	private ArrayList<Card> deckCards = new ArrayList<Card>();
	
	private ImageIcon card[][] = new ImageIcon[52][2];
	private ArrayList<Player> players;
	
	private ImageIcon faceDownSelected = new ImageIcon("images/cards/dummyCardFaceDownSelected.png");
	private ImageIcon faceDownNotSelected = new ImageIcon("images/cards/dummyCardFaceDown.png");
	
	private boolean isCardInitialized = false;
	
	
	public Game(){
		setLayout(null);
		setBounds(0, 0, 1000, 600);
		repaint();
		initComponents();
		initializeCards();
		
		
	}
	private void initComponents(){

		gameBG = new JLabel(new ImageIcon("images/bg/game.png"));
		gameBG.setBounds(0,0,1000,600);
		add(gameBG);
		gameBG.setLayout(null);
		
		backToMenu =new JButton(new ImageIcon("images/buttons/backMenu.png"));
		backToMenu.setBounds(838,15,150,150);
		gameBG.add(backToMenu);
		
		pass =new JButton(new ImageIcon("images/buttons/passButton.png"));
		pass.setBounds(820,365,150,50);
		gameBG.add(pass);
		
		submit =new JButton(new ImageIcon("images/buttons/submitButton.png"));
		submit.setBounds(820,435,150,50);
		gameBG.add(submit);
		
		
		playersLabelList = new ArrayList<JLabel>();
		playersLabelList.add(new JLabel("Player 0"));
		playersLabelList.add(new JLabel("Player 1"));
		playersLabelList.add(new JLabel("Player 2"));
		playersLabelList.add(new JLabel("Player 3"));
		
		playersLabelList.get(0).setBounds(10, 10, 150, 300);
		playersLabelList.get(0).setFont(centuryGothic);
		gameBG.add(playersLabelList.get(0), 0);
		playersLabelList.get(1).setBounds(110, 140, 150, 300);
		playersLabelList.get(1).setFont(centuryGothic);
		gameBG.add(playersLabelList.get(1), 0);
		playersLabelList.get(2).setBounds(110, 170, 150, 300);
		playersLabelList.get(2).setFont(centuryGothic);
		gameBG.add(playersLabelList.get(2), 0);
		playersLabelList.get(3).setBounds(110, 200, 150, 300);
		playersLabelList.get(3).setFont(centuryGothic);
		gameBG.add(playersLabelList.get(3), 0);
		
		
		player1ScoreLabel = new JLabel(""+0);
		player1ScoreLabel.setBounds(750,250,100,100);
		player1ScoreLabel.setFont(centuryGothic);
		//gameBG.add(player1ScoreLabel);
	
		player2ScoreLabel = new JLabel(""+0);
		player2ScoreLabel.setBounds(750,400,100,100);
		player2ScoreLabel.setFont(centuryGothic);
		player2ScoreLabel.setForeground(Color.WHITE);
		//gameBG.add(player2ScoreLabel);
		
		border = new JLabel(whiteBorder);
		border.setBounds(124,49,700,300);
		//gameBG.add(border);
	}
	public void setValues(int m, int c, int a){
		
	}
	
	public void initializeCards(){
		isCardInitialized = true;
		int x = 700, y = 350;
		int k = 0;
		for(int type = 0; type < 4; type++){
			for(int number = 0; number< 5; number++){
				int top = deckCards.size();
				deckCardButtons.add(new JButton(new ImageIcon("images/cards/dummyCardFaceDown.png")));
				deckCardButtons.get(top).setBounds(400, 200, 80, 120);
				deckCards.add(new Card(type+1,number+1, top));
				card[top][0] = new ImageIcon("images/cards/"+type+ "-"+ (number + 1) +".png");
				card[top][1] = new ImageIcon("images/cards/dummyCardSelected.png");
				
				//x-=30;
				//deckCards.get(k).setBounds(x,y,120,180);
				deckCardButtons.get(top).setBorderPainted(false);
				gameBG.add(deckCardButtons.get(top),0);
				k++;
			}
		}
		System.out.println("deckCards size k: " + k );
	}


	//Player1Turn
	public boolean isPlayer1Turn() {
		return player1Turn;
	}

	public void setPlayer1Turn(boolean player1Turn) {
		this.player1Turn = player1Turn;
	}
	
	//GetLabels
	public JLabel getPlayer1Label(){
		return player1ScoreLabel;
	}
	public JLabel getPlayer2Label(){
		return player2ScoreLabel;
	}
	public JLabel getTilesBorder(){
		return border;
	}

	//GetImageIcon
	public ImageIcon getBlackBorder() {
		return blackBorder;
	}
	public ImageIcon getWhiteBorder() {
		return whiteBorder;
	}
	
	
	public JButton getBackMenuButton(){
		return backToMenu;
	}
	
	public JButton getPassButton(){
		return pass;
	}
	public JButton getSubmitButton(){
		return submit;
	}
	
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public void setPlayersLabelList(ArrayList<JLabel> pLL){
		playersLabelList = pLL;
	}
	public ArrayList<JLabel> getPlayersLabelList(){
		return playersLabelList;
	}
	
	public ImageIcon getCardIcon(int deckCardNumber,int status, int position){
		if(position == Card.FACE_DOWN && status == Card.NOT_SELECTED){
			return faceDownNotSelected;
		}
		else if(position == Card.FACE_DOWN && status == Card.SELECTED){
			return faceDownSelected;
		}
		else{
			return card[deckCardNumber][status];
		}
	}
	public ArrayList<Card> getDeckCards(){
		return deckCards;
	}
	
	public void removeCard(int deckCardNumber){
		for(int l = 0; l <deckCards.size(); l++){
			if(deckCards.get(l).getDeckCardNumber() == deckCardNumber){
				deckCards.remove(l);
				break;
			}
		}
	}
	
	
	public ArrayList<JButton> getDeckCardButtons(){
		return deckCardButtons;
	}
	public boolean getIsCardInitialized() {
		return isCardInitialized;
	}
}
