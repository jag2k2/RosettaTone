package com.jag2k2.statemodels.limitstate;

import com.jag2k2.uicomponents.renderer.limit.LimitDrawable;
import com.jag2k2.music.Note;
import com.jag2k2.music.NoteAccidental;
import com.jag2k2.trainer.randomnotegenerator.LineNumerable;
import com.jag2k2.uicomponents.rangeselector.noteselector.LimitModifier;
import com.jag2k2.uicomponents.renderer.records.RenderConstants;
import com.jag2k2.utility.Maybe;

import java.awt.*;

public class LimitStateImp implements LimitModifier, LineNumerable, LimitDrawable {
    private Note limit;
    private Maybe<LimitChangeNotifier> limitChangeNotifier = new Maybe<>();

    public LimitStateImp(Note limit){
        this.limit = limit;
    }

    public void addLimitChangeNotifier(LimitChangeNotifier limitChangeNotifier){
        this.limitChangeNotifier = new Maybe<>(limitChangeNotifier);
    }

    @Override
    public Note getLimit() {
        return limit;
    }

    @Override
    public void setLimit(Note note){
        if(!limit.equals(note)) {
            limit = note;
            for (LimitChangeNotifier notifier : limitChangeNotifier) {
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
        return limit.getLineNumber();
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int lineNumber = limit.getLineNumber();
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
