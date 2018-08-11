/*
* File: BouncingBallUsingThreads.java
* -----------------------------------
* This file implements a simple bouncing ball by creating
* a RunnableBall class and executing it in its own thread.
*/
import acm.graphics.*;
import acm.program.*;
public class BouncingBallUsingThreads extends GraphicsProgram {
    /** Initialize the ball and its velocity components */
    public void init() {
        ball = new RunnableGBall(BALL_RADIUS);
        ball.setEnclosureSize(getWidth(), getHeight());
        ball.setVelocity(2, 1);
        add(ball, getWidth() / 2, getHeight() / 2);
    }
    /** Create a thread to bounce the ball */
    public void run() {
        waitForClick();
        new Thread(ball).start();
    }
    /* Private constants */
    private static final double BALL_RADIUS = 10;
    /* Private instance variables */
    private RunnableGBall ball;
}