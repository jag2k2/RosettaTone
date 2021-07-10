package uicomponents.renderer;

import music.Note;
import notification.RangeChangeObserver;
import statemodels.NoteRangeLimits;
import uicomponents.UIComponent;

import javax.swing.*;
import java.awt.*;

public class RangeRendererImp extends Component implements UIComponent, RangeChangeObserver {
    private final NoteRangeLimits noteRangeModel;

    public RangeRendererImp(NoteRangeLimits noteRangeModel){
        this.noteRangeModel = noteRangeModel;
    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel();
        panel.add(this);
        panel.setBackground(Color.WHITE);
        return panel;
    }

    @Override
    public void rangeChanged() {
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return RenderConstants.rangeIndicatorSize;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;
        RangeDrawer rangeDrawer = new RangeDrawer(graphics2D);
        Note upperLimitNote = noteRangeModel.getLowerLimitNote();
        Note lowerLimitNote = noteRangeModel.getUpperLimitNote();

        rangeDrawer.drawLimit(upperLimitNote);
        rangeDrawer.drawLimit(lowerLimitNote);
        rangeDrawer.drawVerticalConnector(upperLimitNote, lowerLimitNote);
    }
}
