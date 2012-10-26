package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.Card.Type;

public class GameSetUpTest {
	private static Board board;
	public static final int NUM_WEAPONS = 9;
	public static final int NUM_ROOMS = 9;
	public static final int NUM_PEOPLE = 6;
	public static ArrayList<Card> cards;
	public static Card weapon;
	public static Card room;
	public static Card person;
	
	@BeforeClass
	public static void setUp() {
		board = new Board("Legend","BoardLayout.csv", "Cards", "Players");
		weapon = new Card("Wrench", "weapon");
		room = new Card("Holodeck", "room");
		person = new Card("Miss Scarlet", "person");
	}
	
	@Test
	public void testLoadPeople(){
		assert(board.getHuman().getName().equals("Colonel Mustard"));
		assert(board.getHuman().getColor().equalsIgnoreCase("Yellow"));
		assert(board.getHuman().getRow()==43 && board.getHuman().getCol()==3);
		assert(board.getComputer(0).getName().equals("Miss Scarlet"));
		assert(board.getComputer(0).getColor().equalsIgnoreCase("Red"));
		assert(board.getComputer(0).getRow()==11 && board.getHuman().getCol()==0);
		assert(board.getComputer(4).getName().equals("Professor Plum"));
		assert(board.getComputer(4).getColor().equalsIgnoreCase("Purple"));
		assert(board.getComputer(4).getRow()==3 && board.getHuman().getCol()==8);
	}
	
	@Test
	public void testLoadCard(){
		cards=board.getDeck();
		assert(cards.size()==24);
		int numWeapons=0;
		int numRooms=0;
		int numPeople=0;
		for (int i=0; i<board.getDeck().size(); i++){
			switch (cards.get(i).getType()){
			case WEAPON: numWeapons++;
			case ROOM: numRooms++;
			case PERSON: numPeople++;
			}
		}
		assert(numWeapons==NUM_WEAPONS && numRooms==NUM_ROOMS && numPeople==NUM_PEOPLE);
		assert(cards.contains((Card)person));
		assert(cards.contains((Card)room));
		assert(cards.contains((Card)weapon));
	}
	
	@Test
	public void testDeal(){
		
	}
}
