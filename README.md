# Bouncing Balls

My seventh Java assignment completed on **02/05/2021**:

1. Create a class called Ball. Write a program that bounces an instance of Ball inside a JPanel. The Ball must start in a random location, move in a random direction, and must bounce when it reaches the edge of the JPanel and continue in the opposite direction. The Ball should be updated using a Runnable. Test the functionality in a JFrame.

2. Write a second JPanel class that bounces multiple Ball objects; the number is specified by the user. Assign different colors to different Balls (you can have 10 unique colors and re-use colours after that). The Balls can move independently, passing through each other. Each Ball should have its own thread, rather than one thread updating an array of Balls.

3. Write a third JPanel class and a new Ball class, this time with code so that the Balls bounce off each other. A collision will occur between two Ball objects when the distance between their centres is less than the sum of their radii. (Note collision detection will require synchronization between threads.)
