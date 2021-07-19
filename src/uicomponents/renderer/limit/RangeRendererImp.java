package uicomponents.renderer.limit;

import notification.LimitChangeObserver;
import uicomponents.UIComponent;
import uicomponents.renderer.records.RenderConstants;

import javax.swing.*;
import java.awt.*;

public class RangeRendererImp extends Component implements UIComponent, LimitChangeObserver {
    private final RangeDrawable limitRange;
    private final RangeDrawable previewRange;

    public RangeRendererImp(RangeDrawable limitRange, RangeDrawable previewRange) {
        this.limitRange = limitRange;
        this.previewRange = previewRange;
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
        limitRange.draw(graphics2D, 1.0f);
        previewRange.draw(graphics2D, 0.5f);
    }
}
