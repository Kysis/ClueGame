package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.Card.Type;
import clueGame.Player;

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
	public static void setUp() throws BadConfigFormatException {
		board = new Board("Legend","BoardLayout.csv", "players.txt", "cards.txt");
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
		int totalCards=0;
		totalCards+=board.getHuman().getCards().size();
		for (int i=0; i<board.getComputers().size(); i++){
			totalCards=board.getComputer(i).getCards().size();
		}
		assert(totalCards==board.getDeck().size());
		int human=board.getHuman().getCards().size();
		int comp1=board.getComputer(0).getCards().size();
		int comp2=board.getComputer(1).getCards().size();
		int comp3=board.getComputer(2).getCards().size();
		int comp4=board.getComputer(3).getCards().size();
		int comp5=board.getComputer(4).getCards().size();
		//Each of these tests that the size of the hands is equal + or - 1
		assert(human==comp1+1 || human==comp1-1 || human==comp1);
		assert(comp1==comp2+1 || comp1==comp2-1 || comp1==comp2);
		assert(comp2==comp3+1 || comp2==comp3-1 || comp2==comp3);
		assert(comp3==comp4+1 || comp3==comp4-1 || comp3==comp4);
		assert(comp4==comp5+1 || comp4==comp5-1 || comp4==comp5);
		//Create a tempList to hold all the players
		ArrayList<Player> tempList = new ArrayList<Player>();
		//Add all the players
		tempList.add(board.getHuman());
		tempList.addAll(board.getComputers());
		Player tempPlayer1, tempPlayer2;
		for (int i=0; i<tempList.size()-1; i++){
			//Start at the first element
			tempPlayer1=tempList.get(i);
			for (int j=i+1; j<tempList.size(); j++){
				//Check that every other element doesn't contain any cards
				tempPlayer2=tempList.get(j);
				assertFalse(tempPlayer1.getCards().containsAll(tempPlayer2.getCards()));
			}
		}
	}
}
