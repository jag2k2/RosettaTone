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
    public Component makeComponent() {
        JPanel labelPanel = new JPanel();
        BoxLayout layout = new BoxLayout(labelPanel, BoxLayout.Y_AXIS);
        labelPanel.setLayout(layout);
        labelPanel.add(Box.createRigidArea(new Dimension(16, 1)));
        labelPanel.add(new JLabel("GENERATOR"));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(labelPanel, BorderLayout.NORTH);
        JPanel rangePanel = new JPanel(new FlowLayout());
        rangePanel.add(lowerNoteSelector.makeComponent());
        rangePanel.add(upperNoteSelector.makeComponent());
        panel.add(rangePanel, BorderLayout.CENTER);
        panel.add(Box.createRigidArea(new Dimension(1,5)), BorderLayout.SOUTH);

        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setBorder(border);
        return panel;
    }
}
