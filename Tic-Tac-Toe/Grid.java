
public class Grid {
	int isTaken[][]; // keep tabs if each cell is taken: 1 for x 2 for o
	int spaceLeft;
	public int gridSize = 0;
	public Grid(int n) { // nxn grid
		isTaken = new int[n][n];
		gridSize = n;
		spaceLeft = n*n;
		for(int i = 0; i < n; i++) {
			for (int k = 0; k < n; k++)
				isTaken[i][k] = 0;
		}
	}
	
	public int getSpaceLeft() {
		return spaceLeft;
	}
	
	public int getCell(int row, int col) { // return the value of what players took this spot
		//System.out.println(row + " " + col);
		return isTaken[row][col];
	}
	
	public void setCell(int row, int col, int val) { // occupies cell
		isTaken[row][col] = val;
		spaceLeft--;
	}
	
	public Boolean isSet(int row, int col) { // see if it's open
		try {
			return isTaken[row][col] != 0; // return true if spot is taken
		} catch (ArrayIndexOutOfBoundsException exception) {
			System.out.println(exception+":"+row + " " + col);
			return true;
		}
	}
	
	public void clear() {
		for(int i = 0; i < gridSize; i++) {
			for (int k = 0; k < gridSize; k++)
				isTaken[i][k] = 0;
		}
	}
}
