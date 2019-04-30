import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class AI {
	Game game;
	int player, ai;
	List<int[][]> list2d = new ArrayList<int[][]>();
	public AI(Game game, int ai) {
		this.game = game;
		this.ai = ai;
		this.player = (ai == 2)? 1:2;
	}
	
	int evaluate(int b[][]) {
		// Checking for Rows for X or O victory. 
	    for (int row = 0; row<3; row++) 
	    { 
	        if (b[row][0]==b[row][1] && 
	            b[row][1]==b[row][2]) 
	        { 
	            if (b[row][0]==ai) 
	                return +10; 
	            else if (b[row][0]== player) 
	                return -10; 
	        } 
	    } 
	  
	    // Checking for Columns for X or O victory. 
	    for (int col = 0; col<3; col++) 
	    { 
	        if (b[0][col]==b[1][col] && 
	            b[1][col]==b[2][col]) 
	        { 
	            if (b[0][col]==ai) 
	                return +10; 
	  
	            else if (b[0][col]==player) 
	                return -10; 
	        } 
	    } 
	  
	    // Checking for Diagonals for X or O victory. 
	    if (b[0][0]==b[1][1] && b[1][1]==b[2][2]) 
	    { 
	        if (b[0][0]==ai) 
	            return +10; 
	        else if (b[0][0]==player) 
	            return -10; 
	    } 
	  
	    if (b[0][2]==b[1][1] && b[1][1]==b[2][0]) 
	    { 
	        if (b[0][2]==ai) 
	            return +10; 
	        else if (b[0][2]==player) 
	            return -10; 
	    } 
	  
	    // Else if none of them have won then return 0 
	    return 0;
	}
	
	int minimax(int board[][], int depth, boolean isMax) 
	{ 
	    int score = evaluate(board); 
	  
	    // If Maximizer has won the game return his/her 
	    // evaluated score 
	    if (score == 10) 
	        return score; 
	  
	    // If Minimizer has won the game return his/her 
	    // evaluated score 
	    if (score == -10) 
	        return score; 
	  
    // If there are no more moves and no winner then 
    // it is a tie 
    if (!game.isSpaceLeft()) 
        return 0; 
  
        int best = -1000; 
  
        // Traverse all cells 
        for (int i = 0; i<3; i++) 
        { 
            for (int j = 0; j<3; j++) 
            { 
                // Check if cell is empty 
                if (board[i][j]==0) 
                { 
                	board[i][j] = ai;
                    // Call minimax recursively and choose 
                    // the maximum value 
                    best = Math.max( best, 
                        minimax(board, depth+1, !isMax) ); 
  
                    // Undo the move 
                    board[i][j] = 0; 
                } 
            } 
        } 
        return best; 
	}
	
	int [] findBestMove(int board[][]) 
	{ 
	    int bestVal = -1000; 
	    int row = -1;
	    int col = -1;
	  
	    // Traverse all cells, evaluate minimax function for 
	    // all empty cells. And return the cell with optimal 
	    // value. 
	    for (int i = 0; i<3; i++) 
	    { 
	        for (int j = 0; j<3; j++) 
	        { 
	            // Check if cell is empty 
	            if (board[i][j]==0) 
	            { 
	                // Make the move 
	                board[i][j] = ai; 
	  
	                // compute evaluation function for this 
	                // move. 
	                int moveVal = minimax(board, 0, false); 
	  
	                // Undo the move 
	                board[i][j] = 0; 
	  
	                // If the value of the current move is 
	                // more than the best value, then update 
	                // best/ 
	                if (moveVal > bestVal) 
	                { 
	                    row = i; 
	                    col = j; 
	                    bestVal = moveVal; 
	                } 
	            } 
	        } 
	    } 
	  
	    //printf("The value of the best Move is : %d\n\n", 
	       //     bestVal); 
	    int tmp [] = {row, col};
	    return tmp; 
	}
	
	public void tttPick() {
		if (game.isGameOver)
			return;
		if (list2d.isEmpty()) {
			int move[] = findBestMove(game.getGrid());
			game.placeMark(move[0],move[1], ai);
			/*int row = (int)(Math.random() * game.getGridSize());
			int col = (int)(Math.random() * game.getGridSize());
			while (!game.placeMark(row, col, ai)) {
				row = (int)(Math.random() * game.getGridSize());
				col = (int)(Math.random() * game.getGridSize());
			}*/
		}
	}
	
	public void c4Pick() {
		if (game.isGameOver)
			return;
		int col = (int)(Math.random() * game.getGridSize());
		while (col < 0 || !game.placeMark(col, ai)) {
			col = (int)(Math.random() * game.getGridSize());
		}
		
	}


}
