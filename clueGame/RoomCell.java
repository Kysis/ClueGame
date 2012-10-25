package clueGame;

import clueGame.BoardCell;
import clueGame.RoomCell.DoorDirection;

public class RoomCell extends BoardCell {
	public enum DoorDirection {UP,DOWN,LEFT,RIGHT,NONE};
	public DoorDirection doorDirection;
	char room;
	public RoomCell(int row, int col, String identifier) {
		super(row,col);
		room=identifier.charAt(0);
		if(identifier.length()==2) {
			char tempDir=identifier.charAt(1);
			switch( tempDir) {
			case 'U': doorDirection = DoorDirection.UP; break;
			case 'D': doorDirection = DoorDirection.DOWN; break;
			case 'R': doorDirection = DoorDirection.RIGHT; break;
			case 'L': doorDirection = DoorDirection.LEFT; break;
			}
		} else {
			doorDirection = DoorDirection.NONE;
		}
	}
	public char getInitial() {
		return room;
	}
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	@Override
	public boolean isRoom() {
		return true;
	}
	@Override
	public boolean isDoorway() {
		if(doorDirection==DoorDirection.NONE) {
			return false;
		} else {
			return true;
		}
	}
	@Override
	public void draw() {
		if(doorDirection==DoorDirection.NONE) {
			System.out.print(" " +room+" ");
		} else {
			switch (doorDirection) {
			case UP: System.out.print(room + "U "); break;
			case DOWN: System.out.print(room + "D "); break;
			case LEFT: System.out.print(room + "L "); break;
			case RIGHT: System.out.print(room + "R "); break;
			}
		}
	}
	@Override
	public boolean equals(Object o) {
		if(o==null) return false;
		if(o==this) return true;
		if(o instanceof RoomCell) {
			RoomCell banana = (RoomCell)o;
			if(banana.col==this.col && banana.row==this.row) {
				return true;
			}
		}
		return false;
	}
}
