package uicomponents.renderer;

import imageprocessing.StaffImage;
import music.Note;
import music.Staff;

import java.awt.*;
import java.io.File;

public class CanvasRender {
    private static final int canvasWidth = 750;
    private static final int canvasHeight = 750;
    private static final int numberOfLines = 52;
    private static final int lineSpacing = 15;
    private static final int leftMargin = 100;
    private static final int lineLength = 500;
    private static final int noteXSpacing = 200;

    static public Dimension getCanvasSize(){
        return new Dimension(canvasWidth, canvasHeight);
    }

    static public int getLineNumber(Note note){
        return numberOfLines - note.getLineNumber() - 1;
    }

    static public int getNumberOfLines(){
        return numberOfLines;
    }

    static public int getLineXStart(){
        return leftMargin;
    }

    static public int getLineXEnd(){
        return leftMargin + lineLength;
    }

    static public int getLineYOffset(int lineNumber){
        return lineNumber * lineSpacing;
    }

    static public int getClefXOffset(){
        return leftMargin;
    }

    static public int getLineSpacing(){
        return lineSpacing;
    }

    static public int getNoteXOffset(){
        return leftMargin + noteXSpacing;
    }
}
