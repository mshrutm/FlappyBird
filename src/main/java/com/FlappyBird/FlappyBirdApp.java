package com.FlappyBird;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

/**
 * DriverApp for Flappy Bird Game
 *
 * @author Tyler
 * @version 05/11/2019
 */
public class FlappyBirdApp extends JFrame{
	private GamePanel _gamePanel;
	private JButton _start;
	JFrame frame = new JFrame("Flappy Bird");
	JFrame frame2 = new JFrame("Flappy Bird");
	private static final String GAME_NAME = "Flappy Bird";
	private static final String INITIAL_MESSAGE = "Welcome to the game";

	public FlappyBirdApp(){
		_start = new JButton("Start Game");
		_start.addActionListener(new StartListener());
		_start.setBounds(800, 800, 200, 100);

		JLabel mid = new JLabel(INITIAL_MESSAGE, SwingConstants.CENTER);
		JLabel top = new JLabel(GAME_NAME, SwingConstants.CENTER);

		Font font = new Font("Helvetica", Font.BOLD, 12);
		mid.setFont(font);

		Font font2 = new Font("Helvetica", Font.BOLD, 20);
		top.setFont(font2);

		frame2.setTitle("Flappy Bird");

		frame2.add(mid);

		frame2.add(top, BorderLayout.PAGE_START);
		JPanel nedredel = new JPanel();
		nedredel.add(_start);

		frame2.add(nedredel, BorderLayout.PAGE_END);
		frame2.setSize(500, 500);
		frame2.setLocationRelativeTo(null);
		frame2.setVisible(true);
		frame2.setResizable(false);


	}
	private class StartListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			_gamePanel = new GamePanel();
			_gamePanel.invalidate();
			frame.add(_gamePanel,BorderLayout.CENTER);
			frame.pack();
			frame.setVisible(true);
			frame.setResizable(false);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // get screen size
			frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2); // set the frame to be in the middle
			frame.setSize(FlappyBirdConstants.BOARD_WIDTH,FlappyBirdConstants.BOARD_HEIGHT);
			frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

			frame2.dispose();
		}
	}

	public static void main(String[] args){
		FlappyBirdApp app = new FlappyBirdApp();
	}
}