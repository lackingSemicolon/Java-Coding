import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int gridSize = 4;
		boolean restart = true;
		Scanner input = new Scanner(System.in);
		while (restart) {
			int gameMode = pickGame(input);
			Game game = new Game(gridSize, gameMode); game.restart();
			if (gameMode == 1) { // TTT
				playTTT(game);
			}
			else {
				playC4(game);
			}
			System.out.print("\nPlay again? Yes(Y) or No(N): ");
			input.nextLine();
			String resp = input.nextLine().toUpperCase();
			switch (resp.charAt(0)) {
			case 'Y':
				restart = true;
				break;
			case 'N':
			default:
				restart = false;
				break;
					
			}
		}
		
		
	}
	
	static void playTTT(Game game) {
		AI aiPlayer = new AI(game, 2);
		Scanner keyboard = new Scanner(System.in);
		while(game.getStatus() == 0 ) { // while there is not a tie or no one won yet
			game.printGrid();
			System.out.print("Place mark (You're playing as X) at (Row Col): ");
			String input = "";
			while ((input = isValidTTTInput(keyboard, game.getGridSize())).equals("")) { // keep asking for a input till it's valid
				System.out.print("ERR: Invalid Input, Enter Row follow by Col (in range) (eg. A1): ");
			}
			
			int col = Character.getNumericValue(input.charAt(0)); // get column
			int row = Character.getNumericValue(input.charAt(1))-1; // get row
			while (game.placeMark(row, col, 1) == false) { // request for a new cell if user picked a occupied cell
				System.out.print("ERR: Spot is taken; try again: ");
				while ((input = isValidTTTInput(keyboard, game.getGridSize())).equals("")) {
					System.out.print("ERR: Invalid Input, Enter Row follow by Col (in range) (eg. A1): ");
				}
				row = Character.getNumericValue(input.charAt(0)); // get the new updated cell location
				col = Character.getNumericValue(input.charAt(1));
			}
			try { // add a delay too see ai's pick
				game.printGrid();
				if (game.getStatus() != 0) break;
				TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {}
			aiPlayer.tttPick();
		}
		game.printGrid();
		if (game.getStatus() == 3)
			System.out.print("Game OVER\nITS A TIE!");
		else
			System.out.print("Game OVER\n"+ ((game.winner == 1)? "You":"AI") + " won!");
	}
	
	static void playC4(Game game) { // Same thing as playTTT() which some minor tweaks
		AI aiPlayer = new AI(game, 2);
		Scanner keyboard = new Scanner(System.in);
		while(game.getStatus() == 0) {
			game.printGrid();
			System.out.print("Place mark at (Col): ");
			int input = -1;
			while ((input = isValidC4Input(keyboard, game.getGridSize())) == -1) {
				System.out.print("ERR: Invalid Input, Enter Col (in range) (eg. 1): ");
			}
			while (game.placeMark(input, 1) == false) {
				System.out.print("ERR: Column is full; try again: ");
				while ((input = isValidC4Input(keyboard, game.getGridSize())) == -1) {
					System.out.print("ERR: Invalid Input, Enter Col (in range) (eg. 1): ");
				}
			}
			try { // add a delay too see ai's pick
				game.printGrid();
				if (game.getStatus() != 0) break;
				TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {}
			aiPlayer.c4Pick();
		}
		game.printGrid();
		if (game.getStatus() == 3)
			System.out.print("Game OVER\nITS A TIE!");
		else
			System.out.print("Game OVER\nPlayer "+game.winner+" won!");
	}
	
	static String isValidTTTInput(Scanner keyboard, int size) {
		String input = keyboard.nextLine().toUpperCase();
		String newStr = "";
		if (input.length() != 2) { 
			return "";
		}
		char c = input.charAt(0);
		if (!(c >= 'A' && c <= 'A'+size)) { // make sure it's not out of bound for rows
			return "";
		}
		newStr = newStr + (char)(c-17); // convert to number
		c = input.charAt(1);
		if (!(Character.getNumericValue(c) >=  0 && Character.getNumericValue(c) <= size)) { // make sure it's not out of bound for columns
			return "";
		}
		newStr += c;
		return newStr;
	}
	
	//Same as isValidTTTinput() but we're getting only one character
	static int isValidC4Input(Scanner keyboard, int size) {
		String input = keyboard.nextLine().toUpperCase();
		int ret;
		if (input.length() != 1) {
			//System.out.print("\tToo Short");
			return -1;
		}
		char c = input.charAt(0);
		if (!((ret = Character.getNumericValue(c)) >=  0 && ret <= size)) {
			//System.out.println("\tWrong second char "+c);
			return -1;
		}
		return ret-1;
	}
	static int pickGame(Scanner input) {
		System.out.print("Do you want to play Tic-Tac-Toe (1) or Connect 4 (2)? ");
		int ret = input.nextInt();
		while (ret != 1 && ret != 2) {
			System.out.print("Please enter 1 (TTT) or 2 (Connect 4): ");
			ret = input.nextInt();
		}
		return ret;
	}

}
