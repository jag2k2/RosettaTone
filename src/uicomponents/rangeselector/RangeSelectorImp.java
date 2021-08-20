package uicomponents.rangeselector;

import uicomponents.UIComponent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class RangeSelectorImp extends JComponent {

    public RangeSelectorImp(JComponent lowerNoteSelector, JComponent upperNoteSelector){
        JPanel labelPanel = new JPanel();
        BoxLayout layout = new BoxLayout(labelPanel, BoxLayout.Y_AXIS);
        labelPanel.setLayout(layout);
        labelPanel.add(Box.createRigidArea(new Dimension(16, 1)));
        labelPanel.add(new JLabel("GENERATOR"));

        JPanel rangePanel = new JPanel(new FlowLayout());
        rangePanel.add(lowerNoteSelector);
        rangePanel.add(upperNoteSelector);

        this.setLayout(new BorderLayout());
        this.add(labelPanel, BorderLayout.NORTH);
        this.add(rangePanel, BorderLayout.CENTER);
        this.add(Box.createRigidArea(new Dimension(1,5)), BorderLayout.SOUTH);
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    }
}
