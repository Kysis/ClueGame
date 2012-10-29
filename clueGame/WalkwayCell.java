package clueGame;

import clueGame.BoardCell;

public class WalkwayCell extends BoardCell {
	public WalkwayCell(int row, int col) {
		super(row, col);
	}
	@Override
	public boolean isWalkway() {
		return true;
	}
	@Override
	public void draw() {
		System.out.print(" W ");
	}
	@Override
	public boolean equals(Object o) {
		if(o==null) return false;
		if(o==this) return true;
		if(o instanceof WalkwayCell) {
			WalkwayCell temp = (WalkwayCell)o;
			if(temp.col==this.col && temp.row==this.row) {
				return true;
			}
		}
		return false;
	}
}
