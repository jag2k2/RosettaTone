package uicomponents.rangeselector;

import uicomponents.UIComponent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class RangeSelectorImp implements UIComponent {
    private final UIComponent lowerNoteSelector;
    private final UIComponent upperNoteSelector;

    public RangeSelectorImp(UIComponent lowerNoteSelector, UIComponent upperNoteSelector){
        this.lowerNoteSelector = lowerNoteSelector;
        this.upperNoteSelector = upperNoteSelector;
    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(lowerNoteSelector.getComponent());
        panel.add(upperNoteSelector.getComponent());
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setBorder(border);
        return panel;
    }
}
