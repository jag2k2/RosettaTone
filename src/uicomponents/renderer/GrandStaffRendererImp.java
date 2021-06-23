package uicomponents.renderer;

import imageprocessing.StaffImage;
import notification.StaffChangeObserver;
import music.*;
import uicomponents.staffselector.StaffOptions;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GrandStaffRendererImp extends Component implements StaffChangeObserver {
    private static final int canvasWidth = 750;
    private static final int canvasHeight = 750;

    private static final int numberOfLines = 52;
    private static final int lineSpacing = 15;
    private static final int leftMargin = 100;
    private static final int lineLength = 500;
    private static final int noteXSpacing = 200;

    private final Staff trebleStaff;
    private final Staff bassStaff;

    private final StaffImage noteImage;
    private final StaffImage naturalImage;
    private final StaffImage sharpImage;
    private final StaffImage flatImage;

    private final NoteState noteState;
    private final StaffSelection staffSelection;
    private final JTextArea textArea;

    public GrandStaffRendererImp(NoteState noteState, StaffSelection staffSelection, JTextArea texArea){
        this.noteState = noteState;
        this.staffSelection = staffSelection;
        this.textArea = texArea;

        File trebleClefFile = new File("./Images/Treble-clef.png");
        File bassClefFile = new File("./Images/Bass-clef.png");

        this.trebleStaff = new Staff(trebleClefFile, 0.5, 15, 3, 18, 26);
        this.bassStaff = new Staff(bassClefFile, 0.4, 30, 0, 30, 38);

        File noteFile = new File("./Images/Whole-Note.png");
        File naturalFile = new File("./Images/Natural.png");
        File sharpFile = new File("./Images/Sharp.png");
        File flatFile = new File("./Images/Flat.png");

        noteImage = new StaffImage(noteFile, 0.22);
        naturalImage = new StaffImage(naturalFile, 0.4);
        sharpImage = new StaffImage(sharpFile, 0.4);
        flatImage = new StaffImage(flatFile, 0.4);
    }

    @Override
    public void update() {
        textArea.setText(noteState.getActiveNotes().toString());
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(canvasWidth, canvasHeight);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;
        paintBackground(graphics2D);
        drawEnabledStaffs(graphics2D);
        drawNotes(graphics2D, noteState.getActiveNotes());
    }

    protected void paintBackground(Graphics2D graphics2D){
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0,0,canvasWidth,canvasHeight);
    }

    public void drawEnabledStaffs(Graphics2D graphics2D){
        configLineDraw(graphics2D);
        for (Staff staff : enabledStaffs()){
            drawStaff(graphics2D, staff);
        }
    }

    public List<Staff> enabledStaffs(){
        List<Staff> enabledStaffs = new ArrayList<>();

        if (trebleEnabled()){
            enabledStaffs.add(trebleStaff);
        }
        if (bassEnabled()){
            enabledStaffs.add(bassStaff);
        }
        return enabledStaffs;
    }

    public boolean trebleEnabled(){
        StaffOptions selectedOption = staffSelection.getSelection();
        return (selectedOption == StaffOptions.Treble || selectedOption == StaffOptions.Grand);
    }

    public boolean bassEnabled(){
        StaffOptions selectedOption = staffSelection.getSelection();
        return (selectedOption == StaffOptions.Bass || selectedOption == StaffOptions.Grand);
    }

    public void drawStaff(Graphics2D graphics2D, Staff staff){
        StaffImage staffImage = new StaffImage(staff.getImageFile(), staff.getScaleFactor());
        graphics2D.drawImage(staffImage.getBufferedImage(), null, leftMargin, staff.getClefYOffset(lineSpacing));
        for (int i = 0; i < numberOfLines; i++){
            if ((staff.getTopVisibleLine() <= i) && (i <= staff.getBottomVisibleLine()) && ((i % 2) == 0)){
                int linePosition =  i * lineSpacing;
                graphics2D.drawLine(leftMargin, linePosition, leftMargin + lineLength, linePosition);
            }
        }
    }

    protected void configLineDraw(Graphics2D graphics2D){
        graphics2D.setColor(Color.BLACK);
        int lineThickness = 2;
        graphics2D.setStroke(new BasicStroke(lineThickness));
    }

    protected void drawNotes(Graphics2D graphics2D, ActiveNotes activeNotes){
        for (Note note : activeNotes){
            drawNote(graphics2D, note, activeNotes);
            drawAccidentals(graphics2D, note);
            drawHelperLines(graphics2D, note);
        }
    }

    protected void drawNote(Graphics2D graphics2D, Note note, ActiveNotes activeNotes){
        int lineNumber = numberOfLines - note.getLineNumber() - 1;
        int noteX = leftMargin + noteXSpacing;
        int noteHeight = noteImage.getBufferedImage().getHeight();
        int noteY = (lineNumber * lineSpacing) - (noteHeight / 2);

        int noteWidth = noteImage.getBufferedImage().getWidth();
        if (activeNotes.isShifted(note)) {
            noteX += noteWidth;
        }
        graphics2D.drawImage(noteImage.getBufferedImage(), null, noteX, noteY);
    }


    protected void drawAccidentals(Graphics2D graphics2D, Note note) {

        int lineNumber = numberOfLines - note.getLineNumber() - 1;
        int noteX = leftMargin + noteXSpacing;
        int noteHeight = noteImage.getBufferedImage().getHeight();
        int noteY = (lineNumber * lineSpacing) - (noteHeight / 2);
        for (NoteAccidental accidental : note.getActiveAccidentals()) {
            if (accidental == NoteAccidental.SHARP) {
                BufferedImage sharpBufferedImage = sharpImage.getBufferedImage();
                int sharpXPos = noteX - (int) (sharpBufferedImage.getWidth() * 1.3);
                int sharpYPos = noteY - (sharpBufferedImage.getHeight() / 3);
                graphics2D.drawImage(sharpBufferedImage, null, sharpXPos, sharpYPos);
            }
        }
    }

    protected void drawHelperLines(Graphics2D graphics2D, Note note){
        int lineNumber = numberOfLines - note.getLineNumber() - 1;
        int topVisibleLine = trebleStaff.getTopVisibleLine();
        int bottomVisibleLine = bassStaff.getBottomVisibleLine();

        if (!trebleEnabled()) {
            topVisibleLine = bassStaff.getTopVisibleLine();
        }

        if (!bassEnabled()) {
            bottomVisibleLine = trebleStaff.getBottomVisibleLine();
        }

        configLineDraw(graphics2D);
        for (int i = lineNumber; i < topVisibleLine; i++){
            drawHelperLine(graphics2D, i);
        }
        for (int i = lineNumber; i > bottomVisibleLine; i--){
            drawHelperLine(graphics2D, i);
        }

        if (trebleEnabled() && bassEnabled()){
            if(lineNumber > trebleStaff.getBottomVisibleLine() && lineNumber < bassStaff.getTopVisibleLine()){
                drawHelperLine(graphics2D, lineNumber);
            }
        }
    }

    protected void drawHelperLine(Graphics2D graphics2D, int lineNumber){
        int noteX = leftMargin + noteXSpacing;
        int helperLineYPos = (lineSpacing * lineNumber);
        if ((lineNumber % 2) == 0) {
            graphics2D.drawLine(noteX - 2, helperLineYPos, noteX + 2 + noteImage.getBufferedImage().getWidth(), helperLineYPos);
        }
    }
}
