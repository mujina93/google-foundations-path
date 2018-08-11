/*
* File: Stoplight.java
* --------------------
* This class implements a stoplight as a compound graphical object.
*/
import acm.graphics.*;
import acm.util.*;
import java.awt.*;
/**
* This class represents a graphical stoplight with its origin point
* at the center.
*/
public class Stoplight extends GCompound {
    /* Public constants for the colors */
    public static final Color RED = Color.RED;
    public static final Color YELLOW = Color.YELLOW;
    public static final Color GREEN = Color.GREEN;
    /** Creates a new Stoplight object, which is initially red */
    public Stoplight() {
        GRect frame = new GRect(STOPLIGHT_WIDTH, STOPLIGHT_HEIGHT);
        frame.setFilled(true);
        frame.setColor(Color.DARK_GRAY);
        add(frame, -STOPLIGHT_WIDTH / 2, -STOPLIGHT_HEIGHT / 2);
        redLamp = createLamp(0, -STOPLIGHT_HEIGHT / 4);
        yellowLamp = createLamp(0, 0);
        greenLamp = createLamp(0, STOPLIGHT_HEIGHT / 4);
        add(redLamp);
        add(yellowLamp);
        add(greenLamp);
        setState(RED);
    }
    /** Changes the state of the stoplight to the indicated color */
    public void setState(Color color) {
        state = color;
        redLamp.setColor((state == RED) ? RED : Color.GRAY);
        yellowLamp.setColor((state == YELLOW) ? YELLOW : Color.GRAY);
        greenLamp.setColor((state == GREEN) ? GREEN : Color.GRAY);
    }
    /** Returns the current state of the stoplight */
    public Color getState() {
        return state;
    }
    /** Advances the stoplight to the next state */
    public void advance() {
        if (state == RED) {
            setState(GREEN);
        } else if (state == YELLOW) {
            setState(RED);
        } else if (state == GREEN) {
            setState(YELLOW);
        } else {
            throw new ErrorException("Illegal stoplight state");
        }
    }
    /* Creates a new GOval to represent one of the three lamps */
    private GOval createLamp(double x, double y) {
        GOval lamp = new GOval(x - LAMP_RADIUS, y - LAMP_RADIUS,
        2 * LAMP_RADIUS, 2 * LAMP_RADIUS);
        lamp.setFilled(true);
        return lamp;
    }
    /* Private private constants */
    private static final double STOPLIGHT_WIDTH = 50;
    private static final double STOPLIGHT_HEIGHT = 100;
    private static final double LAMP_RADIUS = 10;
    /* Private instance variables */
    private Color state;
    private GOval redLamp;
    private GOval yellowLamp;
    private GOval greenLamp;
}