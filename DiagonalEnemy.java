
import java.awt.*;
public class DiagonalEnemy extends VerticalEnemy{
	
	//instance variables
	private int screenWidth;
	private int xSpeed;
	
	//constructor
    public DiagonalEnemy(int x, int y, int w, int h, int sH, int yS, int sW, int xS) {
    	super(x,y,w,h,sH,yS);
    	screenWidth = sW;
    	xSpeed = xS;
    }
    public Color getColor() {
        return Color.PINK;
    }
    public void move() {
    	super.move();
    	Rectangle rect = getRectangle();
    	 rect.x += xSpeed;
    	 if(rect.x < 0 || rect.x + rect.width > screenWidth) {
    	 xSpeed = -xSpeed;
    	 }
    	 
    }
}
