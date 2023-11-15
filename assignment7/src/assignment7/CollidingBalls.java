package assignment7;

//imports required for the program
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CollidingBalls extends JPanel implements Runnable {
	
	// colours to be used for the balls
    private static Color[] colours = {
        Color.RED, Color.ORANGE, Color.YELLOW,
        Color.GREEN, Color.PINK, Color.BLACK,
        Color.GRAY, Color.BLUE, Color.MAGENTA,
        Color.CYAN, Color.DARK_GRAY
    };
    
    // variable to hold the next colour
    private static int nextColour = 0;
    private ArrayList<Ball> balls;
    
    // container box's width and height
    private static final int CONTAINER_WIDTH = 700, CONTAINER_HEIGHT = 500;
    
    // constructor for the colliding balls taking in the ballCount
    public CollidingBalls(int ballCount) {
    	
  	    // set the size of the container
        this.setPreferredSize(new Dimension(CONTAINER_WIDTH, CONTAINER_HEIGHT));

        // create a new array list
        this.balls = new ArrayList<Ball>();
        
        // loop through the ball counter
        for (int i = 0; i < ballCount; i++) {
        	
        	// add the balls to the array
            this.balls.add(new Ball(colours[nextColour]));
            
            // increment the colour
            nextColour++;
            
            // if the 
            if (nextColour >= colours.length) {
            	
            	// start the next colour from 0 again
                nextColour = 0;
            }
        }
        
        // start the drawer of balls
        new Thread(this).start();
        
        // starts the independent movement of balls
        for (int i = 0; i < ballCount; i++) {
        	
            new Thread(this.balls.get(i)).start();
            
        }
    }
    
 // starts the thread that would execute the balls
    @Override
    public void run() {
    	
    	// infinite loop
        while(true) {
        	
            try {
                Thread.sleep(10);
                
            } catch(Exception e) {
            	
                e.printStackTrace(System.out);
            }
            
            // repaint the graphics
            this.repaint();
            
        }
    }
    
    public void displayBalls() {
    	
    	for (int i = 0; i <= balls.size(); i++) {
    		
    		System.out.println(balls.get(i).x);
    	}
    	
    }
    
 // paints the balls onto the screen
    @Override
    public void paintComponent(Graphics g) {
    	
        // draw the box
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, CONTAINER_WIDTH, CONTAINER_HEIGHT);
    	
        // for all of the balls in the array
        for (int i = 0; i < this.balls.size(); i++) {
        	
        	// draw the balls on the screen
            this.balls.get(i).draw(g);
        }
    }
    
    // a class to model a ball
    class Ball implements Runnable {
    	
    	// position of the ball
        private int x, y;
        
        // speed of the ball
        private int dx, dy;
        
        // properties of the ball
        private int radius;
        private Color color;
        
		// constructor for the ball
        public Ball(Color color) {
        	
        	// set the position of the ball to a random position within the container
            this.x = (int) getRandomNumberInRange(1, CONTAINER_WIDTH);
            this.y = (int) getRandomNumberInRange(1, CONTAINER_HEIGHT); 
            
            // generate a random speed for the ball
            this.dx = (int) getRandomNumberInRange(1, 10);
            this.dy = (int) getRandomNumberInRange(1, 10);
            
            // set the properties of the ball
            this.radius = 15;
            this.color = color;
        }
        
        // method to check if the current ball collides with another ball in the array
        public boolean isCollision(Ball otherBall) {
        	
        	// this.ball coordinates
            int x1 = this.getX();
            int y1 = this.getY();
            
            // other ball coordinates
            int x2 = otherBall.getX();
            int y2 = otherBall.getY();
            
            // calculate radii for both balls
            int radius1 = this.radius;
            int radius2 = otherBall.radius;
            
            // use the distance formula to check for a collision
            if (Math.sqrt((x2 - x1) * (x2 - x1)  + (y2 - y1) * (y2 - y1)) < (radius1 + radius2)) {
            	
            	// return true if there is a collision
                return true;
                
            // otherwise
            } else {
            
            	// return false
            	return false;
            
            }
        }
        

        // method to return the synchronised x coordinate
        public synchronized int getX() {
        	
        	// return the x coordinate
            return this.x;
        }
        

        
        // method to return the synchronised y coordinate
        public synchronized int getY() {
        	
        	// returns the y coordinate
            return this.y;
        }
        
 	   // method to get a random number within a defined range
        private float getRandomNumberInRange(int min, int max) {

        	// if the maximum is less than the minimum
        	if (min >= max) {
        	    	   
        	    // throw exception
        	    throw new IllegalArgumentException("max must be greater than min");
        	           
        	 // otherwise
        	 } else {
        	    	   
        	     // create new random object
        	     Random random = new Random();
        	           
        	     // return a random number in the range
        	     return random.nextInt((max - min) + 1) + min;
        	     
        	}
       }
	
        
        // method to move the ball
        public void move() {
        	
        	// calculate the ball's new position
            this.x += this.dx;
            this.y += this.dy;
            
            // check if the ball is crossing the lower x bound
            if (this.x - this.radius < 0) {
            	
		    	  // invert the direction
		          this.dx = -this.dx; 
		          
		          // reposition the ball
		          this.x = this.radius;
            }
            
            // check if the ball is crossing the upper x bound
            else if (this.x + this.radius > CONTAINER_WIDTH) {
            	
            	// invert the direction
            	this.dx = -this.dx; 
            	
            	// reposition the ball
            	this.x = CONTAINER_WIDTH - this.radius;
            	
            }
            
            // check if the ball is crossing the lower y bound
            if (this.y - this.radius < 0) {
            	
            	// invert the direction
            	this.dy = -this.dy;
            	
            	// reposition the ball
            	this.y = this.radius;
            	
            }
            
            // check if the ball is crossing the upper y bound
            else if (this.y + this.radius > CONTAINER_HEIGHT) {
            	
            	// invert the direction
            	this.dy = -this.dy; 
            	
            	// reposition the ball
            	this.y = CONTAINER_HEIGHT - this.radius;
            	
            }
            
            // loop through the array
            for (int i = 0; i < balls.size(); i++) {
            	
            	// get the balls from the array
                Ball ball = balls.get(i);
                
                // if it is the current ball
                if (ball == this) {
                	
                	// continue
                    continue;
                }
                
                // if the ball collides with another ball in the array
                if (this.isCollision(ball)) {
                	
                	// if the ball collides in the x direction
                    if (this.x >= ball.x) {
                    	
                    	// invert the direction of the ball
                        this.dx  = -this.dx;
                    }
                    
                    // if the ball collides in the y direction
                    if(this.y >= ball.y) {
                    	
                    	// invert the direction of the ball
                        this.dy = -this.dy;
                    }
                }
            }
            
        }
        

        // draws the ball onto the screen
        public void draw(Graphics g) {
        	
        	// set the colour of the ball
            g.setColor(this.color);
            
            // draw the ball
            g.fillOval((int) (x - radius), (int) (y - radius),
                    (int)(2 * radius), (int)(2 * radius));
        }

        
        // run method for each ball so that they can move independently
        @Override
        public void run() {
        	
        	// infinite loop
            while(true) {
            	
            	// try
                try {
                	
                	// sleep the thread
                    Thread.sleep(10);
                    
                 // catch any exceptions
                } catch(Exception e) {
                	
                	// print error
                    e.printStackTrace(System.out);
                }
                
                // move the ball
                this.move();
            }
        }
    }
    
	// main program entry point
	public static void main(String[] args) {
		
		// give information regarding the program
		JOptionPane.showMessageDialog(null, "Program to show bouncing balls on a screen", "Welcome", JOptionPane.INFORMATION_MESSAGE);
		
		// infinite loop
		while (true) {
		
			// try to obtain valid number for the balls
			try {
		
				// take the number of balls as string
				String ballCountStr = JOptionPane.showInputDialog(null, "Enter in the number of balls\nMin: 1	Max: 500", "Ball Number", JOptionPane.INFORMATION_MESSAGE );
			
				// convert the string to an integer
				int ballCount = Integer.valueOf(ballCountStr);
				
				// if the ballCount is greater than 0 but less than 500
				if (ballCount > 0 && ballCount <= 500) {
				   
					 // run the GUI in the EDT instead of the main thread with the Runnable interface
					 javax.swing.SwingUtilities.invokeLater(new Runnable() {
	 	  
				 	 // method to run the threads
				      public void run() {
				     	 
				         // set up main window using a JFrame
				         JFrame frame = new JFrame("Colliding Balls");
				         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				         frame.setContentPane(new CollidingBalls(ballCount));
				         frame.pack();
				         frame.setVisible(true);
				         
				      	}
		  
				   });
		   
			   // exit from the loop if the input was valid
			   break;
	   
			    // if the number was greater than 500
				} else {
					
					// print error message
					JOptionPane.showMessageDialog(null, "Invalid input, please enter an integer less than 500", "Invalid", JOptionPane.INFORMATION_MESSAGE);
					
				}
	
    // catch any string or non-integers	
	} catch (NumberFormatException e) {
		
		// display error message
		JOptionPane.showMessageDialog(null, "Invalid input, please enter an integer", "Invalid", JOptionPane.INFORMATION_MESSAGE);
		
			}
		}
	
	}
}
