package uicomponents.renderer;

import music.Note;
import notification.RangeChangeObserver;
import statemodels.NoteLimitModel;
import java.awt.*;

public class RangeRendererImp extends Component implements RangeChangeObserver {
    private final NoteLimitModel lowerNoteLimitModel;
    private final NoteLimitModel upperNoteLimitModel;

    public RangeRendererImp(NoteLimitModel lowerNoteLimitModel, NoteLimitModel upperNoteLimitModel){
        this.lowerNoteLimitModel = lowerNoteLimitModel;
        this.upperNoteLimitModel = upperNoteLimitModel;
    }

    @Override
    public void updateRange() {
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return CanvasRender.getRangeIndicatorSize();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;
        RangeDrawer rangeDrawer = new RangeDrawer(graphics2D);
        Note upperLimitNote = upperNoteLimitModel.getLimit();
        Note lowerLimitNote = lowerNoteLimitModel.getLimit();

        rangeDrawer.paintBackground();
        rangeDrawer.drawLimit(upperLimitNote);
        rangeDrawer.drawLimit(lowerLimitNote);
        rangeDrawer.drawVerticalConnector(upperLimitNote, lowerLimitNote);
    }
}
