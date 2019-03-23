
public class TTTGame extends GridGame {
	Grid grid;
	int isTaken[][];
	public TTTGame(Grid grid) {
		this.grid = grid;
		isTaken = grid.getArray();
	}
	public int getStatus() {
		return 0;
	}
	
	public Boolean placeMark(int row, int col, int player) {
		if (isTaken[row][col] != 0)
			return false;
		isTaken[row][col] = player;
		return true;
	}
	
	public void placeMark(int player) {
		// IDK what to use this for....
	}
	
	public void printGrid() {
		int size = grid.gridSize;
		if (size  < 2) System.out.println("Size too small");
		System.out.println("\n ");
		int i = 1, j;
		for (j = 0; j <= 4*size; j++) {
			if (j%4 == 2)
				System.out.print(i++);
			else
				System.out.print(" ");
		}
		System.out.println();
		
		for(i = 0; i <= 2* size; i++) {
			if (i%2 != 0)
				System.out.print((char)(i/2 +'A'));
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
						System.out.print(" "+getArrayIndx(j)+' '+getArrayIndx(i)+" ");
					}
				}
			}
			if(i%2!=0)
				System.out.print((char)(i/2 +'A'));
			System.out.println();
		}
		System.out.print(" ");
		for(j = 0, i = 1; j <= 4*size; j++){
			if(j%4==2)
				System.out.print(i++);
			else System.out.print(" ");
		}
		System.out.println();
	}
	
	public void restart() {
		
	}
	
	int getArrayIndx(int val) {
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
}
