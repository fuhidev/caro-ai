package tdt.it.ai.core;

import java.util.ArrayList;

public abstract class BaseSearching {
	
	protected Node currentNode;
	public final int MAX_DEPTH = 2;
	protected abstract int getheuristic(Node node, Gomoku.PLAYER player);
	protected abstract ArrayList<Node> getSuccessors(Node nodeGet, Gomoku.PLAYER player);
	
	protected static boolean checkRepeats(Node n) {
		boolean checkRepeats = false;
		Node checkNode = n;
		while (n.getParent() != null && !checkRepeats) {
			if (n.getParent().equals(checkNode))
				checkRepeats = true;
			n = n.getParent();
		}
		return checkRepeats;
	}

	protected  int[][] getOverTile(Node node) {
		int[][] tile = new int[4][2];
		/*ArrayList<Node> output = new ArrayList<Node>();
		for (int i = 0; i < Board.MAX_ROW; i++) {
			for(int j=0;j<Board.MAX_COL;j++){
				if(node.getState().get(i).get(j) != -1){
//					tile[Direction.Top][0]=i;
					tile[0][1]=j;
				}
			}
		}*/
		return tile;
	}
}
