package controller;

import javax.swing.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class BigTwoGame
{
	public final static int CLUB = 0;
	public final static int SPADE = 1;
	public final static int HEART = 2;
	public final static int DIAMOND = 3;
	
	public final static int SINGLE = 4;
	public final static int PAIR = 5;
	public final static int TRIPLE = 6;
	public final static int STRAIGHT = 7;
	public final static int FLUSH = 8;
	public final static int FULL_HOUSE = 9;
	public final static int FOUR_OF_A_KIND = 10;
	public final static int STRAIGHT_FLUSH = 15;
	
	public ArrayList<Integer> deck;
	public ArrayList<ArrayList<Integer>> playerCards;
	public ArrayList<Integer> move;
	public ArrayList<Integer> highestCardCombo;
	public int highestPlayer;
	public int currentPlayer;
	public int cardSize;
	public int numOfPlayers;
	public boolean firstMove;
	public Scanner input = new Scanner( System.in );
	
	public BigTwoGame( int players, int size )
	{
		this.numOfPlayers = players;
		if( players == 3 ) {
			if( size == 13 || size == 17 )
				cardSize = size;
			else
				System.exit( 2 );
		}
		else if( players == 4 )
			cardSize = 13;
		else
			System.exit( 1 );
		
		int firstPlayer;
		do {
			playerCards = new ArrayList<ArrayList<Integer>>();
			shuffleCards();
			dealCards( players );
			firstPlayer = findPlayerWithLowestCard();
		} while( firstPlayer < 0 );
		System.out.println( "FIRST PLAYER: " + (firstPlayer+1) );
		currentPlayer = firstPlayer;
		highestPlayer = currentPlayer;
		firstMove = true;
		highestCardCombo = new ArrayList<Integer>();
		
		while( getWinner() < 0 )
		{
			move = getMove(currentPlayer);
			
			if( move.size() > 0 )
			{
				if( currentPlayer == highestPlayer || isValidMove( move, highestCardCombo, firstMove ) ) {
					if( firstMove || currentPlayer == highestPlayer || (compareMoves( move, highestCardCombo ) ) )
					{
						highestCardCombo = move;
						highestPlayer = currentPlayer;
						removeFromHand( currentPlayer, move );
						currentPlayer = (currentPlayer+1) % numOfPlayers;
						firstMove = false;
					}
					else
						System.out.println( "Move is invalid! Try a legal combination or pass." );
				}
				else
					System.out.println( "Move is invalid! Try a legal combination or pass." );
			}
			else
			{
				if( currentPlayer != highestPlayer ) {
					currentPlayer = (currentPlayer+1) % numOfPlayers;
				}
				else
					System.out.println( "Move is invalid! Try a legal combination." );
			}
		}
		System.out.println( "WINNER: PLAYER " + (getWinner()+1) );
	}
	
	public void shuffleCards()
	{
		int card;
		deck = new ArrayList<Integer>();
		while( deck.size() < 52 )
		{
			card = (int)Math.round(Math.random()*51);
			if( isUnique( card ) )
				deck.add( card );
		}
		System.out.println( "CARDS" );
		printCards();
		return;
	}

	public void printCards()
	{
		for( int i = 0; i < 52; i++ )
		{
			int card = deck.get(i);
			if( card < 10 )
				System.out.print( "0" );
			System.out.print( card + " " );
			if( i % 10 == 9 )
				System.out.println( "" );
		}
		return;
	}

	public void dealCards( int players )
	{
		for( int i = 0; i < players; i++ )  {
			ArrayList<Integer> tempCards = new ArrayList<Integer>();
			for( int j = 0; j < cardSize; j++ ) {
				tempCards.add( deck.get(j*players+i) );
			}
			playerCards.add( sortHand(tempCards) );
			printHand( i );
		}
		System.out.println();
	}
	
	public boolean isUnique( int card )
	{	
		for( int i = 0; i < deck.size(); i++ )
		{
			if( deck.get(i) == card )
				return false;
		}
		return true;
	}
	
	public int findPlayerWithLowestCard()
	{
		for( int i = 0; i < numOfPlayers; i++ )
		{
			for( int j = 0; j < cardSize; j++ ) {
				if( playerCards.get(i).get(j) == 2 )
					return i;
			}
		}
		return -1;
	}
	
	public int getWinner()
	{
		for( int i = 0; i < numOfPlayers; i++ )
		{
			if( playerCards.get(i).size() == 0 )
				return i;
		}
		return -1;
	}
	
	public ArrayList<Integer> getMove( int player )
	{
		int num;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		printHand( player );
		do {
			System.out.print( "Enter a card: " );
			num = input.nextInt();
			if( num != -1 && isInHand( player, num ) && notInMove( temp, num ) )
				temp.add( num );
			else if( num != -1 )
				System.out.println( "Try a card that is in your hand and not yet on your move." );
		} while( num != -1 );
		return sortHand(temp);
	}

	public void printHand( int player )
	{
		System.out.println( "\nPLAYER " + (player+1) + " CARDS:" );
		for( int i = 0; i < playerCards.get(player).size(); i++ )
		{
			int card = getRank(playerCards.get(player).get(i)) + 1;
			if( card < 10 )
				System.out.print( "0" );
			System.out.print( card + "-" + (getSuit(playerCards.get(player).get(i))+1) + "(" + ((card-1)+13*getSuit(playerCards.get(player).get(i))) + ") " );
			if( i % 5 == 4 )
				System.out.println( "" );
		}
		System.out.println();
		return;
	}
	
	public boolean isInHand( int player, int card )
	{
		return ( playerCards.get(player).indexOf( card ) >= 0 );
	}
	
	public boolean notInMove( ArrayList<Integer> move, int card )
	{
		if( move.size() > 0 && move.indexOf( card ) >= 0 )
			return false;
		else
			return true;
	}
	
	public void removeFromHand( int player, ArrayList<Integer> move )
	{
		playerCards.get(player).removeAll(move);
		return;
	}
	
	public boolean isValidMove( ArrayList<Integer> move, ArrayList<Integer> highestCardCombo, boolean firstMove )
	{
		int moveType, highComboType;
		moveType = getType( move );
		if( firstMove )
		{
			if( !notInMove( move, 2 ) && moveType != -1 )
				return true;
			else
				return false;
		}
		else
		{
			if( move.size() == highestCardCombo.size() )
			{
				highComboType = getType( highestCardCombo );
				if( highComboType <= moveType )
					return true;
				else
					return false;
			}
			else
				return false;
		}
	}

	public boolean compareMoves( ArrayList<Integer> move, ArrayList<Integer> highestCardCombo )
	{
		int moveType = getType( move );
		int highType = getType( highestCardCombo );
		if( moveType > highType && highType > TRIPLE )
			return true;
		else
		{
			if( moveType == SINGLE )
			{
				if( getRealValue( move.get(0) ) > getRealValue( highestCardCombo.get(0) ) )
					return true;
				else
					return false;
			}
			else if( moveType == PAIR )
			{
				if( getRealValue( move.get(1) ) > getRealValue( highestCardCombo.get(1) ) )
					return true;
				else
					return false;
			}
			else if( moveType == TRIPLE )
			{
				if( getRealValue( move.get(0) ) > getRealValue( highestCardCombo.get(0) ) )
					return true;
				else
					return false;
			}
			else if( moveType == STRAIGHT || moveType == STRAIGHT_FLUSH )
			{
				int moveHighCard, highComboHighCard;
				ArrayList<Integer> tempMove = sortCard( move );
				ArrayList<Integer> tempHigh = sortCard( highestCardCombo );
				if( getRank(tempMove.get(0)) == 0 )
				{
					if( getRank(tempMove.get(4)) == 4 )
						moveHighCard = tempMove.get(4);
					else
						moveHighCard = tempMove.get(0);
				}
				else
					moveHighCard = tempMove.get(4);
				if( getRank(tempHigh.get(0)) == 0 )
				{
					if( getRank(tempHigh.get(4)) == 4 )
						highComboHighCard = tempHigh.get(4);
					else
						highComboHighCard = tempHigh.get(0);
				}
				else
					highComboHighCard = tempHigh.get(4);
					
				if( getRealValue(moveHighCard) > getRealValue(highComboHighCard) )
					return true;
				else
					return false;
			}
			else if( moveType == FLUSH )
			{
				if( getRealValue( move.get(4) ) > getRealValue( highestCardCombo.get(4) ) )
					return true;
				else
					return false;
			}
			else if( moveType == FULL_HOUSE )
			{
				int middleCard1, middleCard2, firstCard1, firstCard2, lastCard1, lastCard2, tripleCard1, tripleCard2;
				
				firstCard1 = move.get(0);
				middleCard1 = move.get(2);
				lastCard1 = move.get(4);
				tripleCard1 = (middleCard1 == firstCard1 ? firstCard1 : lastCard1 );firstCard2 = highestCardCombo.get(0);
				middleCard2 = highestCardCombo.get(2);
				lastCard2 = highestCardCombo.get(4);
				tripleCard2 = (middleCard2 == firstCard2 ? firstCard2 : lastCard2 );
				if( tripleCard1 > tripleCard2 )
					return true;
				else
					return false;
			}
			else if( moveType == FOUR_OF_A_KIND )
			{
				if( getRank( move.get(2) ) > getRank( highestCardCombo.get(2) ) )
					return true;
				else
					return false;
			}
			else
				return false;
		}
	}
	
	public int getType( ArrayList<Integer> move )
	{
		if( move.size() == 1 )
			return SINGLE;
		else if( move.size() == 2 )
		{
			if( getRank(move.get(0)) == getRank(move.get(1)) )
				return PAIR;
			else
				return -1;
		}
		else if( move.size() == 3 )
		{
			if( getRank(move.get(0)) == getRank(move.get(1)) && getRank(move.get(1)) == getRank(move.get(2)) )
				return TRIPLE;
			else
				return -1;
		}
		else if( move.size() == 4 )
		{
			return -1;
		}
		else if( move.size() == 5 )
		{
			if( checkIfFlush( move ) )
			{
				if( checkIfStraight( move ) )
					return STRAIGHT_FLUSH;
				else
					return FLUSH;
			}
			else
			{
				if( checkIfStraight( move ) )
					return STRAIGHT;
				else if( checkIfFullHouse( move ) )
					return FULL_HOUSE;
				else if( checkIfFourofaKind( move ) )
					return FOUR_OF_A_KIND;
				else
					return -1;
			}
		}
		else
		{
			return -1;
		}
	}
	
	public int getRank( int card )
	{
		return (card % 13);
	}
	
	public int getSuit( int card )
	{
		return (card / 13);
	}
	
	public int getRealValue( int card )
	{
		return (4*(getRank(getRank(card)+11))+getSuit(card));
	}
	
	public int getOriginalCard( int realValue )
	{
		return ((realValue%4)*13+getRank((realValue/4)+2));
	}
	
	public boolean checkIfStraight( ArrayList<Integer> temp )
	{
		ArrayList<Integer> hand = new ArrayList<Integer>();
		for( int card : temp )
		{
			hand.add( getRank( card ) );
		}
		hand = sortCard( hand );
		
		if( hand.get(0) == (hand.get(1) - 1) && hand.get(1) == (hand.get(2) - 1) && hand.get(2) == (hand.get(3) - 1) &&  hand.get(3) == (hand.get(4) - 1) )
			return true;
		else if( hand.get(0) == 0 && hand.get(1) == 9 && hand.get(2) == 10 && hand.get(3) == 11 && hand.get(4) == 12 )
			return true;
		else
			return false;
	}
	
	public boolean checkIfFlush( ArrayList<Integer> temp )
	{
		int cardRank = getSuit( temp.get(0) );
		for( int i = 1; i < temp.size(); i++ )
		{
			if( cardRank != getSuit(temp.get(i)) )
				return false;
		}
		return true;
	}
	
	public boolean checkIfFullHouse( ArrayList<Integer> temp )
	{
		if( getRank( temp.get(0) ) == getRank( temp.get(1) ) )
		{
			if( getRank( temp.get(1) ) == getRank( temp.get(2) ) && getRank( temp.get(3) ) == getRank( temp.get(4) ) )
				return true;
			else if( getRank( temp.get(2) ) == getRank( temp.get(3) ) && getRank( temp.get(3) ) == getRank( temp.get(4) ) )
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	public boolean checkIfFourofaKind( ArrayList<Integer> temp )
	{
		if( getRank( temp.get(0) ) == getRank( temp.get(1) ) && getRank( temp.get(1) ) == getRank( temp.get(2) ) && getRank( temp.get(2) ) == getRank( temp.get(3) ) )
			return true;
		else if( getRank( temp.get(1) ) == getRank( temp.get(2) ) && getRank( temp.get(2) ) == getRank( temp.get(3) ) && getRank( temp.get(3) ) == getRank( temp.get(4) ) )
			return true;
		else
			return false;
	}
	
	public ArrayList<Integer> sortCard( ArrayList<Integer> move )
	{
		ArrayList<Integer> temp = move;
		Collections.sort( temp );
		return temp;
	}
	
	public ArrayList<Integer> sortHand( ArrayList<Integer> hand )
	{
		ArrayList<Integer> temp = new ArrayList<Integer>();
		ArrayList<Integer> temp2 = new ArrayList<Integer>();
		for( int card: hand )
			temp.add(getRealValue( card ));
		temp = sortCard( temp );
		for( int card2: temp )
			temp2.add(getOriginalCard( card2 ));
		return temp2;
	}
	
	public static void main( String [] args )
	{
		if( args.length == 2 )
			new BigTwoGame( Integer.parseInt( args[0] ), Integer.parseInt( args[1] ) );
		else if( args.length == 1 )
			new BigTwoGame( Integer.parseInt( args[0] ), 17 );
	}
}