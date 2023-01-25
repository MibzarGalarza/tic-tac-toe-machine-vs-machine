import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class TicTacToe {
	static int[][] field = new int[3][3];
	static Scanner scanner = new Scanner(System.in);

	//I put this meessage for save time
	static final String ERROR_MESSAGE = "Invalid input!";
	static final String[] ROW_LABELS = { "A", "B", "C" };
	static Node ai;

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		//The clear file is for the spaces free
		clearField();

		
		ai = new Node(field, Node.playsAsMin);
		ai.learn();

		for (;;) {
			Node current = ai;
			boolean player = Node.playsAsMin;
			//The table is print in the terminal
			printField();

			for (;;) {
				//So here is when the machine decide the move
				System.out.println("Player " + (player ? "X" : "O") + " is about to set");

				//The first player is the player X he feld depends the bucle and decide the number between 1-9
				if (player) {
					current = current.getChildWithValue();
					field = current.copyField();
					
				}
				//The second player is the player O he feld depends the random number beetween 1-9
				else{
					final int[] indices = getRandom();
					field[indices[0]][indices[1]] = player ? 1 : 2;
					current = current.children[indices[0]][indices[1]];
				}

				printField();

				//He print the player winner and the system exit
				if (hasWon()) {
					System.out.println("Player " + (player ? "X" : "O") + " won!\n");
					System.exit(0);
				}

				//If is a draw print the msg It's a draw. If the machine chooses a number already 
				//chosen or filled in the table, it makes a break and chooses another number.

				if (fieldFull()) {
					System.out.println("It's a draw!\n");
					System.exit(0);
					break;

				}
				player = !player;
			}	
		}
	}


	//Here is when the table is print

	static void printField(int[][] field) {
		System.out.println("     1  2  3");
		System.out.println("     _______");

		for (int i = 0; i < field.length; i++) {
			System.out.print(ROW_LABELS[i] + "   ");
			for (int j = 0; j < field[i].length; j++) {

				System.out.print("|" + (field[i][j] == 0 ? "_" : field[i][j] == 1 ? "X" : "O") + "|");
				//Here is when is decided who field is free space or is field.

			}
			System.out.print("   " + ROW_LABELS[i]);
			System.out.println();
		}
	}

	static void printField() {
		printField(field);
	}


	private static boolean hasWon() {
		return hasWon(field);
	}


	//Here is decided why you need for win the game.
	public static boolean hasWon(int[][] field) {
		for (int i = 0; i < field.length; i++) {

			if (field[i][0] == field[i][1] && field[i][1] == field[i][2] && field[i][0] != 0) {
				return true;
			}
			
			if (field[0][i] == field[1][i] && field[1][i] == field[2][i] && field[0][i] != 0) {
				return true;
			}
		}

		if (field[0][0] == field[1][1] && field[1][1] == field[2][2] && field[0][0] != 0) {
			return true;
		}

		if (field[0][2] == field[1][1] && field[1][1] == field[2][0] && field[0][2] != 0) {
			return true;
		}

		return false;
	}

	private static void clearField() {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field.length; j++) {
				field[i][j] = 0;
			}

		}
	}

	private static boolean fieldFull() {
		return fieldFull(field);
	}

	//Here is when the player O get the number random between 1-9 i use the method 
	//tread class random number and convert to in a int and export the variable.

	private static int[] getRandom() {
		int value;
		for (;;) {
			try {
				final ThreadLocalRandom random = ThreadLocalRandom.current();
				value = Math.abs(random.nextInt(9)+1);
			} catch (final Exception e) {
				System.err.println(ERROR_MESSAGE);
				continue;
			}
			value--;
			if (value < 1 || value > 8) {
				System.err.println(ERROR_MESSAGE);
				continue;
			}

			final int row = 2 - value / 3;
			final int col = value % 3;

			if (field[row][col] != 0) {
				System.err.println("Field is already set!");
				continue;
			}

			return new int[] { row, col };

		}

	}


	public static boolean fieldFull(int[][] field) {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field.length; j++) {
				if (field[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}

}