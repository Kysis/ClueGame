package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;

public class PlayerTest {
	private static Board board;
	private static Card weapon;
	private static Card room;
	private static Card person;
	private static Card weapon2;
	private static Card room2;
	private static Card person2;
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
	
	
}
