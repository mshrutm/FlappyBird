package com.FlappyBird;
/**
 * Pipe class
 *
 * @author Tyler
 * @version 05/11/2019
 */
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pipe{

	private SmartRectangle _pipe;
	private int _x, _y, _width, _height, _id;
	private BufferedImage _topPipe, _bottomPipe;

	public Pipe(int x, int y, int width, int height, int id){
		_pipe = new SmartRectangle(java.awt.Color.GREEN.darker());
		_x = x;
		_y = y;
		_id = id;
		_width = width;
		_height = height;
		_pipe.setLocation(x, y);
		_pipe.setSize(width, height);
		try{
            _topPipe = ImageIO.read(new File("src/main/res/img/pipe-up.png"));
        }
        catch (IOException e){
            System.out.println("Error opening image file.");
        }

        try{
            _bottomPipe = ImageIO.read(new File("src/main/res/img/pipe-down.png"));
        }
        catch (IOException e){
            System.out.println("Error opening image file.");
        }
	}

	

	public void drawPipe(java.awt.Graphics2D aBrush){
		if (_id == 1){
			aBrush.drawImage(_topPipe, _x, _y, _width, _height,null);
		}
		else
			aBrush.drawImage(_bottomPipe, _x, _y, _width, _height,null);
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
        _pipe.setLocation(x, y);
    }

    public int getWidth(){
        return (int) _pipe.width;
    }

    public int getHeight(){
        return (int) _pipe.height;
    }

    public SmartRectangle getPipe(){
        return _pipe;
    }

}