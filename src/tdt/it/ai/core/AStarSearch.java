package tdt.it.ai.core;

import java.util.ArrayList;
import java.util.Stack;

import tdt.it.ai.core.Gomoku.PLAYER;

public class AStarSearch extends BaseSearching {

	private final int[] DEFEND_SCORE_ARRAY = { 0, 3, 36, 324, 2916, 26244,
			236196, 2125764, 19131876 };

	private final int[] ATTACK_SCORE_ARRAY = { 0, 9, 63, 441, 3087, 21609,
			151263, 1058841, 7441887 };

	// private final int MAX = ATTACK_SCORE_ARRAY.length;

	public AStarSearch(Node node) {
		super();
		// TODO Auto-generated constructor stub
		currentNode = node;
	}

	@Override
	public ArrayList<Node> getSuccessors(Node nodeGet, Gomoku.PLAYER player) {
		ArrayList<Node> output = new ArrayList<Node>();
		for (int i = 0; i < Board.MAX_ROW; i++) {
			for (int j = 0; j < Board.MAX_COL; j++) {
				if (nodeGet.getState()[i][j] != -1)
					continue;
				else {
					Node newNode = new Node(nodeGet, selectCell(nodeGet, i, j,
							player));
					newNode.setH(getheuristic(newNode, player));
					output.add(newNode);
				}
			}
		}
		return output;
	}

	public Node selectCell(final Node nodeGet, int x, int y,
			Gomoku.PLAYER player) {
		Node output = new Node(nodeGet);
		int[][] state = output.getState();
		state[x][y] = player.getValue();
		output.setState(state);
		return output;
	}

	public int[] getTile() {
		int score = 0;
		// score = new Random().nextInt(1000);
		int noLineAbleWins = 0;
		int[] tile = new int[2];
		for (int i = 0; i < Board.MAX_COL; i++) {
			for (int j = 0; j < Board.MAX_ROW; j++) {
				int attackScore = 0;
				int defendScore = 0;
				// if (currentNode.getState()[i][j] == Gomoku.DEFAULT_PLAYER
				// .getValue()) {
				if (attackScoreHorizontal(i, j) != 0) {
					noLineAbleWins++;
					attackScore += attackScoreHorizontal(i, j);
				}
				if (attackScoreVertical(i, j) != 0) {
					noLineAbleWins++;
					attackScore += attackScoreVertical(i, j);
				}
				if (attackScorePrimaryDiagonal(i, j) != 0) {
					noLineAbleWins++;
					attackScore += attackScorePrimaryDiagonal(i, j);
				}
				if (attackScoreSecondaryDiagonal(i, j) != 0) {
					noLineAbleWins++;
					attackScore += attackScoreSecondaryDiagonal(i, j);
				}
				defendScore += defendScoreHorizontal(i, j);
				defendScore += defendScoreVertical(i, j);
				defendScore += defendScorePrimaryDiagonal(i, j);
				defendScore += defendScoreSecondaryDiagonal(i, j);

				if (defendScore <= attackScore) {
					if (score <= attackScore
							&& currentNode.getState()[i][j] == -1) {
						if (attackScore < ATTACK_SCORE_ARRAY[noLineAbleWins]) {
							attackScore = ATTACK_SCORE_ARRAY[noLineAbleWins];
						}
						score = attackScore;
						tile[0] = i;
						tile[1] = j;
					}
				} else {
					if (score <= defendScore
							&& currentNode.getState()[i][j] == -1) {
						score = defendScore;
						tile[0] = i;
						tile[1] = j;
						// System.out.println(score + "[" + i + j + "]");
					}
				}
				noLineAbleWins = 0;
			}
		}

		return tile;
	}

	private Node search() {
		// int[] tile = new int[2];
		Node currentNode = this.currentNode;
		Stack<Node> stack = new Stack<>();
		stack.add(currentNode);
		Gomoku.PLAYER player = Gomoku.DEFAULT_PLAYER;
		int depth = 0;
		while (!stack.isEmpty()) {
			Node tmpNode = stack.pop();
			if (depth < this.MAX_DEPTH) {
				// LinkedList<Node> nodeSuccessors = new LinkedList<>();
				ArrayList<Node> tmpSuccessors = this.getSuccessors(tmpNode,
						player);
				/*
				 * for (Node n : tmpSuccessors) { Node newNode = new
				 * Node(tmpNode, n); if (!BaseSearching.checkRepeats(newNode))
				 * nodeSuccessors.add(newNode); } if (nodeSuccessors.size() ==
				 * 0) { continue; }
				 */
				if (tmpSuccessors.size() == 0) {
					continue;
				}
				Node highestNode = tmpSuccessors.get(0);
				for (Node n : tmpSuccessors) {
					if (n.getH() > highestNode.getH())
						highestNode = n;
				}
				int highestValue = highestNode.getH();
				for (Node n : tmpSuccessors) {
					if (n.getH() == highestValue)
						stack.add(n);
				}
				depth++;
				player = player == PLAYER.O ? PLAYER.X : PLAYER.O;
			} else {
				while (tmpNode.getParent() != null /* && depth >1 */
						&& tmpNode.getParent().equals(this.currentNode)) {
					tmpNode = tmpNode.getParent();
					// depth--;
				}
				return tmpNode;
			}
		}

		return null;
	}

	@Override
	protected int getheuristic(Node node, Gomoku.PLAYER player) {
		/*
		 * int score = 0; // score = new Random().nextInt(1000);
		 * 
		 * for (int i = 0; i < Board.MAX_COL; i++) for (int j = 0; j <
		 * Board.MAX_ROW; j++) { int attackScore = 0; int defendScore = 0; if
		 * (currentNode.getState()[i][j] == player.getValue()) { attackScore +=
		 * attackScoreHorizontal(node,player,i, j); attackScore +=
		 * attackScoreVertical(node,player,i, j); attackScore +=
		 * attackScorePrimaryDiagonal(node,player,i, j); attackScore +=
		 * attackScoreSecondaryDiagonal(node,player,i, j); defendScore +=
		 * defendScoreHorizontal(node,player,i, j); defendScore +=
		 * defendScoreVertical(node,player,i, j); defendScore +=
		 * defendScorePrimaryDiagonal(node,player,i, j); defendScore +=
		 * defendScoreSecondaryDiagonal(node,player,i, j);
		 * 
		 * if (defendScore <= attackScore) { if (score < attackScore) { score =
		 * attackScore; } } else { if (score < defendScore) { score =
		 * defendScore; } } }
		 * 
		 * }
		 * 
		 * 
		 * // score = new Random().nextInt(10000); System.out.println(score);
		 */return 0;
	}

	private int defendScoreSecondaryDiagonal(int row, int col) {
		int iScoreTemp = 0;
		int iScoreDefend = 0;
		int iSoQuanTa = 0;

		int iSoQuanDich = 0;

		for (int count = 1; count < 6 && col + count < Board.MAX_COL
				&& row - count > -1 && col + count < Board.MAX_COL - 1; count++) {
			int x = currentNode.getState()[row - count][col + count];

			if (x == Gomoku.DEFAULT_PLAYER.getValue()) {
				iSoQuanTa++;
				break;
			}
			if (x != Gomoku.DEFAULT_PLAYER.getValue() && x != -1) {
				iSoQuanDich++;
				iScoreTemp -= 9;

			}
			if (x == -1)
				break;
		}

		for (int count = 1; count < 6 && row + count < Board.MAX_ROW
				&& col - count > -1; count++) {
			int x = currentNode.getState()[row + count][col - count];
			if (x == Gomoku.DEFAULT_PLAYER.getValue()) {
				iSoQuanTa++;
				break;
			}
			if (x != Gomoku.DEFAULT_PLAYER.getValue() && x != -1) {
				iSoQuanDich++;
				iScoreTemp -= 9;

			}

			if (x == -1)
				break;
		}
		if (iSoQuanTa == 2)
			return 0;
		if (iSoQuanDich >= 0) // bi chan 2 dau
			iScoreDefend -= ATTACK_SCORE_ARRAY[iSoQuanTa] * 2;
		iScoreDefend += DEFEND_SCORE_ARRAY[iSoQuanDich];
		iScoreDefend -= ATTACK_SCORE_ARRAY[iSoQuanTa];
		iScoreTemp += iScoreDefend;

		return iScoreTemp;

	}

	private int defendScorePrimaryDiagonal(int row, int col) {

		int iScoreTemp = 0;
		int iScoreDefend = 0;
		int iSoQuanTa = 0;
		int iSoQuanDich = 0;
		for (int count = 1; count < 6 && row + count < Board.MAX_ROW
				&& col + count < Board.MAX_COL; count++) {
			int x = currentNode.getState()[row + count][col + count];
			if (x == Gomoku.DEFAULT_PLAYER.getValue()) {
				iSoQuanTa++;
				break;
			}
			if (x != Gomoku.DEFAULT_PLAYER.getValue() && x != -1) {
				iSoQuanDich++;
				iScoreTemp -= 9;

			}

			if (x == -1)
				break;
		}

		for (int count = 1; count < 6 && row - count > 0 && col - count > -1; count++) {
			int x = currentNode.getState()[row - count][col - count];
			if (x == Gomoku.DEFAULT_PLAYER.getValue()) {
				iSoQuanTa++;
				break;
			}
			if (x != Gomoku.DEFAULT_PLAYER.getValue() && x != -1) {
				iSoQuanDich++;
				iScoreTemp -= 9;

			}

			if (x == -1)
				break;
		}
		if (iSoQuanTa == 2)
			return 0;
		if (iSoQuanDich >= 0) // bi chan 2 dau
			iScoreDefend -= ATTACK_SCORE_ARRAY[iSoQuanTa] * 2;
		iScoreDefend += DEFEND_SCORE_ARRAY[iSoQuanDich];
		iScoreDefend -= ATTACK_SCORE_ARRAY[iSoQuanTa];
		iScoreTemp += iScoreDefend;

		return iScoreTemp;

	}

	private int defendScoreHorizontal(int row, int col) {
		int iScoreTemp = 0;
		int iScoreDefend = 0;
		int iSoQuanTa = 0;
		int iSoQuanDich = 0;

		for (int count = 1; count < 6 && col + count < Board.MAX_COL - 1; count++) {
			int x = currentNode.getState()[row][col + count];
			if (x == Gomoku.DEFAULT_PLAYER.getValue()) {
				iSoQuanTa++;
				break;
			}
			if (x != Gomoku.DEFAULT_PLAYER.getValue() && x != -1) {
				iSoQuanDich++;
				iScoreTemp -= 9;

			}

			if (x == -1)
				break;
		}

		for (int count = 1; count < 6 && col - count > -1; count++) {
			int x = currentNode.getState()[row][col - count];
			if (x == Gomoku.DEFAULT_PLAYER.getValue()) {
				iSoQuanTa++;
				break;
			}
			if (x != Gomoku.DEFAULT_PLAYER.getValue() && x != -1) {
				iSoQuanDich++;
				iScoreTemp -= 9;

			}

			if (x == -1)
				break;
		}
		if (iSoQuanTa == 2)
			return 0;
		if (iSoQuanDich >= 0) // bi chan 2 dau
			iScoreDefend -= ATTACK_SCORE_ARRAY[iSoQuanTa] * 2;
		iScoreDefend += DEFEND_SCORE_ARRAY[iSoQuanDich];
		iScoreDefend -= ATTACK_SCORE_ARRAY[iSoQuanTa];
		iScoreTemp += iScoreDefend;

		return iScoreTemp;
	}

	private int defendScoreVertical(int row, int col) {
		int iScoreTemp = 0;
		int iScoreDefend = 0;
		int iSoQuanTa = 0;
		int iSoQuanDich = 0;
		for (int count = 1; count < 6 && row + count < Board.MAX_ROW; count++) {
			int x = currentNode.getState()[row + count][col];
			if (x == Gomoku.DEFAULT_PLAYER.getValue()) {
				iSoQuanTa++;
				break;
			}
			if (x != Gomoku.DEFAULT_PLAYER.getValue() && x != -1) {
				iSoQuanDich++;
				iScoreTemp -= 9;
			}

			if (x == -1)
				break;
		}
		for (int count = 1; count < 6 && row - count > -1; count++) {
			int x = currentNode.getState()[row - count][col];
			if (x == Gomoku.DEFAULT_PLAYER.getValue()) {
				iSoQuanTa++;
				break;
			}
			if (x != Gomoku.DEFAULT_PLAYER.getValue() && x != -1) {
				iSoQuanDich++;
				iScoreTemp -= 9;

			}

			if (x == -1)
				break;
		}
		if (iSoQuanTa == 2)
			return 0;
		if (iSoQuanDich >= 0) // bi chan 2 dau
			iScoreDefend -= ATTACK_SCORE_ARRAY[iSoQuanTa] * 2;
		iScoreDefend += DEFEND_SCORE_ARRAY[iSoQuanDich];
		iScoreDefend -= ATTACK_SCORE_ARRAY[iSoQuanTa];
		iScoreTemp += iScoreDefend;

		return iScoreTemp;

	}

	private int attackScoreSecondaryDiagonal(int row, int col) {
		int iScoreTemp = 0;
		int iScoreAttack = 0;
		int iSoQuanTa = 0;
		int iSoQuanDich = 0;
		for (int count = 1; count < 6 && col + count < Board.MAX_COL
				&& row - count > -1; count++) {
			int x = currentNode.getState()[row - count][col + count];
			if (x == Gomoku.DEFAULT_PLAYER.getValue())
				iSoQuanTa++;
			if (x != Gomoku.DEFAULT_PLAYER.getValue() && x != -1) {
				iSoQuanDich++;
				iScoreTemp -= 9;
				break;
			}
			if (x == -1)
				break;
		}
		for (int count = 1; count < 6 && row + count > 0
				&& row + count < Board.MAX_ROW && col - count > -1; count++) {
			int x = currentNode.getState()[row + count][col - count];
			if (x == Gomoku.DEFAULT_PLAYER.getValue())
				iSoQuanTa++;
			if (x != Gomoku.DEFAULT_PLAYER.getValue() && x != -1) {
				iSoQuanDich++;
				iScoreTemp -= 9;
				break;
			}
			if (x == -1)
				break;
		}
		if (iSoQuanDich == 2) // bi chan 2 dau
			return 0;
		iScoreAttack += ATTACK_SCORE_ARRAY[iSoQuanTa];
		iScoreAttack -= ATTACK_SCORE_ARRAY[iSoQuanDich];
		iScoreTemp += iScoreAttack;
		return iScoreTemp;
	}

	private int attackScorePrimaryDiagonal(int row, int col) {
		int iScoreTemp = 0;
		int iScoreAttack = 0;
		int iSoQuanTa = 0;
		int iSoQuanDich = 0;

		for (int count = 1; count < 6 && row + count < Board.MAX_ROW - 1
				&& col + count < Board.MAX_COL - 1; count++) {
			int x = currentNode.getState()[row + count][col + count];
			if (x == Gomoku.DEFAULT_PLAYER.getValue())
				iSoQuanTa++;
			if (x != Gomoku.DEFAULT_PLAYER.getValue() && x != -1) {
				iSoQuanDich++;
				iScoreTemp -= 9;
				break;
			}
			if (x == -1)
				break;
		}

		for (int count = 1; count < 6 && row - count > 0 && col - count > -1; count++) {
			int x = currentNode.getState()[row - count][col - count];
			if (x == Gomoku.DEFAULT_PLAYER.getValue())
				iSoQuanTa++;
			if (x != Gomoku.DEFAULT_PLAYER.getValue() && x != -1) {
				iSoQuanDich++;
				iScoreTemp -= 9;
				break;
			}
			if (x == -1)
				break;
		}
		if (iSoQuanDich == 2) // bi chan 2 dau
			return 0;
		iScoreAttack += ATTACK_SCORE_ARRAY[iSoQuanTa];
		iScoreAttack -= ATTACK_SCORE_ARRAY[iSoQuanDich];
		iScoreTemp += iScoreAttack;
		return iScoreTemp;
	}

	private int attackScoreVertical(int row, int col) {
		int iScoreTemp = 0;
		int iScoreAttack = 0;
		int iSoQuanTa = 0;
		int iSoQuanDich = 0;
		for (int count = 1; count < 6 && row + count < Board.MAX_ROW - 1; count++) {
			int x = currentNode.getState()[row + count][col];
			if (x == Gomoku.DEFAULT_PLAYER.getValue())
				iSoQuanTa++;
			if (x != Gomoku.DEFAULT_PLAYER.getValue() && x != -1) {
				iSoQuanDich++;
				iScoreTemp -= 9;
				break;
			}
			if (x == -1)
				break;
		}

		for (int count = 1; count < 6 && row - count > 0; count++) {
			int x = currentNode.getState()[row - count][col];
			if (x == Gomoku.DEFAULT_PLAYER.getValue())
				iSoQuanTa++;
			if (x != Gomoku.DEFAULT_PLAYER.getValue() && x != -1) {
				iSoQuanDich++;
				iScoreTemp -= 9;
				break;
			}
			if (x == -1)
				break;
		}
		if (iSoQuanDich == 2) // bi chan 2 dau
			return 0;
		iScoreAttack += ATTACK_SCORE_ARRAY[iSoQuanTa];
		iScoreAttack -= ATTACK_SCORE_ARRAY[iSoQuanDich];
		iScoreTemp += iScoreAttack;

		return iScoreTemp;

	}

	private int attackScoreHorizontal(int row, int col) {
		int iScoreTemp = 0;
		int iScoreAttack = 0;
		int iSoQuanTa = 0;
		int iSoQuanDich = 0;

		for (int count = 1; count < 6 && col + count < Board.MAX_COL - 1; count++) {
			int x = currentNode.getState()[row][col + count];
			if (x == Gomoku.DEFAULT_PLAYER.getValue())
				iSoQuanTa++;
			if (x != Gomoku.DEFAULT_PLAYER.getValue() && x != -1) {
				iSoQuanDich++;
				iScoreTemp -= 9;
				break;
			}
			if (x == -1)
				break;
		}

		for (int count = 1; count < 6 && col - count > -1; count++) {
			int x = currentNode.getState()[row][col - count];
			if (x == Gomoku.DEFAULT_PLAYER.getValue())
				iSoQuanTa++;
			if (x != Gomoku.DEFAULT_PLAYER.getValue() && x != -1) {
				iSoQuanDich++;
				iScoreTemp -= 9;
				break;
			}
			if (x == -1)
				break;
		}
		// System.out.println(iSoQuanTa);
		// System.out.println(this.currentNode);
		if (iSoQuanDich == 2) // bi chan 2 dau return 0;
			return 0;
		iScoreAttack += ATTACK_SCORE_ARRAY[iSoQuanTa];
		iScoreAttack -= ATTACK_SCORE_ARRAY[iSoQuanDich];
		iScoreTemp += iScoreAttack;

		return iScoreTemp;
	}
}
