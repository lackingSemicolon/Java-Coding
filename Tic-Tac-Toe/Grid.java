
public class Grid {
	int isTaken[][]; // keep tabs if each cell is taken: 1 for x 2 for o
	public int gridSize = 0;
	public Grid(int n) { // nxn grid
		isTaken = new int[n][n];
		gridSize = n;
		for(int i = 0; i < n; i++) {
			for (int k = 0; k < n; k++)
				isTaken[i][k] = 0;
		}
	}
	
	public int [][]getArray() {
		return isTaken;
	}
	
	public int getCell(int row, int col) {
		return 0;
	}
	
	public void setCell(int row, int col, int val) {
		
	}
	
	public Boolean isSet(int row, int col) {
		return false;
	}
	
	public void clear() {
		
	}
}
