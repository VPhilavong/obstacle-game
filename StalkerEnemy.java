import java.awt.*;
public class StalkerEnemy extends Enemy {

    // instance variables
    private Rectangle playerRect;

    // constructor
    public StalkerEnemy(int x, int y, int w, int h, Rectangle p) {
        super(x, y, w, h);
        playerRect = p;
    }

    public Color getColor() {
        return Color.MAGENTA;
    }

    public void move() {
        Rectangle ourRect = getRectangle();

       if (ourRect.x <= playerRect.x)
            ourRect.x -= 4;
        else
            ourRect.x += 4;

        if (ourRect.y <= playerRect.y) {
            ourRect.y -= 4;
        } else {
            ourRect.y += 4;
        }

    }
}