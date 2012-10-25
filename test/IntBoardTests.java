package test;

import java.util.HashSet;
import java.util.LinkedList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import experiment.IntBoard;

public class IntBoardTests {
	private IntBoard board;
	@Before
	public void initialize() {
		board = new IntBoard();
	}	
	
	@Test
	public void calcIndexTest() {
		//Tests corners and then 2 points in middle (so all rows and columns are tested)
		Assert.assertEquals(board.calcIndex(0,0), 0);
		Assert.assertEquals(board.calcIndex(0,3), 3);
		Assert.assertEquals(board.calcIndex(3,0), 12);
		Assert.assertEquals(board.calcIndex(3,3), 15);
		Assert.assertEquals(board.calcIndex(1,2), 6);
		Assert.assertEquals(board.calcIndex(2,1), 9);
	}
	
	@Test
	public void adjListTest0() {
		board.calcAdjacencies();
		
		// Look at size of adj list at different index values
		Assert.assertEquals(board.getAdjList(0).size(),2);		
		// Look at contents of adj list at different index values
		Assert.assertTrue(board.getAdjList(0).contains(1));
		Assert.assertTrue(board.getAdjList(0).contains(4));
	}
	@Test
	public void adjListTest15() {
		board.calcAdjacencies();
		
		// Look at size of adj list at different index values
		Assert.assertEquals(board.getAdjList(15).size(),2);
		// Look at contents of adj list at different index values
		Assert.assertTrue(board.getAdjList(15).contains(14));
		Assert.assertTrue(board.getAdjList(15).contains(11));
	}
	@Test
	public void adjListTest7() {
		board.calcAdjacencies();
		
		// Look at size of adj list at different index values
		Assert.assertEquals(board.getAdjList(7).size(),3);
		// Look at contents of adj list at different index values
		Assert.assertTrue(board.getAdjList(7).contains(3));
		Assert.assertTrue(board.getAdjList(7).contains(6));
		Assert.assertTrue(board.getAdjList(7).contains(11));
	}
	@Test
	public void adjListTest8() {
		board.calcAdjacencies();
		
		// Look at size of adj list at different index values
		Assert.assertEquals(board.getAdjList(8).size(),3);
		// Look at contents of adj list at different index values
		Assert.assertTrue(board.getAdjList(8).contains(4));
		Assert.assertTrue(board.getAdjList(8).contains(9));
		Assert.assertTrue(board.getAdjList(8).contains(12));
	}
	@Test
	public void adjListTest5() {
		board.calcAdjacencies();
		
		// Look at size of adj list at different index values
		Assert.assertEquals(board.getAdjList(5).size(),4);
		// Look at contents of adj list at different index values
		Assert.assertTrue(board.getAdjList(5).contains(1));
		Assert.assertTrue(board.getAdjList(5).contains(4));
		Assert.assertTrue(board.getAdjList(5).contains(6));
		Assert.assertTrue(board.getAdjList(5).contains(9));
	}
	@Test
	public void adjListTest10() {
		board.calcAdjacencies();
		
		// Look at size of adj list at different index values
		Assert.assertEquals(board.getAdjList(10).size(),4);
		// Look at contents of adj list at different index values
		Assert.assertTrue(board.getAdjList(10).contains(6));
		Assert.assertTrue(board.getAdjList(10).contains(9));
		Assert.assertTrue(board.getAdjList(10).contains(11));
		Assert.assertTrue(board.getAdjList(10).contains(14));
	}
	
	
	@Test
	public void pathTest0_1() {
		board.calcAdjacencies();
		board.calcTargets(0, 1);
		
		Assert.assertEquals(board.getTargets().size(),2);
		Assert.assertTrue(board.getTargets().contains(1));
		Assert.assertTrue(board.getTargets().contains(4));
	}
	@Test
	public void pathTest3_2() {
		board.calcAdjacencies();
		board.calcTargets(3, 2);
		
		Assert.assertEquals(board.getTargets().size(),3);
		Assert.assertTrue(board.getTargets().contains(1));
		Assert.assertTrue(board.getTargets().contains(6));
		Assert.assertTrue(board.getTargets().contains(11));
	}
	@Test
	public void pathTest12_3() {
		board.calcAdjacencies();
		board.calcTargets(12, 3);
		
		Assert.assertEquals(board.getTargets().size(),6);
		Assert.assertTrue(board.getTargets().contains(0));
		Assert.assertTrue(board.getTargets().contains(5));
		Assert.assertTrue(board.getTargets().contains(8));
		Assert.assertTrue(board.getTargets().contains(10));
		Assert.assertTrue(board.getTargets().contains(13));
		Assert.assertTrue(board.getTargets().contains(15));
	}
	@Test
	public void pathTest15_4() {
		board.calcAdjacencies();
		board.calcTargets(15, 4);
		
		Assert.assertEquals(board.getTargets().size(),6);
		Assert.assertTrue(board.getTargets().contains(2));
		Assert.assertTrue(board.getTargets().contains(5));
		Assert.assertTrue(board.getTargets().contains(7));
		Assert.assertTrue(board.getTargets().contains(8));
		Assert.assertTrue(board.getTargets().contains(13));
		Assert.assertTrue(board.getTargets().contains(10));
	}
	@Test
	public void pathTest9_5() {
		board.calcAdjacencies();
		board.calcTargets(9, 5);
		
		Assert.assertEquals(board.getTargets().size(),8);
		Assert.assertTrue(board.getTargets().contains(8));
		Assert.assertTrue(board.getTargets().contains(13));
		Assert.assertTrue(board.getTargets().contains(5));
		Assert.assertTrue(board.getTargets().contains(10));
		Assert.assertTrue(board.getTargets().contains(0));
		Assert.assertTrue(board.getTargets().contains(15));
		Assert.assertTrue(board.getTargets().contains(2));
		Assert.assertTrue(board.getTargets().contains(7));
	}
	@Test
	public void pathTest6_6() {
		board.calcAdjacencies();
		board.calcTargets(6, 6);
		
		Assert.assertEquals(board.getTargets().size(),7);
		Assert.assertTrue(board.getTargets().contains(4));
		Assert.assertTrue(board.getTargets().contains(14));
		Assert.assertTrue(board.getTargets().contains(1));
		Assert.assertTrue(board.getTargets().contains(11));
		Assert.assertTrue(board.getTargets().contains(9));
		Assert.assertTrue(board.getTargets().contains(12));
		Assert.assertTrue(board.getTargets().contains(3));
	}
}
