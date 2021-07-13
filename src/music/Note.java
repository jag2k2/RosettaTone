package music;

import statemodels.NoteDrawable;
import uicomponents.renderer.records.RenderConstants;
import utility.NoteCollection;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.HashSet;

public class Note implements Comparable<Note>, NoteDrawable {

    private final NoteName noteName;
    private final int octave;
    private final Set<NoteAccidental> accidentals = new HashSet<>();

    public Note(NoteName noteName, int octave) {
        this.noteName = noteName;
        this.octave = octave;
        accidentals.add(NoteAccidental.NATURAL);
    }

    public Note(NoteName noteName, int octave, NoteAccidental noteAccidental) {
        this.noteName = noteName;
        this.octave = octave;
        accidentals.add(noteAccidental);

    }

    public Note(NoteName noteName, int octave, Set<NoteAccidental> noteAccidentals){
        this.noteName = noteName;
        this.octave = octave;
        accidentals.addAll(noteAccidentals);
    }

    public int getOctave() {
        return octave;
    }

    public NoteName getNoteName(){
        return noteName;
    }

    public Set<NoteAccidental> getActiveAccidentals() {
        return accidentals;
    }

    public boolean isAdjacent(Note otherNote) {
        int notePosition = noteName.getPosition();
        int otherNotePosition = otherNote.noteName.getPosition();
        int positionDifference = Math.abs(notePosition - otherNotePosition);
        int octaveDifference = Math.abs(octave - otherNote.octave);
        boolean sameOctaveAdjacent = (positionDifference == 1)  && (octaveDifference == 0);
        boolean noteBCAdjacent = (positionDifference == 6) && (octaveDifference == 1);
        return sameOctaveAdjacent || noteBCAdjacent;
    }

    public boolean noteHeadEquals(Note note){
        return this.noteName == note.noteName && this.octave == note.octave;
    }

    public Note getNext(NoteAccidental noteAccidental) {
        int newOctave = octave;
        NoteName newNoteName;

        if (noteName.getPosition() >= NoteName.B.getPosition()){
            newOctave++;
            newNoteName = NoteName.C;
        }
        else {
            newNoteName = NoteName.values()[noteName.getPosition() + 1];
        }
        return new Note(newNoteName, newOctave, noteAccidental);
    }

    public Note getPrevious(NoteAccidental noteAccidental) {
        int newOctave = octave;
        NoteName newNoteName;

        if (noteName.getPosition() <= NoteName.C.getPosition()){
            newOctave--;
            newNoteName = NoteName.B;
        }
        else {
            newNoteName = NoteName.values()[noteName.getPosition() - 1];
        }
        return new Note(newNoteName, newOctave, noteAccidental);
    }

    protected int noteValue(){
        return octave * 10 + noteName.getPosition();
    }

    @Override
    public void draw(Graphics2D graphics2D, BufferedImage noteImage, BufferedImage sharpImage, BufferedImage naturalImage, BufferedImage flatImage,
                     NoteCollection notes, int xPos, Set<Integer> ledgerLines) {

        int lineNumber = RenderConstants.getLineNumber(this);
        int noteWidth = noteImage.getWidth();
        int noteHeight = noteImage.getHeight();
        int noteY = RenderConstants.getLineYOffset(lineNumber) - (noteHeight / 2);

        for (NoteAccidental accidental : getActiveAccidentals()) {
            if (accidental == NoteAccidental.SHARP) {
                int sharpXPos = xPos - (int) (sharpImage.getWidth() * 1.3);
                int sharpYPos = noteY - (sharpImage.getHeight() / 3);
                graphics2D.drawImage(sharpImage, null, sharpXPos, sharpYPos);
            }
        }

        int lineThickness = RenderConstants.lineThickness;
        graphics2D.setStroke(new BasicStroke(lineThickness));

        for (int ledgerLineNumber : ledgerLines) {
            int helperLineYPos = RenderConstants.getLineYOffset(ledgerLineNumber);
            int lineXPosStart = xPos - 2;
            int lineXPosEnd = lineXPosStart + noteWidth + 2;
            graphics2D.drawLine(lineXPosStart, helperLineYPos, lineXPosEnd, helperLineYPos);
        }

        if (isSqueezed(notes)) {
            xPos += noteWidth;
        }
        graphics2D.drawImage(noteImage, null, xPos, noteY);

        /*if(drawName){
            NoteName noteName = note.getNoteName();
            graphics2D.setFont(new Font("Dialog", Font.BOLD, RenderConstants.nameFontSize));
            int nameY = RenderConstants.getLineYOffset(lineNumber) - 2;
            //if ((RenderConstants.getLineNumber(note) % 2) == 1)
            //nameY += noteHeight / 2;
            //graphics2D.drawString(noteName.toString(), xPos + noteWidth, nameY);
        }*/
    }


    public boolean isSqueezed(NoteCollection notes){
        Note adjacentNote = getPrevious(NoteAccidental.NATURAL);
        for (Note noteInSet : notes){
            if (noteInSet.noteHeadEquals(adjacentNote)){
                return !adjacentNote.isSqueezed(notes);
            }
        }
        return false;
    }

    @Override
    public int compareTo(Note o) {
        return noteValue() - o.noteValue();
    }

    @Override
    public int hashCode() {
        String nameOctave = noteName.name() + octave;
        return nameOctave.hashCode();
    }

    @Override
    public String toString() {
        String noteString = noteName.toString();
        if (accidentals.contains(NoteAccidental.NATURAL) && (accidentals.contains(NoteAccidental.FLAT) || accidentals.contains(NoteAccidental.SHARP))){
            noteString += "n";
        }
        if (accidentals.contains(NoteAccidental.SHARP)) {
            noteString += "#";
        }
        if (accidentals.contains(NoteAccidental.FLAT)) {
            noteString += "b";
        }
        noteString += Integer.toString(octave);
        return noteString;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Note){
            Note noteCompare = (Note) obj;
            return this.noteName == noteCompare.noteName
                    && this.octave == noteCompare.octave
                    && this.accidentals.equals(noteCompare.accidentals);
        }
        return false;
    }
}
