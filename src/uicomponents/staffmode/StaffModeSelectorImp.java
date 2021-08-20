package uicomponents.staffmode;

import statemodels.StaffMode;
import uicomponents.util.selectors.JSelector;
import uicomponents.staffmode.selectorfactory.JComboStaffSelectorFactory;
import uicomponents.util.SelectorFactory;
import uicomponents.util.ModeHandler;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class StaffModeSelectorImp extends JComponent {
    public StaffModeSelectorImp(ModeHandler<StaffMode> modeHandler){
        SelectorFactory<StaffMode> selectorFactory = new JComboStaffSelectorFactory();
        JSelector<StaffMode> selector = modeHandler.createModeSelector(selectorFactory);
        selector.setPreferredSize(new Dimension(100, 40));

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
