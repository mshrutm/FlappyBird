package com.FlappyBird;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;
import java.io.*;
import java.applet.*;
import javax.sound.sampled.*;

/**
 * Where games will be done
 *
 * @author Tyler
 * @version 05/11/2019
 */
public class GamePanel extends JPanel implements ActionListener
{
    
    // instance variables
    private int _score;
    private Timer _timer;
    private KeyPListener _pauseKey;
    private KeySpaceListener _spaceKey;
    private Bird _bird;
    private ArrayList<Pipe> _pipelist;
    private int _ticks = 0, _yMotion = 0;
    private Random _generator;
    private SmartRectangle _grass;
    private SmartRectangle _land;
    private Font _flappyFontBase, _flappyScoreFont, _flappyFontText;
    private boolean _gameOver;
    private int _highscore = 0;
    private Clip _clip, _clip2, _clip3, _clip4, _clip5;
    
    /**
     * Constructor for objects of class GamePanel
     */
    public GamePanel()
    {
        this.resetGame();
        this.startGame();
    }

    public void resetGame(){
        //set variables
        _score = 0;
        _gameOver = false;
    }

    public void startGame(){
        this.setBackground(Color.CYAN.darker().darker());
        this.setVisible(true);
        this.setPreferredSize(new Dimension(FlappyBirdConstants.BOARD_WIDTH, FlappyBirdConstants.BOARD_HEIGHT));

        // create character/objects
        _bird = new Bird();
        _pipelist = new ArrayList<Pipe>();
        _grass = new SmartRectangle(java.awt.Color.GREEN);
        _grass.setSize(FlappyBirdConstants.BOARD_WIDTH, 120);
        _grass.setLocation(0, FlappyBirdConstants.BOARD_HEIGHT - 120);
        _land = new SmartRectangle(java.awt.Color.ORANGE);
        _land.setSize(FlappyBirdConstants.BOARD_WIDTH, 20);
        _land.setLocation(0, FlappyBirdConstants.BOARD_HEIGHT - 120);


        // initialize KeyListener
        _pauseKey = new KeyPListener(this);
        _spaceKey = new KeySpaceListener(this);
        _generator = new Random();

        // load effect
        this.loadSound();
        this.loadFont();

        // make pipe
        for (int i = 0; i < 4; i++){
            this.makePipe(true);
        }

        // start timer
        _timer = new Timer(20, this);
        _timer.start();
    }

    public void loadSound(){
    	// load point sound
        try {
         // Open an audio input stream.
         File pointSound = new File("src/main/res/sound/sfx_point.wav");
         AudioInputStream ptaudioIn = AudioSystem.getAudioInputStream(pointSound);  // Get a sound clip resource.
         _clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         _clip.open(ptaudioIn);
         //clip.start();
      } 
      	catch (Exception ex) {
	        // Exit if sound cannot be loaded
	        ex.printStackTrace();
	        System.err.println("Could not load sound file!");
	        System.exit(-1);
        }

        // load hit sound
        try {
         File hitSound = new File("src/main/res/sound/sfx_hit.wav");
         AudioInputStream hitaudioIn = AudioSystem.getAudioInputStream(hitSound);
         _clip2 = AudioSystem.getClip();
         _clip2.open(hitaudioIn);
      } 
      	catch (Exception ex) {
	        ex.printStackTrace();
	        System.err.println("Could not load sound file!");
	        System.exit(-1);
        }

        // load wing sound
        try {
         File wingSound = new File("src/main/res/sound/sfx_wing.wav");
         AudioInputStream wingaudioIn = AudioSystem.getAudioInputStream(wingSound);
         _clip3 = AudioSystem.getClip();
         _clip3.open(wingaudioIn);
      } 
      	catch (Exception ex) {
	        ex.printStackTrace();
	        System.err.println("Could not load sound file!");
	        System.exit(-1);
        }

        // load die sound
        try {
         File dieSound = new File("src/main/res/sound/sfx_die.wav");
         AudioInputStream dieaudioIn = AudioSystem.getAudioInputStream(dieSound);
         _clip4 = AudioSystem.getClip();
         _clip4.open(dieaudioIn);
      } 
      	catch (Exception ex) {
	        ex.printStackTrace();
	        System.err.println("Could not load sound file!");
	        System.exit(-1);
        }
    }
    public void loadFont(){
        try {
            InputStream is = new BufferedInputStream(new FileInputStream("src/main/res/font/flappy-font.ttf"));
            _flappyFontBase = Font.createFont(Font.TRUETYPE_FONT, is);

            _flappyScoreFont = _flappyFontBase.deriveFont(Font.PLAIN, 50);
            _flappyFontText  = _flappyFontBase.deriveFont(Font.PLAIN, 20);
        } 
        catch (Exception ex) {
            // Exit if font cannot be loaded
            ex.printStackTrace();
            System.err.println("Could not load Flappy Font!");
            System.exit(-1);
        }
    }
    public void makePipe(boolean start){
        int space = 250;
        int width = 100;
        int height = 50 + _generator.nextInt(300);
        Pipe pipe , pipe2 ;
        if (_pipelist.size() == 0){
            pipe = new Pipe(FlappyBirdConstants.BOARD_WIDTH + width + _pipelist.size() * 300, FlappyBirdConstants.BOARD_HEIGHT - height- 120, width, height, 1);
            pipe2 = new Pipe(FlappyBirdConstants.BOARD_WIDTH + width + _pipelist.size() * 300, 0, width, FlappyBirdConstants.BOARD_HEIGHT - height - space, 2);
        }
        else{
            pipe = new Pipe(_pipelist.get(_pipelist.size() - 1).getX() + 600, FlappyBirdConstants.BOARD_HEIGHT - height- 120, width, height, 1);
            pipe2 = new Pipe(_pipelist.get(_pipelist.size() - 1).getX() + 600, 0, width, FlappyBirdConstants.BOARD_HEIGHT - height - space, 2);
        }
        _pipelist.add(pipe);
        _pipelist.add(pipe2); 
    }



    private int getScore(){
        return _score;
    }

    private int getHighScore(){
        if (_score > _highscore)
            _highscore = _score;
        return _highscore;
    }
    
    public void paintComponent (java.awt.Graphics aBrush) 
    {
        super.paintComponent(aBrush);
        Graphics2D aBetterBrush = (Graphics2D) aBrush;

        //draw grass
        _grass.draw(aBetterBrush);
        _grass.fill(aBetterBrush);

        // draw land
        _land.draw(aBetterBrush);
        _land.fill(aBetterBrush);

        _bird.drawBird(aBetterBrush);
        for(Pipe pipe : _pipelist){
            pipe.drawPipe(aBetterBrush);
        }
        this.drawText(aBetterBrush);
    }

    public void runClip(Clip clip){
    	if (clip.isRunning())
	                clip.stop();
            clip.setFramePosition(0);
            clip.start();
    }

    public void gameOver(Bird bird, SmartRectangle obstacle){
        _gameOver = _bird.checkCollide(obstacle);
        if (_gameOver){
        	this.runClip(_clip2);
        	this.runClip(_clip4);

            int confirm = JOptionPane.showConfirmDialog(null, "Do you want to restart?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            _timer.stop();
            _pauseKey.setEnabled(false);
            _spaceKey.setEnabled(false);
            if (confirm == JOptionPane.YES_OPTION){
                this.resetGame();
                this.startGame();
            }
            else{
                System.exit(0);
            }
        }
    }

    public boolean getGameOver(){
        return _gameOver;
    }

    public void drawText(Graphics2D aBrush){
        aBrush.setColor(Color.white);
        if (!_timer.isRunning() && !_gameOver){
            aBrush.setFont(_flappyFontText);
            aBrush.drawString("Paused", FlappyBirdConstants.BOARD_WIDTH/2-25, FlappyBirdConstants.BOARD_HEIGHT/2 - 25);
        }
        else{
            aBrush.setFont(_flappyScoreFont);
            aBrush.drawString(" " + this.getScore(), FlappyBirdConstants.BOARD_WIDTH/2-25, FlappyBirdConstants.BOARD_HEIGHT/2 - 200);
        }
        aBrush.setFont(_flappyFontText);
        aBrush.drawString("High Score: " + this.getHighScore(), 0, 25);
    }


    public void actionPerformed(ActionEvent e){
        int speed = 10;
        _ticks ++;
        // start of the game, creating some pipe
        for (int i = 0; i < _pipelist.size(); i++){
            Pipe pipe = _pipelist.get(i);
            pipe.setX(pipe.getX() - speed);
            pipe.setLocation(pipe.getX(), pipe.getY());
        }
        if (_ticks % 2 == 0 && _yMotion < 15){
            _yMotion += 2;
        }
        // check if hits the ground
        this.gameOver(_bird, _land);
        if (this.getGameOver())
            return;
        this.gameOver(_bird, _grass);
        if (this.getGameOver())
            return;

        // after a while from starting game, we make more pipe
        for (int i = 0; i < _pipelist.size(); i++){
            Pipe pipe = _pipelist.get(i);
            int x = pipe.getX();
            int y = pipe.getY();
            int width = pipe.getWidth();
            this.gameOver(_bird, pipe.getPipe()); // check if hits any pipe
            if (_gameOver)
                return;

            if (i % 2 == 0 && !this.getGameOver() && _bird.getX() == x){ // get score
                _score++;
                if (_clip.isRunning())
	                _clip.stop();   // Stop the music player if it is still running
	            _clip.setFramePosition(0); // rewind to the beginning
	            _clip.start();     // Start playing

            }

            if (x + width <= 0){ // if the column already passes border, remove and append more pipe.
                _pipelist.remove(pipe);
                this.makePipe(true);
            }
        }

        _bird.setLocation(_bird.getX(), _bird.getY()+_yMotion); // bird falls

        repaint();
    }

    private class KeyPListener extends KeyInteractor 
    {
        public KeyPListener(JPanel p)
        {
            super(p,KeyEvent.VK_P);
        }
        
        public  void actionPerformed (ActionEvent e) {
            if(_timer.isRunning()){
                _timer.stop();
                repaint();
            }
            else {
                _timer.start();
            }
        }
    }
    private class KeySpaceListener extends KeyInteractor 
    {
        public KeySpaceListener(JPanel p)
        {
            super(p,KeyEvent.VK_SPACE);
        }
        
        public  void actionPerformed (ActionEvent e) {
            if (!_timer.isRunning()){
                repaint();
            }
            else{
                _yMotion = _bird.jump(_yMotion);
                _bird.setLocation(_bird.getX(), _bird.getY() + _yMotion);
                runClip(_clip3);
            }

        }
    }
}