package uicomponents.renderer.records;

import music.Note;
import music.NoteName;

import java.awt.*;


public class RenderConstants {
    public static final String notePath = "/images/Whole-Note.png";
    public static final String naturalPath = "/images/Natural.png";
    public static final String sharpPath = "/images/Sharp.png";
    public static final String flatPath = "/images/Flat.png";

    public static final double noteResizeFactor = 0.22;
    public static final double accidentalResizeFactor = 0.40;

    public static final String trebleClefPath = "/images/Treble-clef.png";
    public static final double trebleResizeFactor = 0.5;

    public static final String bassClefPath = "/images/Bass-clef.png";
    public static final double bassResizeFactor = 0.4;


    public static final StaffConstants trebleStaff = new StaffConstants(0.5, 15, 3, 18, 26);
    public static final StaffConstants bassStaff = new StaffConstants(0.4, 30, 0, 30, 38);

    private static final int canvasWidth = 1200;
    private static final int canvasHeight = 750;
    public static final Dimension canvasSize = new Dimension(canvasWidth, canvasHeight);

    private static final int rangeIndicatorWidth = 100;
    public static final Dimension rangeIndicatorSize = new Dimension(rangeIndicatorWidth, canvasHeight);

    public static final int numberOfLines = 52;
    public static final int lineSpacing = 15;
    public static final int lineThickness = 2;

    public static final int leftMargin = 10;
    public static final int noteXSpacing = 135;
    public static final int nameFontSize = 35;

    public static final int limitRenderXOffset = 50;
    public static final int limitDotDiameter = 20;

    static public int getLineNumber(Note note){
        return (numberOfLines - 1) - ((note.getNoteName().getPosition() + (note.getOctave() * 7)) - 5);
    }
    
    static public int getLineXStart() { return leftMargin; }

    static public int getLineXEnd(){
        return canvasWidth - leftMargin - 20;
    }

    static public int getLineYOffset(int lineNumber){
        return lineNumber * lineSpacing;
    }

    static public int getClefXOffset(){
        return leftMargin;
    }

    static public int getNoteXOffset(int column){
        return leftMargin + column * noteXSpacing;
    }

    static public Note getNote(int lineNumber){
        int notePosition = (numberOfLines - 1) - lineNumber + 5;
        int octave = notePosition / 7;
        int octavePosition = notePosition % 7;
        return new Note(NoteName.values()[octavePosition], octave);
    }
}
