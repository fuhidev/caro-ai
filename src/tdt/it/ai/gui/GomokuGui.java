package tdt.it.ai.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javafx.beans.InvalidationListener;
import javafx.collections.SetChangeListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tdt.it.ai.core.Board;
import tdt.it.ai.core.Gomoku;

public class GomokuGui extends JFrame implements Observer {

	private JButton[][] arrBtn;
	private Gomoku gomoku;
	private String messenger;
	private boolean isStart = false;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { GomokuGui frame = new
	 * GomokuGui(new Board()); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the frame.
	 */
	public GomokuGui(Gomoku gomoku) {
		this.gomoku = gomoku;
		gomoku.addObserver(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Gomoku Game");
		setBounds(100, 100, 600, 450);
		setResizable(false);
		setLayout(new BorderLayout());
		add(createContentPane(), BorderLayout.CENTER);
		add(createMenuPane(), BorderLayout.EAST);
	}

	private JPanel createContentPane() {
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(Board.MAX_ROW, Board.MAX_COL));
		arrBtn = new JButton[Board.MAX_ROW][Board.MAX_COL];
		for (int i = 0; i < Board.MAX_ROW; i++) {
			for (int j = 0; j < Board.MAX_COL; j++) {
				int r = i, c = j;
				JButton btn = new JButton();
				btn.setBackground(Color.WHITE);
				btn.addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						if (isStart && gomoku.doClick(r, c))
							/* btn.setBackground(Color.BLACK) */;
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
					}
				});
				arrBtn[i][j] = btn;
				contentPane.add(btn);
			}
		}
		return contentPane;
	}

	private JPanel createMenuPane() {
		JPanel menuPane = new JPanel();
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				gomoku.newGame();
				isStart = true;
			}
		});
		;
		menuPane.add(btnNewGame);
		return menuPane;
	}

	private void setIcon(JButton btn, int _player) {
		/*
		 * if (btn.getIcon() != null) return;
		 */
		Icon icon;
		if (_player == -1) {
			icon = new ImageIcon();
		} else {
			Character c = _player == Gomoku.PLAYER.X.getValue() ? 'x' : 'o';
			icon = new ImageIcon("Img\\" + c + ".png");
		}
		btn.setIcon(icon);
		btn.setBackground(Color.white);
	}

	public void updateMatrix() {
		for (int i = 0; i < Board.MAX_ROW; i++) {
			for (int j = 0; j < Board.MAX_COL; j++) {
				int _player = gomoku.getBoard().getCurrentNode().getState()[i][j];
				setIcon(arrBtn[i][j], _player);
			}
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		updateMatrix();
		if (arg1 != null) {
			if (arg1 instanceof String)
				JOptionPane.showMessageDialog(null, (String) arg1);
			else {
				int[] tile = (int[]) arg1;
				arrBtn[tile[0]][tile[1]].setBackground(Color.DARK_GRAY);
			}
		}
	}
}
