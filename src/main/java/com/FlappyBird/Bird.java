package com.FlappyBird;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Bird class
 *
 * @author Tyler
 * @version 05/11/2019
 */
public class Bird{

    private int _x = FlappyBirdConstants.BOARD_WIDTH/2 - 10;
    private int _y = FlappyBirdConstants.BOARD_HEIGHT/2 - 10;

    private SmartRectangle _bird;
    private int moveDistance = FlappyBirdConstants.MOVE_DISTANCE;

    private boolean hitState = false;

    private BufferedImage _birdSprite;

    public Bird() {
        _bird = new SmartRectangle(java.awt.Color.RED);
        _bird.setSize(FlappyBirdConstants.PLAYER_WIDTH, FlappyBirdConstants.PLAYER_HEIGHT);
        _bird.setLocation(_x, _y);

        try{
            _birdSprite = ImageIO.read(new File("src/main/res/img/Bird.png"));
        }
        catch (IOException e){
            System.out.println("Error opening image file.");
        }

    }

    public void drawBird(java.awt.Graphics2D aBrush) {
        aBrush.drawImage(_birdSprite, (int)_bird.getX(), (int)_bird.getY(), FlappyBirdConstants.PLAYER_WIDTH, FlappyBirdConstants.PLAYER_HEIGHT,null);
    }

    public int jump(int yMot)
    {
        yMot = -moveDistance;
        return yMot;
    }

    public boolean checkCollide(SmartRectangle obstacle) {
        return _bird.intersects(obstacle);
    }

    public SmartRectangle getBird(){
        return _bird;
    }

    public int getX(){
        return _x;
    }

    public int getY(){
        return _y;
    }

    public void setX(int x){
         _x = x;
    }

    public void setY(int y){
         _y = y;
    }

    public void setLocation(int x, int y){
        _x = x;
        _y = y;
        _bird.setLocation(x, y);
    }
}