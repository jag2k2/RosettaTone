package uicomponents.renderer;

import imageprocessing.StaffImage;
import music.*;
import uicomponents.staffselector.StaffModeHolder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NoteDrawer {
    private static final File trebleClefFile = new File("./Images/Treble-clef.png");
    private static final File bassClefFile = new File("./Images/Bass-clef.png");
    private static final File noteFile = new File("./Images/Whole-Note.png");
    private static final File naturalFile = new File("./Images/Natural.png");
    private static final File sharpFile = new File("./Images/Sharp.png");
    private static final File flatFile = new File("./Images/Flat.png");

    private static final StaffImage trebleImage = new StaffImage(trebleClefFile, 0.5);
    private static final StaffImage bassImage = new StaffImage(bassClefFile, 0.4);
    private static final StaffImage noteImage = new StaffImage(noteFile, 0.22);
    private static final StaffImage naturalImage = new StaffImage(naturalFile, 0.4);
    private static final StaffImage sharpImage = new StaffImage(sharpFile, 0.4);
    private static final StaffImage flatImage = new StaffImage(flatFile, 0.4);

    private static final Staff trebleStaff = new Staff(trebleImage, 15, 3, 18, 26);
    private static final Staff bassStaff = new Staff(bassImage, 30, 0, 30, 38);

    private final Graphics2D graphics2D;

    public NoteDrawer(Graphics2D graphics2D){
        this.graphics2D = graphics2D;
    }

    protected void paintBackground(){
        graphics2D.setColor(Color.WHITE);
        int canvasWidth = (int) CanvasRender.getCanvasSize().getWidth();
        int canvasHeight = (int) CanvasRender.getCanvasSize().getHeight();
        graphics2D.fillRect(0,0,canvasWidth,canvasHeight);
    }

    public void drawEnabledStaffs(StaffModeHolder staffModeHolder){
        configLineDraw();
        for (Staff staff : enabledStaffs(staffModeHolder)){
            drawStaff(staff);
        }
    }

    public java.util.List<Staff> enabledStaffs(StaffModeHolder staffModeHolder){
        List<Staff> enabledStaffs = new ArrayList<>();

        if (staffModeHolder.trebleEnabled()){
            enabledStaffs.add(trebleStaff);
        }
        if (staffModeHolder.bassEnabled()){
            enabledStaffs.add(bassStaff);
        }
        return enabledStaffs;
    }

    public void drawStaff(Staff staff){
        int clefImageXPos = CanvasRender.getClefXOffset();
        int clefImageYPos = staff.getClefYOffset();
        graphics2D.drawImage(staff.getStaffImage(), null, clefImageXPos, clefImageYPos);
        int totalNumberOfLines = CanvasRender.getNumberOfLines();
        for (int i = 0; i < totalNumberOfLines; i++){
            if ((staff.getTopVisibleLine() <= i) && (i <= staff.getBottomVisibleLine()) && ((i % 2) == 0)){
                int lineYPos =  CanvasRender.getLineYOffset(i);
                int lineXPosStart = CanvasRender.getLineXStart();
                int lineXPosEnd = CanvasRender.getLineXEnd();
                graphics2D.drawLine(lineXPosStart, lineYPos, lineXPosEnd, lineYPos);
            }
        }
    }

    protected void configLineDraw(){
        graphics2D.setColor(Color.BLACK);
        int lineThickness = 2;
        graphics2D.setStroke(new BasicStroke(lineThickness));
    }

    protected void drawNotes(NoteList noteList, StaffModeHolder staffModeHolder){
        for (Note note : noteList){
            drawNote(note, noteList);
            drawAccidentals(note);
            drawHelperLines(note, staffModeHolder);
        }
    }

    protected void drawNote(Note note, NoteList noteList){
        int lineNumber = CanvasRender.getLineNumber(note);
        int noteX = CanvasRender.getNoteXOffset();
        int noteHeight = noteImage.getBufferedImage().getHeight();
        int noteY = CanvasRender.getLineYOffset(lineNumber) - (noteHeight / 2);

        int noteWidth = noteImage.getBufferedImage().getWidth();
        if (noteList.isSandwiched(note)) {
            noteX += noteWidth;
        }
        graphics2D.drawImage(noteImage.getBufferedImage(), null, noteX, noteY);
    }


    protected void drawAccidentals(Note note) {
        int lineNumber = CanvasRender.getLineNumber(note);
        int noteX = CanvasRender.getNoteXOffset();
        int noteHeight = noteImage.getBufferedImage().getHeight();
        int noteY = CanvasRender.getLineYOffset(lineNumber) - (noteHeight / 2);
        for (NoteAccidental accidental : note.getActiveAccidentals()) {
            if (accidental == NoteAccidental.SHARP) {
                BufferedImage sharpBufferedImage = sharpImage.getBufferedImage();
                int sharpXPos = noteX - (int) (sharpImage.getBufferedImage().getWidth() * 1.3);
                int sharpYPos = noteY - (sharpBufferedImage.getHeight() / 3);
                graphics2D.drawImage(sharpBufferedImage, null, sharpXPos, sharpYPos);
            }
        }
    }

    protected void drawHelperLines(Note note, StaffModeHolder staffModeHolder){
        int lineNumber = CanvasRender.getLineNumber(note);
        int topVisibleLine = trebleStaff.getTopVisibleLine();
        int bottomVisibleLine = bassStaff.getBottomVisibleLine();

        if (!staffModeHolder.trebleEnabled()) {
            topVisibleLine = bassStaff.getTopVisibleLine();
        }

        if (!staffModeHolder.bassEnabled()) {
            bottomVisibleLine = trebleStaff.getBottomVisibleLine();
        }

        configLineDraw();
        for (int i = lineNumber; i < topVisibleLine; i++){
            drawHelperLine(i);
        }
        for (int i = lineNumber; i > bottomVisibleLine; i--){
            drawHelperLine(i);
        }

        if (staffModeHolder.trebleEnabled() && staffModeHolder.bassEnabled()){
            if(lineNumber > trebleStaff.getBottomVisibleLine() && lineNumber < bassStaff.getTopVisibleLine()){
                drawHelperLine(lineNumber);
            }
        }
    }

    protected void drawHelperLine(int lineNumber){
        int helperLineYPos = CanvasRender.getLineYOffset(lineNumber);
        if ((lineNumber % 2) == 0) {
            int lineXPosStart = CanvasRender.getNoteXOffset() - 2;
            int lineXPosEnd = lineXPosStart + noteImage.getBufferedImage().getWidth() + 2;
            graphics2D.drawLine(lineXPosStart, helperLineYPos, lineXPosEnd, helperLineYPos);
        }
    }
}
