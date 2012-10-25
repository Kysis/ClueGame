package experiment;

import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

import junit.framework.Assert;

public class IntBoard {
	private TreeMap<Integer,LinkedList<Integer>> adjacencies;
	private TreeSet<Integer> targets;
	private static int ROWS, COLS, GRID_PIECES;
	private boolean[] visited;
	public IntBoard() {
		super();
		ROWS=4;
		COLS=4;
		GRID_PIECES=ROWS*COLS;
		visited = new boolean[GRID_PIECES];
	}
	
	public void calcAdjacencies() {
		adjacencies = new TreeMap<Integer,LinkedList<Integer>>();
		// Loop through all the grid pieces
		for(int i=0; i<GRID_PIECES; i++) {
			LinkedList<Integer> adjList = new LinkedList<Integer>();
			// Checking the square that is directly above the node is valid in the board
			if(i-COLS>=0) {
				adjList.add(i-COLS);
			}
			// Checking the square that is directly below the node is valid in the board
			if(i+COLS<GRID_PIECES) {
				adjList.add(i+COLS);
			}
			// Checking the square that is directly to the right of the node is valid in the board
			if( (i+1)%COLS!=0 ) {
				adjList.add(i+1);
			}
			// Checking the square that is directly to the left of the node is valid in the board
			if( (i-1)%COLS!=COLS-1 && (i-1)>=0) {
				adjList.add(i-1);
			}
			adjacencies.put(i, adjList);
		}
	}
	public TreeSet<Integer> f( int start, int steps) {
		TreeSet<Integer> tempList = new TreeSet<Integer>();
		visited[start] = true;
		if(steps == 0){
			tempList.add(start);
		}else{
			for(int adj : adjacencies.get(start)){
				if(!visited[adj]){
					tempList.addAll(f(adj,steps-1));
					visited[adj] = false;
				}
			}
		}
		return tempList;
	}
	
	public void calcTargets (int start, int steps) {
		targets=new TreeSet<Integer>();
		targets=f(start,steps);
	}
	
	public TreeSet<Integer> getTargets() {
		return targets;
	}
	
	public LinkedList<Integer> getAdjList(int index) {
		return adjacencies.get(index);
	}
	
	public int calcIndex(int row, int col) {
		return row*COLS+col;
	}
}
