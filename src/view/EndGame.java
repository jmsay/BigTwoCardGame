package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class EndGame extends JPanel{
	private ImageIcon blackWon = new ImageIcon("images/buttons/blackWon.png");
	private ImageIcon whiteWon = new ImageIcon("images/buttons/whiteWon.png");
	private ImageIcon draw = new ImageIcon("images/buttons/draw.png");
	private JLabel winner, gameBG;
	private JButton backToMenu;
		
	public EndGame(){
		setLayout(null);
		setBounds(0, 0, 1000, 600);
		initComponents();
	}
	private void initComponents(){
		
		gameBG = new JLabel(new ImageIcon("images/bg/bg.gif"));
		gameBG.setBounds(0,0,1000,600);
		add(gameBG);
		
		backToMenu =new JButton(new ImageIcon("images/buttons/backMenu.png"));
		backToMenu.setBounds(670, 180,150,150);
		gameBG.add(backToMenu);
		
		winner = new JLabel();
		winner.setBounds(187,180,475, 311);
		gameBG.add(winner);
		
	}
	
	public void setWinner(int b){
		if(b==2)	winner.setIcon(blackWon);
		else if(b==1)	winner.setIcon(whiteWon);
		else winner.setIcon(draw);
		
	}
	
	public JButton getBackMenuButton(){
		return backToMenu;
	}
	
		
}
