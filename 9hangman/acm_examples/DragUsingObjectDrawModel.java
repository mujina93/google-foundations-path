/*
* File: DragUsingObjectDrawModel.java
* -----------------------------------
* This implementation illustrates the technique of using callback
* methods in the style of the objectdraw package.
*/
import java.awt.*;
import acm.graphics.*;
import acm.program.*;
/** This class displays a mouse-draggable rectangle and oval */
public class DragUsingObjectDrawModel extends GraphicsProgram {
    /** Initializes the program */
    public void init() {
        GRect rect = new GRect(100, 100, 150, 100);
        rect.setFilled(true);
        rect.setColor(Color.RED);
        add(rect);
        GOval oval = new GOval(300, 115, 100, 70);
        oval.setFilled(true);
        oval.setColor(Color.GREEN);
        add(oval);
    }
    /** Called on mouse press to record the coordinates of the click */
    public void mousePressed(GPoint pt) {
        last = pt;
        gobj = getElementAt(last);
    }
    /** Called on mouse drag to reposition the object */
    public void mouseDragged(GPoint pt) {
        if (gobj != null) {
            gobj.move(pt.getX() - last.getX(), pt.getY() - last.getY());
            last = pt;
        }
    }
    /** Called on mouse click to move this object to the front */
    public void mouseClicked(GPoint pt) {
        if (gobj != null) gobj.sendToFront();
    }
    /* Private instance variables */
    private GObject gobj;
    private GPoint last;
}