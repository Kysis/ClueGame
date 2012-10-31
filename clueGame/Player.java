package clueGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Player {
	private String name;
	private String color;
	private int row, col;
	private ArrayList<Card> cards;
	private ArrayList<Card> accuse;
	
	public Player(String n, String c, int r, int co) {
		this.name = name;
		this.row = r;
		this.col = co;
		this.color = c;
		cards = new ArrayList<Card>();
		accuse = new ArrayList<Card>();
	}
	
	public Card disproveSuggestion(ArrayList<Player> players, Card weapon, Card person, Card room){
		Collections.shuffle(players);
		for(int i = 0; i < players.size(); ++ i) {
			ArrayList<Card> temp = new ArrayList<Card>();
			temp = players.get(i).getCards();
			for(int j = 0; j < temp.size(); ++ j) {
				if(temp.get(j).equals(weapon)) {
					return weapon;
				} else if(temp.get(j).equals(person)) {
					return person;
				} else if(temp.get(j).equals(room)) {
					return room;
				}
			}
		}
		return null;
	}
	
	public void makeAccusation(Card weapon, Card person, Card room){
		accuse = new ArrayList<Card>();
		accuse.add(weapon);
		accuse.add(person);
		accuse.add(room);
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
	
	public void addCard(Card c) {
		cards.add(c);
	}
	
	public ArrayList<Card> getAccusation() {
		return accuse;
	}
}
