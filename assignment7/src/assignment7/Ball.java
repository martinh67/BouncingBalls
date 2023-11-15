package assignment7;

//imports required
import java.awt.*;
import java.util.Formatter;
import javax.swing.*;
import java.util.Random;

//extends JPanel to override the paintComponent() for customisation
public class Ball extends JPanel {
	
	// container box's width and height
	private static final int CONTAINER_WIDTH = 700, CONTAINER_HEIGHT = 500;
	
	// radius of the ball
	private float radius;
	
	// position of the ball
	private float x, y;
	
	// speed of the ball
	private float dx, dy;
	
	// constructor to render GUI objects and start the ball bouncing
	public Ball() {
		   
		  // set the radius
		  this.radius = 50;
		   
		  // set the x and y
		  this.x = getRandomNumberInRange(1, CONTAINER_WIDTH);
		  this.y = getRandomNumberInRange(1, CONTAINER_HEIGHT); 
		  
		  // set the speed
		  this.dx = getRandomNumberInRange(1, 10);
		  this.dy = getRandomNumberInRange(1, 10);
		   
		  // set the size of the container
	   this.setPreferredSize(new Dimension(CONTAINER_WIDTH, CONTAINER_HEIGHT));
	
	   // start the ball bouncing in its own thread
	   Thread thread = new Thread() {
	 	  
	 	 // the run method
	      public void run() {
	     	 
	     	// an infinite loop
	         while (true) { 
	         	
	         	// move the ball
	         	this.move();
	
	             // repaint the graphics
	             repaint();
	            
	            // delay for timing control and give other threads a chance
	            try {
	         	  
	         	  // sleep for 15 milliseconds
	               Thread.sleep(15);
	
	            // catch any interrupted exceptions
	            } catch (InterruptedException e) {
	         	   
	         	   // record error
	         	   e.printStackTrace();
	            }
	         }
	      }
	
	     // method to move the ball within the container
			private void move() {
				
			       // calculate the ball's new position
			       x += dx;
			       y += dy;
			       
			       // check if the ball is crossing the lower x bound
			       if (x - radius < 0) {
			    	  
			    	  // invert the direction
			          dx = -dx; 
			          
			          // reposition the ball
			          x = radius;
			          
			       // check if the ball is crossing the upper x bound
			       } else if (x + radius > CONTAINER_WIDTH) {
			    	   
			    	  // invert the direction
			          dx = -dx;
			          
			          // reposition the ball
			          x = CONTAINER_WIDTH - radius;
			       }
			       
			       // check if the ball is crossing the lower y bound
			       if (y - radius < 0) {
			    	   
			    	  // invert the direction
			          dy = -dy;
			          
			          // reposition the ball
			          y = radius;
			          
			       // check if the ball is crossing the upper y bound
			       } else if (y + radius > CONTAINER_HEIGHT) {
			    	  
			    	  // invert the direction
			          dy = -dy;
			          
			          // reposition the ball
			          y = CONTAINER_HEIGHT - radius;
			          
			       }
				
			}
	   };
	   
	   // start the thread
	   thread.start();
	}
	
	// method to get a random number within a defined range
	private static int getRandomNumberInRange(int min, int max) {
	
		   // if the maximum is less than the minimum
	    if (min >= max) {
	 	   
	 	   // throw exception
	        throw new IllegalArgumentException("Max must be greater than min");
	        
	    // otherwise
	    } else {
	 	   
	 	   // create new random object
	        Random random = new Random();
	        
	        // return a random number in the range
	        return random.nextInt((max - min) + 1) + min;
	  
	    }
	}
	
	// custom rendering codes for drawing the JPanel
	@Override
	public void paintComponent(Graphics g) {
		   
		  // paint the background
	   super.paintComponent(g);    // Paint background
	
	   // draw the box
	   g.setColor(Color.WHITE);
	   g.fillRect(0, 0, CONTAINER_WIDTH, CONTAINER_HEIGHT);
	
	   // draw the ball onto the screen
	   g.setColor(Color.GREEN);
	   g.fillOval((int) (x - radius), (int) (y - radius),
	         (int)(2 * radius), (int)(2 * radius));
	
	   // display the ball's position and speed
	   g.setColor(Color.BLACK);
	   g.setFont(new Font("Times New", Font.PLAIN, 12));
	   StringBuilder stringBuilder = new StringBuilder();
	   Formatter formatter = new Formatter(stringBuilder);
	   formatter.format("Ball Position = (%3.0f,%3.0f)\n "
	   		+ "Ball Speed = (%2.0f,%2.0f)", x, y, dx, dy);
	   g.drawString(stringBuilder.toString(), 20, 30);
	   
	}
	
	// main program entry point
	public static void main(String[] args) {
		   
	   // run the GUI in the EDT instead of the main thread with the Runnable interface
	   javax.swing.SwingUtilities.invokeLater(new Runnable() {
	 	  
		      // method to run the threads
		      public void run() {
		        	 
			      // set up main window using a JFrame
			      JFrame frame = new JFrame("Ball");
			      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			      frame.setContentPane(new Ball());
			      frame.pack();
			      frame.setVisible(true);
	   
	      }
		      
	   });
	}

}

