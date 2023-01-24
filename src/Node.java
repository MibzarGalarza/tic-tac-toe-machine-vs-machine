
public class Node {
	static boolean playsAsMin = false;

	int[][] field = new int[3][3];
	Node[][] children = new Node[3][3];

	int value;
	boolean isMin;

	public Node(int[][] field, boolean isMin) {
		this.isMin = isMin;
		this.field = field;
	}

	int[][] copyField() {
		int[][] copied = new int[3][3];
		for (int i = 0; i < copied.length; i++) {
			for (int j = 0; j < copied[i].length; j++) {
				copied[i][j] = field[i][j];
			}
		}
		return copied;
	}

	public int learn() {
		if (TicTacToe.hasWon(field)) {
			value = isMin ? 1 : -1;
			return value;
		} else if (TicTacToe.fieldFull(field)) {
			value = 0;
			return value;
		}

		value = isMin ? 1 : -1;

		for (int i = 0; i < children.length; i++) {
			for (int j = 0; j < children[i].length; j++) {

				if (field[i][j] == 0) {
					int[][] childField = copyField();
					childField[i][j] = isMin ? 1 : 2;

					children[i][j] = new Node(childField, !isMin);

					value = isMin ? Math.min(value, children[i][j].learn()) : Math.max(value, children[i][j].learn());

				}
			}
		}

		return value;
	}

	public Node getChildWithValue() {

		for (int i = 0; i < children.length; i++) {
			for (int j = 0; j < children[i].length; j++) {
				if (children[i][j] != null && children[i][j].value == value) {
					return children[i][j];
				}
			}
		}
		return null;
	}

	public Node getPlayer2Node() {

		for (int i = 0; i < children.length; i++) {
			for (int j = 0; j < children[i].length; j++) {
				if (children[i][j] != null && children[i][j].value == value) {
					return children[i][j];
				}
			}
		}
		return null;
	}

}
