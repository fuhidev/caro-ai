package tdt.it.ai.core;

import java.util.Comparator;

public class Node {

	private int[][] state;
	private int h;
	private Node parent;

	public Node() {
		this.state = new int[Board.MAX_ROW][Board.MAX_COL];
		for (int i = 0; i < Board.MAX_ROW; i++)
			for (int j = 0; j < Board.MAX_COL; j++)
				state[i][j] = -1;

		this.h = 0;
	}

	public void copyState(int[][] state) {
		for (int i = 0; i < Board.MAX_ROW; i++) {
			for (int j = 0; j < Board.MAX_COL; j++) {
				this.state[i][j] = state[i][j];
			}
		}

	}

	public Node(Node parent, Node newNode) {
		setParent(parent);
		this.state = new int[Board.MAX_ROW][Board.MAX_COL];
		for (int i = 0; i < Board.MAX_ROW; i++) {
			for (int j = 0; j < Board.MAX_COL; j++) {
				this.state[i][j] = newNode.getState()[i][j];
			}
		}
		this.h = newNode.h;
	}

	public Node(Node node) {
		this.state = new int[Board.MAX_ROW][Board.MAX_COL];
		for (int i = 0; i < Board.MAX_ROW; i++) {
			for (int j = 0; j < Board.MAX_COL; j++) {
				this.state[i][j] = node.getState()[i][j];
			}
		}
		this.h = node.h;
	}

	public static Comparator<Node> HeuristicComparator = new Comparator<Node>() {

		@Override
		public int compare(Node a, Node b) {
			return a.h - b.h;
		}
	};

	@Override
	public boolean equals(Object obj) {
		Node node = (Node) obj;
		for (int i = 0; i < Board.MAX_ROW; i++) {
			for (int j = 0; j < Board.MAX_COL; j++) {
				int a = this.state[i][j], b = node.getState()[i][j];
				if (a != b)
					return false;
			}
		}
		return true;
	};

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append(this.h);
		output.append("\n");
		for (int i = 0; i < Board.MAX_ROW; i++) {
			for (int j = 0; j < Board.MAX_COL; j++) {
				output.append(state[i][j]);
			}
			output.append("\n");
		}
		return output.toString();
	}

	public Node copy(Node node) {
		Node tmp = new Node();
		tmp.state = node.state;
		tmp.h = node.h;
		return tmp;
	}

	public int[][] getState() {
		return state;
	}

	public void setState(int[][] state) {
		this.state = state;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getParent() {
		return parent;
	}

}
