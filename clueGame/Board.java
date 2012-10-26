package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.RoomCell.DoorDirection;

public class Board {
	//Map<String,String> legendMap = new HashMap<String,String>();
	private ArrayList<BoardCell> cells;
	private Map<Character,String> rooms;
	private int numRows;
	private int numCols;
	private Map<Integer,LinkedList<Integer>> adjacencies;
	private Set<BoardCell> targets;
	private int gridPieces;
	private boolean[] visited;
	private ArrayList<ComputerPlayer> computers;
	private HumanPlayer human;
	private ArrayList<Card> deck;
	private String currentPlayer;
	
	public Player getComputer(int index){
		return computers.get(index);
	}
	
	public Player getHuman(){
		return human;
	}
	
	public ArrayList<Card> getDeck(){
		return deck;
	}
	
	public Board(String legendFile, String configFile, String playerFile, String cardFile) {
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character,String>();
		loadConfigFiles(legendFile,configFile);
		gridPieces = numRows*numCols;
		calcAdjacencies();
	}
	public void loadConfigFiles(String legendFile, String configFile) {
		try{
			loadLegend(legendFile);
			loadBoard(configFile);
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}
	public void loadLegend(String file) throws BadConfigFormatException {
		try{
			FileReader reader = new FileReader(file);
			Scanner scan = new Scanner(reader);
			while(scan.hasNext()) {
				String legendLine = scan.nextLine();
				String[] legendData = legendLine.split(",");
				if(legendData.length!=2) {
					throw new BadConfigFormatException("Legend does not map character to name");
				} else {
					rooms.put(legendData[0].charAt(0), legendData[1].trim());
				}
			}			
		} catch (FileNotFoundException e) {
			System.out.println("Legend file not found");
		}
	}
	public void loadBoard(String file) throws BadConfigFormatException {
		try{
			int curRow = 0;
			FileReader reader = new FileReader(file);
			Scanner scan = new Scanner(reader);
			if(scan.hasNext()){
				String boardLine = scan.nextLine();
				String[] boardData = boardLine.split(",");
				numCols = boardData.length;
				int curCol = 0;
				for(String s:boardData) {
					//System.out.print(s+" ");
					if(s.equalsIgnoreCase("W")) {
						cells.add(new WalkwayCell(curRow,curCol));
					} else {
						cells.add(new RoomCell(curRow,curCol,s));
					}
					curCol++;
				}
				//System.out.println("");
				curRow++;
			}
			while(scan.hasNext()) {
				String boardLine = scan.nextLine();
				String[] boardData = boardLine.split(",");
				
				if(boardData.length!=numCols) {
					throw new BadConfigFormatException("Inconsistent board length");
				} else {
					int curCol = 0;
					for(String s:boardData) {
						//System.out.print(s+" ");
						if(s.equalsIgnoreCase("W")) {
							cells.add(new WalkwayCell(curRow,curCol));
						} else {
							cells.add(new RoomCell(curRow,curCol,s));
						}
						curCol++;
					}
					//System.out.println("");
					curRow++;
				}
			}
			System.out.println();
			System.out.println();
			numRows = curRow;
		} catch (FileNotFoundException e) {
			System.out.println("Board file not found");
		}
	}
	
	public int calcIndex(int row, int col) {
		return row*numCols+col;
	}
	
	public RoomCell getRoomCellAt(int row, int col) {
		BoardCell tempCell = cells.get(calcIndex(row,col));
		if(tempCell instanceof RoomCell) {
			return (RoomCell) tempCell;
		} else {
			return null;
		}
	}
	
	public BoardCell getCellAt(int index) {
		return cells.get(index);
	}
	
	public ArrayList<BoardCell> getCells() {
		return cells;
	}
	public Map<Character, String> getRooms() {
		return rooms;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumCols() {
		return numCols;
	}
	public boolean isAdjacent(int index, DoorDirection direction) {
		BoardCell temp = getCellAt(index);
		if(temp.isWalkway()) {
			return true;
		} else if(((RoomCell)temp).getDoorDirection() == direction){
			return true;
		} else{
			return false;
		}			
	}
	public void calcAdjacencies() {
		adjacencies = new HashMap<Integer,LinkedList<Integer>>();
		// Loop through all the grid pieces
		for(int i=0; i<gridPieces; i++) {
			LinkedList<Integer> adjList = new LinkedList<Integer>();
			// Checking the square that is directly above the node is valid in the board
			BoardCell tempCell = getCellAt(i);
			if(tempCell.isWalkway()) {
				if(i-numCols>=0) {
					if(isAdjacent(i-numCols,DoorDirection.DOWN)) {
						adjList.add(i-numCols);
					}
				}
				// Checking the square that is directly below the node is valid in the board
				if(i+numCols<gridPieces) {
					if(isAdjacent(i+numCols,DoorDirection.UP)) {
						adjList.add(i+numCols);
					}
				}
				// Checking the square that is directly to the right of the node is valid in the board
				if( (i+1)%numCols!=0 ) {
					if(isAdjacent(i+1,DoorDirection.LEFT)) {
						adjList.add(i+1);
					}
				}
				// Checking the square that is directly to the left of the node is valid in the board
				if( (i-1)%numCols!=numCols-1 && (i-1)>=0) {
					if(isAdjacent(i-1,DoorDirection.RIGHT)) {
						adjList.add(i-1);
					}
				}
			} else if(tempCell.isDoorway()) {
				switch( ((RoomCell)tempCell).getDoorDirection() ) {
				case UP: adjList.add(i-numCols); break;
				case DOWN: adjList.add(i+numCols); break;
				case RIGHT: adjList.add(i+1); break;
				case LEFT: adjList.add(i-1); break;
				case NONE: break;
				}
			}
			adjacencies.put(i, adjList);
		}
	}
	
	public Set<BoardCell> recurseTargets( int start, int steps,boolean begin) {
		Set<BoardCell> tempList = new HashSet<BoardCell>();
		visited[start] = true;
		if(steps == 0 || (getCellAt(start).isDoorway() && !begin)){
			tempList.add(getCellAt(start));
		}else{
			for(int adj : adjacencies.get(start)){
				if(!visited[adj]){
					tempList.addAll(recurseTargets(adj,steps-1,false));
					visited[adj] = false;
				}
			}
		}
		return tempList;
	}
	
	public void calcTargets (int start, int steps) {
		targets=new HashSet<BoardCell>();
		visited = new boolean[gridPieces];
		targets=recurseTargets(start,steps,true);
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public LinkedList<Integer> getAdjList(int index) {
		return adjacencies.get(index);
	}
	
	/*public void printCells() {
		int cellCount=0;
		for(BoardCell cell:cells) {
			cell.draw();
			if(cellCount==numCols-1) {
				System.out.println();
				cellCount=0;
			} else {
				cellCount++;
			}			
		}
	}
	public void printLegend() {
		Set<Character> keys = rooms.keySet();
		for(char c:keys) {
			System.out.println(c+" "+rooms.get(c));
		}
	}
	public void printTargets() {
		for(BoardCell b:targets) {
			System.out.println(calcIndex(b.row,b.col));
		}
	}
	public static void main(String[] args) {
		Board board = new Board("Legend","BoardLayout.csv");
		for( int i: board.getAdjList(board.calcIndex(5, 1)) ) {
			System.out.println(i);			
		}		
		//board.calcTargets(board.calcIndex(5, 2), 2);
		//Set<BoardCell> targets= board.getTargets();
		//board.printTargets();
	}*/
}
