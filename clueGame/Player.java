package clueGame;

import java.util.ArrayList;
import java.util.LinkedList;

public class Player {
	private String name;
	private String color;
	private int row, col;
	private ArrayList<Card> cards;
	
	public Card disproveSuggestion(ArrayList<Player> players, Card weapon, Card person, Card room){
		return room;
	}
	
	public void makeAccusation(Card weapon, Card person, Card room){
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
}
