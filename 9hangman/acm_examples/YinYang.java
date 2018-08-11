/*
* File: YinYang.java
* ------------------
* This program draws the Taoist yin-yang symbol at the center of
* the graphics window. The height and width of the entire figure
* are both specified by the constant FIGURE_SIZE.
*/
import acm.graphics.*;
import acm.program.*;
import java.awt.*;
public class YinYang extends GraphicsProgram {
/** Runs the program */
public void run() {
    double x = getWidth() / 2;
    double y = getHeight() / 2;
    double r = FIGURE_SIZE / 2;
    GArc bigBlack = new GArc(x - r, y - r, 2 * r, 2 * r, -90, 180);
    bigBlack.setFilled(true);
    add(bigBlack);
    GArc smallWhite = new GArc(x - r / 2, y - r, r, r, -90, 180);
    smallWhite.setFilled(true);
    smallWhite.setColor(Color.WHITE);
    add(smallWhite);
    GArc smallBlack = new GArc(x - r / 2, y, r, r, 90, 180);
    smallBlack.setFilled(true);
    add(smallBlack);
    GArc outerCircle = new GArc(x - r, y - r, 2 * r, 2 * r, 0, 360);
    add(outerCircle);
}
/* Private constants */
private static final double FIGURE_SIZE = 150;
}