package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable{
	/**
	 * ambot kun nano an gamit hine. Warai ak makapamati kan ser.
	 */
	private static final long serialVersionUID = 1L;
	public static final int MENU = 0;
	public static final int GAME = 1;
	
	//private 
	
	
	
	public int panel;
	public int playerTurn; //whose turn it is to move
	ArrayList<Player> players;
	private boolean newGameSwitch = false;
	private int turn;
	public int winner = -1; 
	public boolean newTurn = false;
	
	
	public GameState(){
		players = new ArrayList<Player>();
		panel = MENU;
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.defaultWriteObject();
	}
    
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		in.defaultReadObject();
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public void setPanel(int p){
		panel = p;
	}
	public int getPanel(){
		return panel;
	}
	
	public boolean getNewGameSwitch(){
		return newGameSwitch;
	}
	public void setPanelSwitch(boolean p){
		newGameSwitch = p;
	}
	
	public void setTurn(int t){
		turn = t;
	}
	public int getTurn(){
		return turn;
	}

	public void setWinner(int mT) {
		winner = mT;
	}

	public int getWinner() {
		return winner;
	}

	public void setNewTurn(boolean nT) {
		newTurn = nT;
		
	}
	public boolean getNewTurn() {
		return newTurn;
		
	}
}
