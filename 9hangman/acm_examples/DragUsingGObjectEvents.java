/*
* File: DragUsingGObjectEvents.java
* ---------------------------------
* This implementation illustrates the technique of assigning
* listeners to GObjects.
*/
import java.awt.*;
import java.awt.event.*;
import acm.graphics.*;
import acm.program.*;
/** This class displays a mouse-draggable rectangle and oval */
public class DragUsingGObjectEvents extends GraphicsProgram {
    /** Initializes the program */
    public void init() {
        GRect rect = new GRect(100, 100, 150, 100);
        rect.setFilled(true);
        rect.setColor(Color.RED);
        rect.addMouseListener(this);
        rect.addMouseMotionListener(this);
        add(rect);
        GOval oval = new GOval(300, 115, 100, 70);
        oval.setFilled(true);
        oval.setColor(Color.GREEN);
        oval.addMouseListener(this);
        oval.addMouseMotionListener(this);
        add(oval);
    }
    /** Called on mouse press to record the coordinates of the click */
    public void mousePressed(MouseEvent e) {
        last = new GPoint(e.getPoint());
    }
    /** Called on mouse drag to reposition the object */
    public void mouseDragged(MouseEvent e) {
        GObject gobj = (GObject) e.getSource();
        gobj.move(e.getX() - last.getX(), e.getY() - last.getY());
        last = new GPoint(e.getPoint());
    }
    /** Called on mouse click to move this object to the front */
    public void mouseClicked(MouseEvent e) {
        GObject gobj = (GObject) e.getSource();
        gobj.sendToFront();
    }
    /* Private instance variables */
    private GPoint last;
}