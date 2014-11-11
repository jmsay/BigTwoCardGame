package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3483726649310566361L;
	public static final int NO_MOVE = 0;
	public static final int PASS = 1;
	public static final int SUBMIT = 2;
	
	private int portID;
	private String name;
	private int move;
	private ArrayList<Card> cards;
	private ArrayList<Card> oldMoveCards;
	private ArrayList<Card> moveCards;
	
	public Player(String n){
		name = n;
		move = NO_MOVE;
		cards = new ArrayList<Card>();
		moveCards = new ArrayList<Card>();
		oldMoveCards = new ArrayList<Card>();
	}
	
	public ArrayList<Card> getCards(){
		return cards;
	}

	public ArrayList<Card> getMoveCards(){
		return moveCards;
	}
	public ArrayList<Card> getOldMoveCards(){
		return oldMoveCards;
	}
	
	public void setOldMoveCards(ArrayList<Card> mC){
		oldMoveCards= mC;
	}
	public void setName(String n){
		name = n;
	}
	public String getName(){
		return name;
	}
	
	public void setPortID( int p){
		portID = p;
	}
	public int getPortID(){
		return portID;
	}
	
	
	public void setMove( int m){
		move = m;
	}
	public int getMove(){
		return move;
	}
}
