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
			WalkwayCell banana = (WalkwayCell)o;
			if(banana.col==this.col && banana.row==this.row) {
				return true;
			}
		}
		return false;
	}
}
