/*
* File: StoplightGraphics.java
* ----------------------------
* This program illustrates the construction of a simple GUI using a
* GraphicsProgram as the main class.
*/
import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
* This class displays four buttons at the south edge of the window.
* Pressing a button lights the indicated lamp in the stoplight or
* advances the stoplight to its next configuration.
*/
public class StoplightGraphics extends GraphicsProgram {
    /** Initialize the buttons and create the stoplight */
    public void init() {
        add(new JButton("Green"), SOUTH);
        add(new JButton("Yellow"), SOUTH);
        add(new JButton("Red"), SOUTH);
        add(new JButton("Advance"), SOUTH);
        signal = new Stoplight();
        add(signal, getWidth() / 2, getHeight() / 2);
        addActionListeners();
    }
    /** Listen for a button action */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Advance")) {
            signal.advance();
        } else if (command.equals("Red")) {
            signal.setState(Stoplight.RED);
        } else if (command.equals("Yellow")) {
            signal.setState(Stoplight.YELLOW);
        } else if (command.equals("Green")) {
            signal.setState(Stoplight.GREEN);
        }
    }
    /* Private instance variables */
    private Stoplight signal;
}