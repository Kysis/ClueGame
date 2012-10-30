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
	private ArrayList<Card> accusation;
	
	public ArrayList<Card> getAccusation(){
		return this.accusation;
	}
	
	public void setAccusation(ArrayList<Card> accusation) {
		this.accusation = accusation;
	}

	public ComputerPlayer getComputer(int index){
		return computers.get(index);
	}
	
	public ArrayList<ComputerPlayer> getComputers(){
		return this.computers;
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
		deck = new ArrayList<Card>();
		computers = new ArrayList<ComputerPlayer>();
		loadConfigFiles(legendFile,configFile,playerFile,cardFile);
		gridPieces = numRows*numCols;
		calcAdjacencies();
	}
	public void loadConfigFiles(String legendFile, String configFile, String playerFile, String cardFile) {
		try{
			loadLegend(legendFile);
			loadBoard(configFile);
			loadPlayers(playerFile);
			loadCards(cardFile);
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void loadPlayers(String file) throws BadConfigFormatException {
		try {
			FileReader reader = new FileReader(file);
			Scanner scan = new Scanner(reader);
			String playerLine = scan.nextLine();
			String[]playerData = playerLine.split(",");
			if(playerData.length != 4) {
				throw new BadConfigFormatException("Error in loading players");
			}
			int r = Integer.parseInt(playerData[2]);
			int c = Integer.parseInt(playerData[3]);
			human = new HumanPlayer(playerData[1], playerData[0], r, c);
			while(scan.hasNext()) {
				playerLine = scan.nextLine();
				playerData = playerLine.split(",");
				if(playerData.length != 4) {
					throw new BadConfigFormatException("Error in loading players");
				}
				r = Integer.parseInt(playerData[2]);
				c = Integer.parseInt(playerData[3]);
				ComputerPlayer temp = new ComputerPlayer(playerData[1], playerData[0], r, c);
				computers.add(temp);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Player file not found");
		}
	}
	
	public void loadCards(String file) throws BadConfigFormatException {
		try {
			FileReader reader = new FileReader(file);
			Scanner scan = new Scanner(reader);
			while(scan.hasNext()) {
				String cardLine = scan.nextLine();
				String[] cardData = cardLine.split(",");
				Card temp = new Card(cardData[1], cardData[0]);
				deck.add(temp);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Card file not found");
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
					if(s.equalsIgnoreCase("W")) {
						cells.add(new WalkwayCell(curRow,curCol));
					} else {
						cells.add(new RoomCell(curRow,curCol,s));
					}
					curCol++;
				}
				curRow++;
			}
			while(scan.hasNext()) {
				String boardLine = scan.nextLine();
				String[] boardData = boardLine.split(",");
				for(int i = 0; i < boardData.length; ++ i) {
					if(boardData[i].length() > 2) {
						throw new BadConfigFormatException("Problems with board read");
					}
				}
				if(boardData.length!=numCols) {
					throw new BadConfigFormatException("Inconsistent board length");
				} else {
					int curCol = 0;
					for(String s:boardData) {
						if(s.equalsIgnoreCase("W")) {
							cells.add(new WalkwayCell(curRow,curCol));
						} else {
							cells.add(new RoomCell(curRow,curCol,s));
						}
						curCol++;
					}
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
	

}
