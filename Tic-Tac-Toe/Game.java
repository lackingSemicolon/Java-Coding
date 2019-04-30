
public class Game extends GridGame {
	Grid grid;
	int gameType; // 1 for TTT 2 for connect 4;
	int nextPlayer = 0, winner = 0;
	boolean isGameOver = false;
	
	public int lastPlay[] = {-1,-1};
	
	public Game(int gridSize, int gameType) {
		this.gameType = gameType;
		if (gameType == 1) // if TTT
			this.grid = new Grid(3);
		else
			this.grid = new Grid(gridSize);
	}
	
	public boolean isSpaceLeft() {
		return grid.getSpaceLeft() > 0;
	}
	
	public int getGridSize() {
		return grid.gridSize;
	}
	
	public int [][] getGrid() {
		return grid.getGrid();
	}
	public int getStatus() { // return who is the winner
		if (!isGameOver)
			return 0;
		if (grid.getSpaceLeft() == 0)
			return 3; // tie
		return winner;
	}
	
	public Boolean placeMark(int row, int col, int player) { // For TTT
		if (!isGameOver && !grid.isSet(row, col)) { // if the game is not ever yet or spot is not taken
			grid.setCell(row, col, player);
			lastPlay[0] = row; lastPlay[1] = col;
			isGameOver = didPlayerWin(row, col, player); //update win status
			if (isGameOver) 
				winner = player;
			else 
				nextPlayer = (player+1 == 3)? 1:2;
			return true;
		}
		return false; // try again
		
	}
	public Boolean placeMark(int col, int player) { // for C4
		Boolean isColFull = false;
		for (int i = getGridSize()-1; i >= 0; i--) {
			if (!(isColFull = grid.isSet(col, i)) ) {
				grid.setCell(col, i, player);
				isGameOver = didPlayerWin(col, i, player); //update win status
				if (isGameOver) 
					winner = player;
				else 
					nextPlayer = (player+1 == 3)? 1:2;
				break;
			}
		}
		printGrid();
		return !isColFull; // return true if we manage to place a mark
	}
	
	public void placeMark(int player) {
		// IDK what to use this for....
	}
	
	public void printGrid() {
		int size = grid.gridSize;
		if (size  < 2) System.out.println("Size too small");
		System.out.println("\n ");
		int i = 1, j;
		for (j = 0; j <= 4*size; j++) { // print the col lable
			if (j%4 == 2)
				System.out.print(i++);
			else
				System.out.print(" ");
		}
		System.out.println();
		
		for(i = 0; i <= 2* size; i++) {
			if (i%2 != 0)
				System.out.print((char)(i/2 +'A')); //print row label
			for(j = 0; j <= 2*size; j++){
				if(i%2==0)
				{
					if(j==0 || j%2==0)
						System.out.print(" ");
					else System.out.print("---");
				}
				else{
					if(j%2==0)
						System.out.print("|");
					else {
						//System.out.print(" "+j+' '+i+" ");
						//System.out.print(" "+getArrayIndx(j)+' '+getArrayIndx(i)+" ");
						//System.out.print("  ");
						int whoOccup = grid.getCell(getArrayIndx(j), getArrayIndx(i));
						if (whoOccup == 0)
							System.out.print("   ");
						else {
							char marker = (whoOccup == 1)? 'X':'O';
							System.out.print(" "+marker+" ");
						}
					}
				}
			}
			if(i%2!=0)
				System.out.print((char)(i/2 +'A'));//print row label
			System.out.println();
		}
		System.out.print(" ");
		for(j = 0, i = 1; j <= 4*size; j++){ //print col label on bottom
			if(j%4==2)
				System.out.print(i++);
			else System.out.print(" ");
		}
		System.out.println();
	}
	
	public void restart() { //reset game
		grid.clear();
		isGameOver = false;
		winner = 0;
		printGrid();
	}
	
	int getArrayIndx(int val) { // return value according to array indexes.
		int ret = 0;
		switch(val) {
		case 1:
			ret = 0;
			break;
		case 3:
			ret = 1;
			break;
		case 5:
			ret = 2;
			break;
		case 7:
			ret = 3;
			break;
		case 9:
			ret = 4;
		default:
			break;
		}
		return ret;
	}
	
	boolean didPlayerWin(int row, int col, int player) {
		return (checkVertical(row, col, player) || checkHorizontal(row, col, player) || checkDiagonal(row, col, player));
	}
	boolean checkVertical(int row, int col, int player) { // check if win by vert
		boolean ret = true;
		for (int i = 0; i < grid.gridSize; i++) { // Start from the top and go down directly
			ret = (grid.getCell(row, i) == player)? true:false;
			if (ret == false) break;
		}
		if (ret == false) System.out.println("Won by Vert");
		return ret;
	}
	boolean checkHorizontal(int row, int col, int player) { // check win by hor
		boolean ret = true;
		for (int i = 0; i < grid.gridSize; i++) { // Start from the top and go down directly
			ret = (grid.getCell(i, col) == player)? true:false;
			if (ret == false) break;
		}
		if (ret == false) System.out.println("Won by Hor");
		return ret;
	}
	boolean checkDiagonal(int row, int col, int player) { // check win by diag
		int last = grid.gridSize-1;
		// check where the player latest position is
		boolean leftCorner = (row == 0 && col == 0)? true:false;
		boolean rightCorner = (row == 0 && col == last)? true:false;
		boolean bottomLCorner = (row == last && col == 0)? true:false;
		boolean bottomRCorner = (row == last && col == last)? true:false;
		boolean middleCorner = (row == last-(last/2) && col == last-(last/2))? true:false;
		
		if (!(leftCorner || rightCorner || bottomLCorner || bottomRCorner)) {// if it's not any of the corner then no point in checking diagonal
			if (last == 2 && !middleCorner) { //3x3 grid and not in middle corner either
				return false;
			}
		}
		boolean ret = true;
		// if it is in the middle or corner points
		for (int i = 0, size; i <= (size = grid.gridSize); i++) { // check from top left to bottom right
			ret = (grid.getCell((row+i)%size, (col+i)%size) == player);
			if (ret == false) break;
		}
		if (ret == false) { // now check the mirror of it
			if (middleCorner || rightCorner || bottomLCorner) {
				for (int i = 0, size; i <= (size = grid.gridSize); i++) { // bottom left to top right
					int newRow = (row+i)%size;
					int newCol;
					if ((newCol = (col-i)%size) < 0) // if it goes out of bound, send it to the other side
						newCol = size-1;
					//System.out.println(newRow + " " + newCol);
					ret = (grid.getCell(newRow, newCol) == player);
					if (ret == false) break;
				}
			}
		}
		if (ret == false) System.out.println("Won by diagonal");
		return ret;
	}
}
