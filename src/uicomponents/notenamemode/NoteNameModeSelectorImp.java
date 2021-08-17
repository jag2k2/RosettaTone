package uicomponents.notenamemode;

import uicomponents.notenamemode.selectorFactory.JComboNoteNameSelectorFactory;
import uicomponents.util.JSelector;
import uicomponents.util.ModeHandler;
import uicomponents.util.SelectorFactory;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class NoteNameModeSelectorImp extends JComponent {
    public NoteNameModeSelectorImp(ModeHandler<NoteNameMode> modeHandler, NoteNameMode defaultMode){
        SelectorFactory<NoteNameMode> selectorFactory = new JComboNoteNameSelectorFactory();
        JSelector<NoteNameMode> selector = modeHandler.createModeSelector(selectorFactory, defaultMode);

        JPanel labelPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(labelPanel, BoxLayout.Y_AXIS);
        labelPanel.setLayout(boxLayout);
        labelPanel.add(Box.createRigidArea(new Dimension(10,1)));
        labelPanel.add(new JLabel("NAME ASSIST"));

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
