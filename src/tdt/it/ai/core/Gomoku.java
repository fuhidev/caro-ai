package tdt.it.ai.core;

import java.util.Observable;
import java.util.Observer;

public class Gomoku extends Observable implements Observer {
	private PLAYER player;
	private Board board;
	public static final PLAYER DEFAULT_PLAYER = PLAYER.O;
	private String messenger;
	private AStarSearch search;
	
	public static enum PLAYER {
		O(0), X(1);
		private int value;

		PLAYER(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public static enum STATUS {
		START(0), STOP(1);
		private int value;

		STATUS(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public String getMessenger() {
		return messenger;
	}

	
	
	public Gomoku() {
		super();
		// TODO Auto-generated constructor stub
		this.board = new Board();
		this.player = DEFAULT_PLAYER;
		this.search = new AStarSearch(this.board.getCurrentNode());
		board.addObserver(this);
	}



	public Gomoku(Board board) {
		super();
		// TODO Auto-generated constructor stub
		this.board = board;
		this.player = DEFAULT_PLAYER;
		this.search = new AStarSearch(this.board.getCurrentNode());
		board.addObserver(this);
	}

	public Board getBoard() {
		return board;
	}

	public boolean doClick(int r, int c) {
		if (board.getWinner() != null)// đã chiến thắng thì không cho đánh nữa
										// hoặc nếu là chưa bắt đầu thì không
										// cho đánh
			return false;
		if(board.getNumOfCelled() == Board.MAX_COL*Board.MAX_ROW){
			messenger = "Hết cờ đánh";
			setChanged();
			notifyObservers(messenger);
			return false;
		}
		int [] tile = new int[2];
		if (this.selectMove(r, c)){
			swapPlayer();
			tile[0]=r;
			tile[1]=c;
			setChanged();
			notifyObservers(tile);
		}
		if(this.player == DEFAULT_PLAYER && this.board.getWinner() == null){
			search.currentNode = board.getCurrentNode();
			tile = search.getTile();
			this.selectMove(tile[0], tile[1]);
			swapPlayer();
			setChanged();
			notifyObservers(tile);
		}
		return true;
	}

	public void newGame() {
		this.board = new Board();
		board.addObserver(this);
		int state[][] = new int[Board.MAX_ROW][Board.MAX_COL];
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state.length; j++) {
				state[i][j] = -1;
			}
		}
		state[6][6] = DEFAULT_PLAYER.getValue();
		this.player = DEFAULT_PLAYER;
		swapPlayer();
		board.getCurrentNode().setState(state);
		setChanged();
		notifyObservers();
	}

	private boolean selectMove(int r, int c) {
		
		return this.board.move(r, c, player);
	}

	private void swapPlayer() {
		int num =board.getNumOfCelled() + 1; 
		board.setNumOfCelled(num);
		this.player = this.player == PLAYER.O ? PLAYER.X : PLAYER.O;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		PLAYER winner = this.board.getWinner();
		if (winner != null) {
			messenger = winner + " is winner";
			setChanged();
			notifyObservers(messenger);
		}

	}
}
