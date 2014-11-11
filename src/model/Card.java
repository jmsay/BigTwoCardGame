package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Card implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6199652170350360542L;
	/**
	 * 
	 */
	

	public final static int CLUB = 0;
	public final static int SPADE = 1;
	public final static int HEART = 2;
	public final static int DIAMOND = 3;
	

	public final static int FACE_DOWN = 0;
	public final static int FACE_UP = 1;
	
	public final static int NOT_SELECTED = 0;
	public final static int SELECTED = 1;

	
	private int position;
	private int deckCardNumber;
	private int status;
	private int number;
	private int type;
	private int x,y;
		
	public Card(int t, int n, int dCN){
		type = t;
		number = n;
		deckCardNumber = dCN;
		position = FACE_DOWN;
		status = NOT_SELECTED;
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.defaultWriteObject();
	}
    
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		in.defaultReadObject();
	}
	
	public void setPosition(int p){
		position = p;
	}
	
	
	public int getType(){
		return type;
	}
	public int getNumber(){
		return number;
	}

	public int getDeckCardNumber() {
		return deckCardNumber;
	}
	
	public int getPosition(){
		return position;
	}
	
	public int getStatus(){
		return status;
	}

	public void setStatus(int s){
		status = s;
	}
	
	public int getX(){
		return x;
	}

	public void setX(int x){
		this.x = x;
	}
	public int getY(){
		return y;
	}

	public void setY(int y){
		this.y = y;
	}
	
	
}
