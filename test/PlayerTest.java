package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.Player;

public class PlayerTest {
	private static Board board;
	private static Card weapon;
	private static Card room;
	private static Card person;
	private static Card weapon2;
	private static Card room2;
	private static Card person2;
	private static Card weapon3;
	private static Card room3;
	private static Card person3;
	private static ArrayList<Card> answer;
	
	@BeforeClass
	public static void setUp() {
		board = new Board("Legend","BoardLayout.csv", "Cards", "Players");
		weapon = new Card("Wrench", "weapon");
		room = new Card("Holodeck", "room");
		person = new Card("Miss Scarlet", "person");
		weapon2 = new Card("Gun", "weapon");
		room2 = new Card("Galley", "room");
		person2 = new Card("Colonel Mustard", "person");
		weapon3 = new Card("Kinfe", "weapon");
		room3 = new Card("Bedroom", "room");
		person3 = new Card("Professor Plum", "person");
		answer = new ArrayList<Card>();
		answer.add(weapon);
		answer.add(person);
		answer.add(room);
	}
	
	@Test
	public void testAccusation(){
		//Check a correct accusation
		board.getHuman().makeAccusation(weapon, person, room);
		assertTrue(answer.containsAll(board.getAccusation()));
		//Check a wrong weapon
		board.getHuman().makeAccusation(weapon2, person, room);
		assertFalse(answer.containsAll(board.getAccusation()));
		//Check a wrong person
		board.getHuman().makeAccusation(weapon, person2, room);
		assertFalse(answer.containsAll(board.getAccusation()));
		//Check a wrong room
		board.getHuman().makeAccusation(weapon, person, room2);
		assertFalse(answer.containsAll(board.getAccusation()));
		//Check when all 3 are wrong
		board.getHuman().makeAccusation(weapon2, person2, room2);
		assertFalse(answer.containsAll(board.getAccusation()));
	}
	
	@Test
	public void testLocationPick(){
		//Check that they computer prefers a room
		ComputerPlayer temp = new ComputerPlayer();
		for (int i=0; i<101; i++){
			//Pick 100 different locations and assure they are all rooms
			temp.pickLocation(board);
			assert(board.getCellAt(board.calcIndex(temp.getRow(), temp.getCol())).isRoom());
			//Resets the row and column
			temp.setCol(0);
			temp.setRow(0);
		}
	}
	
	@Test
	public void testLocationPickRandomness(){
		ComputerPlayer temp = new ComputerPlayer();
		board.calcTargets(board.calcIndex(38, 0), 3);
		int loc38_3 = 0;
		int loc39_2 = 0;
		int loc40_1 = 0;
		for (int i=0; i<100; i++){
			temp.pickLocation(board);
			BoardCell chosen = board.getCellAt(board.calcIndex(temp.getRow(), temp.getCol()));
			if (chosen==board.getCellAt(board.calcIndex(38, 3))){
				loc38_3++;
			} else if (chosen==board.getCellAt(board.calcIndex(39, 2))){
				loc39_2++;
			} else if (chosen==board.getCellAt(board.calcIndex(40, 1))){
				loc40_1++;
			} else {
				fail("Bad target selected");
			}
		}
		assertEquals(100, loc38_3+loc39_2+loc40_1);
		assertTrue(loc38_3>10);
		assertTrue(loc39_2>10);
		assertTrue(loc40_1>10);
	}
	
	@Test
	public void testDisproveSuggestion(){
		Player temp = new Player();
		ArrayList<Card> hand = new ArrayList<Card> ();
		hand.add(person);
		hand.add(person2);
		hand.add(weapon);
		hand.add(weapon2);
		hand.add(room);
		hand.add(room2);
		assertEquals(person, temp.disproveSuggestion(weapon3, person, room3));
		assertEquals(weapon, temp.disproveSuggestion(weapon, person3, room3));
		assertEquals(room, temp.disproveSuggestion(weapon3, person3, room));
		assertEquals(null, temp.disproveSuggestion(weapon3, person3, room3));
	}
	
	@Test
	public void testMultipleSuggestion(){
		Player temp = new Player();
		Card tempCard;
		ArrayList<Card> hand = new ArrayList<Card> ();
		//Deal the hand
		hand.add(person);
		hand.add(person2);
		hand.add(weapon);
		hand.add(weapon2);
		hand.add(room);
		hand.add(room2);
		int num_weapon = 0;
		int num_person = 0;
		int num_room = 0;
		for (int i=0; i<100; i++){
			tempCard=temp.disproveSuggestion(weapon, person, room);
			if (tempCard==weapon){
				num_weapon++;
			} else if (tempCard==person){
				num_person++;
			} else if (tempCard==room){
				num_room++;
			} else {
				fail("Returned a card outside the suggestion.");
			}
		}
		assertTrue(num_weapon>0 && num_person>0 && num_room>0);
	}
	
	
}
