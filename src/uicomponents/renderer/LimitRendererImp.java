package uicomponents.renderer;

import notification.LimitChangeObserver;
import uicomponents.UIComponent;
import uicomponents.renderer.records.RenderConstants;

import javax.swing.*;
import java.awt.*;

public class LimitRendererImp extends Component implements UIComponent, LimitChangeObserver {
    private final RangeDrawable noteLimitInterval;

    public LimitRendererImp(RangeDrawable noteLimitInterval){
        this.noteLimitInterval = noteLimitInterval;
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
        noteLimitInterval.draw(graphics2D);
    }
}
