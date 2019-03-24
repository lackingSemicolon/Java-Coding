import java.lang.Math;

public class AI {
	Game game;
	int player;
	public AI(Game game, int player) {
		this.game = game;
		this.player = player;
	}
	
	public void tttPick() {
		if (game.isGameOver)
			return;
		int row = (int)(Math.random() * game.getGridSize());
		int col = (int)(Math.random() * game.getGridSize());
		while (!game.placeMark(row, col, player)) {
			row = (int)(Math.random() * game.getGridSize());
			col = (int)(Math.random() * game.getGridSize());
		}
		
	}
	
	public void c4Pick() {
		if (game.isGameOver)
			return;
		int col = (int)(Math.random() * game.getGridSize());
		while (col < 0 || !game.placeMark(col, player)) {
			col = (int)(Math.random() * game.getGridSize());
		}
		
	}


}
