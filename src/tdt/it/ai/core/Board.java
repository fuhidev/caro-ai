package tdt.it.ai.core;

import java.util.Observable;

import tdt.it.ai.core.Gomoku.PLAYER;

public class Board extends Observable{
	public static final int MAX_COL = 13;
	public static final int MAX_ROW = 13;
	private final int MAX_SCORE = 5;
	private PLAYER winner;
	private Node currentNode;
	private int numOfCelled = 1;

	
	
	public int getNumOfCelled() {
		return numOfCelled;
	}

	public void setNumOfCelled(int numOfCelled) {
		this.numOfCelled = numOfCelled;
	}

	public Board() {
		super();
		// TODO Auto-generated constructor stub
		this.currentNode = new Node();
	}

	public PLAYER getWinner() {
		return winner;
	}

	public Node getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(Node currentNode) {
		this.currentNode = currentNode;
	}

	public boolean move(int r, int c, Gomoku.PLAYER player) {
		int[][] state = currentNode.getState();
		if (state[r][c] != -1)
			return false;
		state[r][c] = player.getValue();
		// currentNode.setState(state);
		if (isWiner(this.currentNode, player, r, c)) {
			winner = player;			
		}
		setChanged();
		notifyObservers();
		return true;
	}
	public boolean isWiner(Node currentNode, Gomoku.PLAYER player, int r, int c) {

		if (checkHorizontal(player, r, currentNode)) {
			
			return true;
		}

		if (checkVertical(player, c, currentNode)) {
			return true;
		}
		
		 if (checkPrimaryDiagonal(player, r, c, currentNode)) {
		 return true;
		 }
		
		 if (checkSecondaryDiagonal(player, r, c, currentNode)) {
		 return true;
		 }

		return false;
	}

	/**
	 * Checks for a match in horizontal direction starting at x, y.
	 *
	 * @param player
	 *            Which player to check for.
	 * @param r
	 *            Starting point in x.
	 * @param c
	 *            Starting point in y.
	 * @return True if player has won, false otherwise.
	 */
	public boolean checkHorizontal(Gomoku.PLAYER player, int r, Node node) {
		int count = 0;
		int col = 0;

		boolean onlyDetained = false;
		boolean bothDetained = false;
		while (col < MAX_COL ) {

			if (node.getState()[r][col] != player.getValue()
					&& node.getState()[r][col] != -1) {
				if (count < MAX_SCORE) {
					onlyDetained = true;
					count = 0;
				}
				if (count >= MAX_SCORE && onlyDetained == true) {
					bothDetained = true;
					count = 0;
				}

			} else if (node.getState()[r][col] == -1) {
				if (count == MAX_SCORE && bothDetained == false)
					return true;
				onlyDetained = false;
				bothDetained = false;
				count = 0;
			}
			if (node.getState()[r][col] == player.getValue()) {

				count++;

			}
			col++;
		}
		if (count == MAX_SCORE && bothDetained == false)
			return true;
		return false;
	}

	/**
	 * Checks for a match in vertical direction starting at x, y.
	 *
	 * @param player
	 *            Which player to check for.
	 * @param x
	 *            Starting point in x.
	 * @param y
	 *            Starting point in y.
	 * @return True if player has won, false otherwise.
	 */
	public boolean checkVertical(Gomoku.PLAYER player, int c, Node node) {
		int count = 0;
		int row = 0;

		boolean onlyDetained = false;
		boolean bothDetained = false;
		while (row < MAX_ROW ) {
			if (node.getState()[row][c] != player.getValue()
					&& node.getState()[row][c] != -1) {
				if (count < MAX_SCORE) {
					onlyDetained = true;
					count = 0;
				}
				if (count >= MAX_SCORE && onlyDetained == true) {
					bothDetained = true;
					count = 0;
				}

			} else if (node.getState()[row][c] == -1) {
				if (count == MAX_SCORE && bothDetained == false)
					return true;
				onlyDetained = false;
				bothDetained = false;
				count = 0;
			}
			if (node.getState()[row][c] == player.getValue()) {

				count++;

			}
			row++;
		}
		if (count == MAX_SCORE && bothDetained == false)
			return true;
		return false;
	}

	/**
	 * Checks for a match in southeast direction starting at x, y.
	 *
	 * @param player
	 *            Which player to check for.
	 * @param x
	 *            Starting point in x.
	 * @param y
	 *            Starting point in y.
	 * @return True if player has won, false otherwise.
	 */
	public boolean checkPrimaryDiagonal(Gomoku.PLAYER player, int r, int c, Node node) {
		int count = 0;
		int row = 0;
		int col = 0;
		if(r > c){
			col = 0;
			row = r - c;
		}
		else{
			row = 0;
			col = c - r;
		}
		boolean onlyDetained = false;
		boolean bothDetained = false;
		while (row < MAX_ROW  && col < MAX_COL) {
			if (node.getState()[row][col] != player.getValue()
					&& node.getState()[row][col] != -1) {
				if (count < MAX_SCORE) {
					onlyDetained = true;
					count = 0;
				}
				if (count >= MAX_SCORE && onlyDetained == true) {
					bothDetained = true;
					count = 0;
				}

			} else if (node.getState()[row][col] == -1) {
				if (count == MAX_SCORE && bothDetained == false)
					return true;
				onlyDetained = false;
				bothDetained = false;
				count = 0;
			}
			if (node.getState()[row][col] == player.getValue()) {

				count++;

			}
			row++;
			col++;
		}
		if (count == MAX_SCORE && bothDetained == false)
			return true;
		return false;
	}
	/**
	 * Checks for a match in northeast direction starting at x, y.
	 *
	 * @param player
	 *            Which player to check for.
	 * @param x
	 *            Starting point in x.
	 * @param y
	 *            Starting point in y.
	 * @return True if player has won, false otherwise.
	 */
	public boolean checkSecondaryDiagonal(Gomoku.PLAYER player, int r, int c, Node node) {
		int count = 0;
		int row = 0;
		int col = 0;
		if((r < 5 && c < 5)||(r > MAX_ROW - 5 && c> MAX_COL - 5))
			return false;
		if(c + r < MAX_COL){
			col = 0;
			row = r + c;
		}
		else{
			row = MAX_ROW - 1;
			col = c - (MAX_ROW - 1 - r);
		}
		boolean onlyDetained = false;
		boolean bothDetained = false;
		while (row > 0 && col < MAX_COL) {
			if (node.getState()[row][col] != player.getValue()
					&& node.getState()[row][col] != -1) { 
				if (count < MAX_SCORE) {
					onlyDetained = true;
					count = 0;
				}
				if (count >= MAX_SCORE && onlyDetained == true) {
					bothDetained = true;
					count = 0;
				}

			} else if (node.getState()[row][col] == -1) {
				if (count == MAX_SCORE && bothDetained == false)
					return true;
				onlyDetained = false;
				bothDetained = false;
				count = 0;
			}
			if (node.getState()[row][col] == player.getValue()) {

				count++;

			}
			row--;
			col++;
		}
		if (count == MAX_SCORE && bothDetained == false)
			return true;
		return false;
	}

}
