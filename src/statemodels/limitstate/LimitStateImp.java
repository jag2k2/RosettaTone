package statemodels.limitstate;

import music.line.Line;
import trainer.randomnotegenerator.BoundedNoteGenerator;
import uicomponents.LimitState;
import uicomponents.renderer.limit.LimitDrawable;
import music.note.Note;
import music.note.NoteAccidental;
import uicomponents.renderer.records.RenderConstants;
import utility.Maybe;

import java.awt.*;

public class LimitStateImp implements LimitState {
    private Note limit;
    private Maybe<LimitChangeNotifier> limitChangeNotifier = new Maybe<>();

    public LimitStateImp(Note limit){
        this.limit = limit;
    }

    @Override
    public void addLimitChangeNotifier(LimitChangeNotifier limitChangeNotifier){
        this.limitChangeNotifier = new Maybe<>(limitChangeNotifier);
    }

    @Override
    public Note getActive() {
        return limit;
    }

    @Override
    public void update(Note note){
        if(!limit.equals(note)) {
            limit = note;
            for (LimitChangeNotifier notifier : limitChangeNotifier) {
                notifier.notifyObservers();
            }
        }
    }

    @Override
    public void increment() {
        update(limit.getNext(NoteAccidental.NATURAL));
    }

    @Override
    public void decrement() {
        update(limit.getPrevious(NoteAccidental.NATURAL));
    }

    @Override
    public Note generateRandomNote(BoundedNoteGenerator upperLimit) {
        LimitStateImp otherLimit = (LimitStateImp) upperLimit;
        return limit.generateRandomNote(otherLimit.limit);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        Line line = new Line(limit);
        int circleXPos = RenderConstants.limitRenderXOffset;
        int circleDiameter = RenderConstants.limitDotDiameter;
        int circleYPos = line.getYOffset() - (circleDiameter/2);
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillOval(circleXPos, circleYPos, circleDiameter, circleDiameter);

        int fontXPos = 20;
        int fontYPos = line.getYOffset() + (circleDiameter/2) - 2;

        graphics2D.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        graphics2D.drawString(limit.toString(), fontXPos, fontYPos);
    }

    @Override
    public void drawConnection(Graphics2D graphics2D, LimitDrawable otherLimit){
        if(otherLimit instanceof LimitStateImp){
            LimitStateImp connectingLimit = (LimitStateImp) otherLimit;

            int connX1 = getPosition().x;
            int connX2 = connectingLimit.getPosition().x;
            int connY1 = getPosition().y;
            int connY2 = connectingLimit.getPosition().y;

            graphics2D.setColor(Color.BLACK);

            graphics2D.setStroke(new BasicStroke(RenderConstants.limitLineThickness));
            graphics2D.drawLine(connX1, connY1, connX2, connY2);
        }
    }

    protected Point getPosition() {
        int x = RenderConstants.limitRenderXOffset + (RenderConstants.limitDotDiameter / 2);
        Line line = new Line(limit);
        int y = line.getYOffset();
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
        return limit.toString();
    }
}
