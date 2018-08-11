/*
* File: StoplightConsole.java
* ---------------------------
* This program illustrates the construction of a simple GUI.
*/
import acm.program.*;
import java.awt.event.*;
import javax.swing.*;
/**
* This class displays three buttons at the south edge of the window.
* The name of the button is echoed on the console each time a button
* is pressed.
*/
public class StoplightConsole extends ConsoleProgram {
    /** Initialize the GUI */
    public void init() {
        add(new JButton("Green"), SOUTH);
        add(new JButton("Yellow"), SOUTH);
        add(new JButton("Red"), SOUTH);
        addActionListeners();
    }
    /** Listen for a button action */
    public void actionPerformed(ActionEvent e) {
        println(e.getActionCommand());
    }
}