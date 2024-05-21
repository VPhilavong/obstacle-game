import java.awt.*;
public class VerticalEnemy extends Enemy {
	//instance variables
	private int screenHeight;
	private int ySpeed;
	
	//constructor
    public VerticalEnemy(int x, int y, int w, int h, int sH, int yS) {
    	super(x,y,w,h);
    	screenHeight = sH;
    	ySpeed = yS;
    }
    public Color getColor() {
        return Color.RED;
    }
    public void move() {
    	Rectangle rect = getRectangle();
    	 rect.y -= ySpeed;
    	 if(rect.y < 0 || rect.y + rect.height > screenHeight) {
    	 ySpeed = -ySpeed;
    	 }
    		
    }
    
}