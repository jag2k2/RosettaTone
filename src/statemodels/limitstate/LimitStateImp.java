package statemodels.limitstate;

import uicomponents.renderer.LimitDrawable;
import music.Note;
import music.NoteAccidental;
import trainer.randomnotegenerator.LineNumerable;
import uicomponents.rangeselector.noteselector.LimitModifier;
import uicomponents.rangeselector.noteselector.LimitPreviewer;
import uicomponents.renderer.records.RenderConstants;
import utility.Maybe;

import java.awt.*;

public class LimitStateImp implements LimitModifier, LimitPreviewer, LineNumerable, LimitDrawable {
    private Note limit;
    private Maybe<LimitChangeNotifier> limitChangeNotifier = new Maybe<>();
    private Maybe<LimitChangeNotifier> previewChangeNotifier = new Maybe<>();

    public LimitStateImp(Note limit){
        this.limit = limit;
    }

    public void addLimitChangeNotifier(LimitChangeNotifier limitChangeNotifier){
        this.limitChangeNotifier = new Maybe<>(limitChangeNotifier);
    }

    public void addPreviewChangeNotifier(LimitChangeNotifier previewChangeNotifier){
        this.previewChangeNotifier = new Maybe<>(previewChangeNotifier);
    }

    @Override
    public Note getLimit() {
        return limit;
    }

    @Override
    public void setLimit(Note note){
        if (!limit.equals(note)) {
            limit = note;
            for (LimitChangeNotifier notifier : limitChangeNotifier){
                notifier.notifyObservers();
            }
        }
    }

    @Override
    public void preview(Note note) {
        if (!limit.equals(note)) {
            limit = note;
            for (LimitChangeNotifier notifier : previewChangeNotifier){
                notifier.notifyObservers();
            }
        }
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
        if (obj instanceof LimitStateImp){
            LimitStateImp toCompare = (LimitStateImp) obj;
            return limit.equals(toCompare.limit);
        }
        return false;
    }

    @Override
    public String toString() {
        return "active: " + limit;
    }
}
