package uicomponents.renderer;

import notification.LimitChangeObserver;
import uicomponents.UIComponent;
import uicomponents.renderer.records.RenderConstants;

import javax.swing.*;
import java.awt.*;

public class LimitRendererImp extends Component implements UIComponent, LimitChangeObserver {
    private final LimitDrawable lowerNoteLimit;
    private final LimitDrawable upperNoteLimit;

    public LimitRendererImp(LimitDrawable lowerNoteLimit, LimitDrawable upperNoteLimit) {
        this.lowerNoteLimit = lowerNoteLimit;
        this.upperNoteLimit = upperNoteLimit;
    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel();
        panel.add(this);
        panel.setBackground(Color.WHITE);
        return panel;
    }

    @Override
    public Dimension getPreferredSize() {
        return RenderConstants.rangeIndicatorSize;
    }

    @Override
    public void limitChanged() {
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;
        lowerNoteLimit.draw(graphics2D);
        upperNoteLimit.draw(graphics2D);

        int connX1 = upperNoteLimit.getPosition().x;
        int connX2 = lowerNoteLimit.getPosition().x;
        int connY1 = upperNoteLimit.getPosition().y;
        int connY2 = lowerNoteLimit.getPosition().y;

        graphics2D.setColor(Color.BLACK);

        graphics2D.setStroke(new BasicStroke(RenderConstants.limitLineThickness));
        graphics2D.drawLine(connX1, connY1, connX2, connY2);
    }
}
