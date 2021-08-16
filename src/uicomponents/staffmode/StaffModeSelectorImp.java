package uicomponents.staffmode;

import uicomponents.staffmode.eventhandler.JSelector;
import uicomponents.staffmode.selectorfactory.JComboSelectorFactory;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class StaffModeSelectorImp extends JComponent {
    public StaffModeSelectorImp(StaffModeHandler staffModeHandler, StaffMode defaultMode){
        SelectorFactory selectorFactory = new JComboSelectorFactory();
        JSelector selector = staffModeHandler.createModeSelector(selectorFactory, defaultMode);

        JPanel labelPanel = new JPanel();
        BoxLayout layout = new BoxLayout(labelPanel, BoxLayout.Y_AXIS);
        labelPanel.setLayout(layout);
        labelPanel.add(Box.createRigidArea(new Dimension(6, 1)));
        labelPanel.add(new JLabel("STAFF"));

        this.setLayout(new BorderLayout());
        this.add(labelPanel, BorderLayout.NORTH);

        this.add(selector, BorderLayout.CENTER);
        this.add(Box.createRigidArea(new Dimension(10,1)), BorderLayout.WEST);
        this.add(Box.createRigidArea(new Dimension(10,1)), BorderLayout.EAST);
        this.add(Box.createRigidArea(new Dimension(1,5)), BorderLayout.SOUTH);
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        this.setBorder(border);
    }
}
