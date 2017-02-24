package tdt.it.ai.main;

import tdt.it.ai.core.AStarSearch;
import tdt.it.ai.core.Board;
import tdt.it.ai.core.Gomoku;
import tdt.it.ai.gui.GomokuGui;

public class Test {
	public static void main(String[] args) {
		/*int[][] state = {
				{ -1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, 1, -1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, 1, -1, -1, 1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, 1, -1, -1, -1, 1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, 1, -1, 1, 1, 1, 1, 1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, 1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, -1 } };*/
		Gomoku gomoku = new Gomoku();
		GomokuGui gui = new GomokuGui(gomoku);
		gui.setVisible(true);
		/*do {
			int []tile = s.getTile();
			gomoku.doClick(tile[0], tile[1]);
			gui.updateMatrix();
		} while (b.getWinner() != null);*/
		// System.out.println(s.getTile()[-1]+""+s.getTile()[1]);
	}

}
