package uicomponents.renderer;

import music.Note;

import java.awt.*;

public class RangeDrawer {
    private final Graphics2D graphics2D;

    public RangeDrawer(Graphics2D graphics2D){
        this.graphics2D = graphics2D;
    }

    protected void paintBackground(){
        graphics2D.setColor(Color.WHITE);
        int canvasWidth = (int) CanvasRender.getRangeIndicatorSize().getWidth();
        int canvasHeight = (int) CanvasRender.getCanvasSize().getHeight();
        graphics2D.fillRect(0,0,canvasWidth,canvasHeight);
    }

    public void drawLimit(Note note){
        int lineNumber = CanvasRender.getLineNumber(note);
        int circleRadius = 20;
        int circleXPos = 50;
        int circleYPos = CanvasRender.getLineYOffset(lineNumber) - (circleRadius/2);
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillOval(circleXPos, circleYPos, circleRadius, circleRadius);

        int fontXPos = 20;
        int fontYPos = CanvasRender.getLineYOffset(lineNumber) + (circleRadius/2) - 2;

        graphics2D.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        graphics2D.drawString(note.toString(), fontXPos, fontYPos);
    }

    public void drawVerticalConnector(Note upperLimitNote, Note lowerLimitNote){
        int connX1 = 50 + (20 / 2);
        int connX2 = 50 + (20 / 2);
        int connY1 = CanvasRender.getLineYOffset(CanvasRender.getLineNumber(upperLimitNote));
        int connY2 = CanvasRender.getLineYOffset(CanvasRender.getLineNumber(lowerLimitNote));

        graphics2D.setColor(Color.BLACK);
        int lineThickness = 5;
        graphics2D.setStroke(new BasicStroke(lineThickness));
        graphics2D.drawLine(connX1, connY1, connX2, connY2);
    }
}
