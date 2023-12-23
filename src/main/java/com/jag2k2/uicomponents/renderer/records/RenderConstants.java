package com.jag2k2.uicomponents.renderer.records;

import java.awt.*;

public class RenderConstants {
    public static final String notePath = "Whole-Note.png";
    public static final String naturalPath = "Natural.png";
    public static final String sharpPath = "Sharp.png";
    public static final String flatPath = "Flat.png";

    public static final double noteResizeFactor = 0.22;
    public static final double accidentalResizeFactor = 0.40;

    public static final String trebleClefPath = "Treble-clef.png";
    public static final double trebleResizeFactor = 0.5;

    public static final String bassClefPath = "Bass-clef.png";
    public static final double bassResizeFactor = 0.4;


    public static final StaffConstants trebleStaff = new StaffConstants(0.5, 15, 3, 18, 26);
    public static final StaffConstants bassStaff = new StaffConstants(0.4, 30, 0, 30, 38);

    public static final int canvasWidth = 1200;
    public static final int canvasHeight = 800;
    public static final Dimension canvasSize = new Dimension(canvasWidth, canvasHeight);

    private static final int rangeIndicatorWidth = 100;
    public static final Dimension rangeIndicatorSize = new Dimension(rangeIndicatorWidth, canvasHeight);

    public static final int numberOfLines = 52;
    public static final int lineSpacing = 15;
    public static final int ledgerLineThickness = 2;

    public static final int topMargin = 10;
    public static final int leftMargin = 10;
    public static final int rightMargin = 10;
    public static final int noteXSpacing = 135;
    public static final int nameFontSize = 35;

    public static final int limitRenderXOffset = 50;
    public static final int limitDotDiameter = 20;
    public static final int limitLineThickness = 5;

    public static final int flashcardCount = 8;

    public static final int hitXOffset = 850;
    public static final int missXOffset = 900;
    public static final int hitYOffset = 50;
    public static final int missYOffset = 110;

    static public int getLineYOffset(int lineNumber){
        return topMargin + lineNumber * lineSpacing;
    }

    static public int getNoteXOffset(int column){
        return leftMargin + (column + 1) * noteXSpacing;
    }
}
