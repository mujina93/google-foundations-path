/*
* File: HangmanCanvas.java
* ------------------------
* This file keeps track of the Hangman display.
*/
import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
    /** Resets the display so that only the scaffold appears */
    public void reset() {
        width = (double)getWidth();
        height = (double)getHeight();

        GLine scaffold = new GLine(width*0.1, height*0.1, 
                                    width*0.1, height*0.1+SCAFFOLD_HEIGHT);
        add(scaffold);

        GLine beam = new GLine(width*0.1, height*0.1, 
                                width*0.1+BEAM_LENGTH, height*0.1);
        add(beam);

        GLine rope = new GLine(width*0.1+BEAM_LENGTH, height*0.1,
                                width*0.1+BEAM_LENGTH, height*0.1+ROPE_LENGTH);
        add(rope);

        // last point touched by the 'rope' line. Useful for drawing next pieces
        // using this as a relative point, instead of using every time
        // absolute coordinates
        currentDrawingPoint = new GPoint(width*0.1+BEAM_LENGTH, height*0.1+ROPE_LENGTH);    
    }
    /**
    * Updates the word on the screen to correspond to the current
    * state of the game. The argument string shows what letters have
    * been guessed so far; unguessed letters are indicated by hyphens.
    */
    public void displayWord(String word) {
        if(labelAdded){
            remove(label);
        }
        label = new GLabel(word);
		label.setFont("SansSerif-50");
		double x = width*0.1;
		double y = height*0.1+SCAFFOLD_HEIGHT+WORD_OFFSET+label.getAscent();
		add(label, x, y);
        labelAdded = true;
    }
    /**
    * Updates the display to correspond to an incorrect guess by the
    * user. Calling this method causes the next body part to appear
    * on the scaffold and adds the letter to the list of incorrect
    * guesses that appears at the bottom of the window.
    */
    public void noteIncorrectGuess(char letter, int errors) throws Exception{
        switch(errors){
            case 0:
                break;
            case 1:
                GOval head = new GOval(2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
                add(head, currentDrawingPoint.getX() - HEAD_RADIUS, 
                            currentDrawingPoint.getY());
                // last drawing point: the chin (lowest part of the cirlce)
                currentDrawingPoint = new GPoint(currentDrawingPoint.getX(),
                                        currentDrawingPoint.getY()+2*HEAD_RADIUS);
                break;
            case 2:
                // initialize GPen for first time
                // body
                pen = new GPen();
                add(pen, currentDrawingPoint);
                pen.drawLine(0, BODY_LENGTH);
                break;
            case 3:
                // left arm
                pen.move(0, - BODY_LENGTH + ARM_OFFSET_FROM_HEAD);
                currentDrawingPoint = new GPoint(pen.getX(), pen.getY());
                pen.drawLine(- UPPER_ARM_LENGTH, 0);
                pen.drawLine(0, LOWER_ARM_LENGTH);
                break;
            case 4:
                // right arm
                pen.move(- pen.getX() + currentDrawingPoint.getX(),
                        - pen.getY() + currentDrawingPoint.getY());
                currentDrawingPoint = new GPoint(pen.getX(), pen.getY());
                pen.drawLine(UPPER_ARM_LENGTH, 0);
                pen.drawLine(0, LOWER_ARM_LENGTH);
                pen.move(- pen.getX() + currentDrawingPoint.getX(),
                        - pen.getY() + currentDrawingPoint.getY());
                pen.move(0, BODY_LENGTH - ARM_OFFSET_FROM_HEAD);
                break;
            case 5:
                // left leg
                currentDrawingPoint = new GPoint(pen.getX(), pen.getY());
                pen.drawLine(-HIP_WIDTH,0);
                pen.drawLine(0,LEG_LENGTH);
                pen.move(- pen.getX() + currentDrawingPoint.getX(),
                        - pen.getY() + currentDrawingPoint.getY());
                break;
            case 6:
                // right leg
                currentDrawingPoint = new GPoint(pen.getX(), pen.getY());
                pen.drawLine(HIP_WIDTH,0);
                pen.drawLine(0,LEG_LENGTH);
                pen.move(- pen.getX() + currentDrawingPoint.getX(),
                        - pen.getY() + currentDrawingPoint.getY());
                break;
            case 7:
                // left foot
                currentDrawingPoint = new GPoint(pen.getX(), pen.getY());
                pen.move(-HIP_WIDTH,0);
                pen.move(0,LEG_LENGTH);
                pen.drawLine(-FOOT_LENGTH,0);
                pen.move(- pen.getX() + currentDrawingPoint.getX(),
                        - pen.getY() + currentDrawingPoint.getY());
                break;
            case 8:
                // right foot
                pen.move(HIP_WIDTH,0);
                pen.move(0,LEG_LENGTH);
                pen.drawLine(FOOT_LENGTH,0);
                break;
            default:
                throw new Exception("Unexpected error case when drawing hangman");
        }
    }
    // members
    private double width;
    private double height;
    private GLabel label;
    private boolean labelAdded = false;
    private GPoint currentDrawingPoint;
    private GPen pen;
    /* Constants for the simple version of the picture (in pixels) */
    private static final int SCAFFOLD_HEIGHT = 360;
    private static final int BEAM_LENGTH = 144;
    private static final int ROPE_LENGTH = 18;
    private static final int HEAD_RADIUS = 36;
    private static final int BODY_LENGTH = 144;
    private static final int ARM_OFFSET_FROM_HEAD = 28;
    private static final int UPPER_ARM_LENGTH = 72;
    private static final int LOWER_ARM_LENGTH = 44;
    private static final int HIP_WIDTH = 36;
    private static final int LEG_LENGTH = 108;
    private static final int FOOT_LENGTH = 28;
    private static final double WORD_OFFSET = 5;
}