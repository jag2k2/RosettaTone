package statemodels.notelimit;

import music.Note;
import music.NoteAccidental;
import trainer.randomnotegenerator.LineNumberable;
import uicomponents.rangeselector.noteselector.NoteModifier;
import uicomponents.rangeselector.noteselector.NotePreviewer;
import uicomponents.renderer.MusicDrawable;
import uicomponents.renderer.RenderConstants;

import java.awt.*;

public class NoteLimitImp implements NoteModifier, NotePreviewer, LineNumberable, MusicDrawable {
    private Note limit;
    private final LimitChangeNotifier limitChangeNotifier;

    public NoteLimitImp(Note limit, LimitChangeNotifier limitChangeNotifier){
        this.limit = limit;
        this.limitChangeNotifier = limitChangeNotifier;
    }

    @Override
    public Note getLimit() {
        return limit;
    }

    @Override
    public void setLimit(Note note){
        if (!limit.equals(note)) {
            limit = note;
            limitChangeNotifier.notifyObservers();
        }
    }

    @Override
    public void preview(Note note) {

    }

    @Override
    public void increment() {
        setLimit(limit.getNext(NoteAccidental.NATURAL));
    }

    @Override
    public void decrement() {
        setLimit(limit.getPrevious(NoteAccidental.NATURAL));
    }

    @Override
    public int getLineNumber() {
        return RenderConstants.getLineNumber(limit);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int lineNumber = RenderConstants.getLineNumber(limit);
        int circleXPos = RenderConstants.limitRenderXOffset;
        int circleDiameter = RenderConstants.limitDotDiameter;
        int circleYPos = RenderConstants.getLineYOffset(lineNumber) - (circleDiameter/2);
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillOval(circleXPos, circleYPos, circleDiameter, circleDiameter);

        int fontXPos = 20;
        int fontYPos = RenderConstants.getLineYOffset(lineNumber) + (circleDiameter/2) - 2;

        graphics2D.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        graphics2D.drawString(limit.toString(), fontXPos, fontYPos);
    }

    @Override
    public Point getPosition() {
        int x = RenderConstants.limitRenderXOffset + (RenderConstants.limitDotDiameter / 2);
        int y = RenderConstants.getLineYOffset(getLineNumber());
        return new Point(x,y);
    }

    @Override
    public int compareTo(Note note) {
        return limit.compareTo(note);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NoteLimitImp){
            NoteLimitImp toCompare = (NoteLimitImp) obj;
            return limit.equals(toCompare.limit);
        }
        return false;
    }

    @Override
    public String toString() {
        return "active: " + limit;
    }
}
