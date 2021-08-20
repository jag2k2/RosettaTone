package uicomponents.renderer.limit;

import uicomponents.renderer.records.RenderConstants;
import javax.swing.*;
import java.awt.*;

public class RangeRendererImp extends JComponent {
    private final RangeDrawable limitRange;
    private final RangeDrawable previewRange;

    public RangeRendererImp(RangeDrawable limitRange, RangeDrawable previewRange) {
        this.limitRange = limitRange;
        this.previewRange = previewRange;
    }

    @Override
    public Dimension getPreferredSize() {
        return RenderConstants.rangeIndicatorSize;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;
        limitRange.draw(graphics2D, 1.0f);
        previewRange.draw(graphics2D, 0.5f);
    }
}
