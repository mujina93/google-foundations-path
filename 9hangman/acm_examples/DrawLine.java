/*
* File: DrawLine.java
* -------------------
* This program allows users to create lines on the graphics
* canvas by clicking and dragging with the mouse. The line
* is redrawn from the original point to the new endpoint, which
* makes it look as if it is connected with a rubber band.
*/
import acm.graphics.*;
import acm.program.*;
import java.awt.event.*;
/** This class allows users to draw lines on the canvas */
public class DrawLine extends GraphicsProgram {
    /** Initializes the program by enabling the mouse listeners */
    public void init() {
        addMouseListeners();
    }
    /** Called on mouse press to create a new line */
    public void mousePressed(MouseEvent e) {
        line = new GLine(e.getX(), e.getY(), e.getX(), e.getY());
        add(line);
    }
    /** Called on mouse drag to reset the endpoint */
    public void mouseDragged(MouseEvent e) {
        line.setEndPoint(e.getX(), e.getY());
    }
    /* Private instance variables */
    private GLine line;
}