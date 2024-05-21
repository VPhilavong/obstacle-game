import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
/*
    Class:      ObstacleGame
    Purpose:    Creates a simple obstacle course for a player to navigate
    Author:     ___________________
    Date:       ___________________
*/
public class ObstacleGame extends JPanel implements ActionListener, KeyListener {
    private Rectangle player = new Rectangle(); //a rectangle that represents the player
    private Rectangle goal = new Rectangle(); //a rectangle that represents the goal
    private Enemy[] enemies = new Enemy[5]; //the array of Enemy objects;
    
    private boolean up, down, left, right; //booleans that track which keys are currently pressed
    private Timer timer; //the update timer
    
    private int gameWidth = 1000; //the width of the game area
    private int gameHeight = 1000; //the height of the game area
    //Sets up the basic GUI for the game
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Obstacle Game");
        frame.setLayout(new BorderLayout());
        
        ObstacleGame game = new ObstacleGame();
        frame.add(game, BorderLayout.CENTER);
        
        game.addKeyListener(game);
        frame.addKeyListener(game);
        
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        
        game.setUpGame();
    }
    
    //Constructor for the game panel
    public ObstacleGame() {
         setPreferredSize(new Dimension(gameWidth, gameHeight));
    }
    
    //Method that is called by the timer 30 times per second (roughly)
    //Most games go through states - updating objects, then drawing them
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }
    
    //Called every time a key is pressed
    //Stores the down state for use in the update method
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP ) {
            up = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        }
    }
    
    //Called every time a key is released
    //Stores the down state for use in the update method
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP ) {
            up = false;
        }
        else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = false;
        }
        else if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        }
        else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        }
    }
    
    //Called every time a key is typed
    public void keyTyped(KeyEvent e) {
    }
     
    //Sets the initial state of the game
    //Could be modified to allow for multiple levels
    public void setUpGame() {
    
        if(timer != null) {
            timer.stop();
        }
    
        timer = new Timer(1000 / 30, this); //roughly 30 frames per second
        timer.start();
        
        up = down = left = right = false;
    
        player = new Rectangle(50, 50, 50, 50);
        goal = new Rectangle(800, 800, 50, 50);
        
        enemies[0] = new VerticalEnemy(300, 300, 50, 50, gameHeight, 15);
        enemies[1] = new DiagonalEnemy(500, 500, 50, 50, gameHeight, 20, gameWidth, 20);
        enemies[2] = new StalkerEnemy(500, 500, 50, 50, player);
        enemies[3] = new SpinningEnemy(500, 500, 50, 50, 300);
        //enemies[] = new VerticalEnemy(700, 300, 50, 50, gameHeight, 20);
    }
    
    //The update method does 5 things
    //1 - it has the player move based on what key is currently being pressed
    //2 - it prevents the player from leaving the screen
    //3 - it checks if the player has reached the goal, and if so congratulates them and restarts the game
    //4 - it checks if any of the Enemy objects are touching the player, and if so notifies the player of their defeat and restarts the game
    //5 - it tells each of the Enemy objects to update()
    public void update() {
        if(up) {
            player.y-=20;
        }
        if(down) {
            player.y+=20;
        }
        if(left) {
            player.x-=20;
        }
        if(right) {
            player.x+=20;
        }
        
        if(player.x < 0) {
            player.x = 0;
        }
        else if(player.x + player.width > gameWidth) {
            player.x = gameWidth - player.width;
        }
        
        if(player.y < 0) {
            player.y = 0;
        }
        else if(player.y + player.height > gameHeight) {
            player.y = gameHeight - player.height;
        }
        
        if(player.intersects(goal)) {
            JOptionPane.showMessageDialog(null, "You won!");
            setUpGame();
        }
        
        for(Enemy e: enemies) {
            if(e == null)
                continue;
        
            if(e.intersects(player)) {
                JOptionPane.showMessageDialog(null, "You lost");
                setUpGame();
            }
            
            e.move();
        }
        
    }
    
    //The paint method does 3 things
    //1 - it draws a white background
    //2 - it draws the player in blue
    //3 - it draws the goal in green
    //4 - it draws all the Enemy objects
    
    
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, gameWidth, gameHeight);
    
        g.setColor(Color.BLUE);
        g.fillRect(player.x, player.y, player.width, player.height);
        
        g.setColor(Color.GREEN);
        g.fillRect(goal.x, goal.y, goal.width, goal.height);
        
        for(Enemy e: enemies) {
            if(e == null)
                continue;
            e.draw(g);
        }
    }
}