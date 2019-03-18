
public abstract class GridGame {
	Grid grid;
	
	public abstract int getStatus();
	
	public abstract Boolean placeMark(int row, int col, int player);
	
	public abstract void placeMark(int player);
	
	public abstract void printGrid();
	
	public abstract void restart();
}
