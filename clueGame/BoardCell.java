package clueGame;

public abstract class BoardCell {
	int row,col;
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	public boolean isWalkway() {
		return false;
	}
	public boolean isRoom() {
		return false;
	}
	public boolean isDoorway() {
		return false;
	}
	@Override
	public abstract boolean equals(Object o);
	
	public abstract void draw();
}
