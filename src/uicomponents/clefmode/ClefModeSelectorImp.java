package uicomponents.clefmode;

import notification.ModeChangeNotifier;
import uicomponents.UIComponent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClefModeSelectorImp implements UIComponent, ActionListener, ClefModeSelector {
    private final JComboBox<ClefMode> clefComboBox;
    private final ModeChangeNotifier modeChangeNotifier;

    public ClefModeSelectorImp(ClefMode staffState, ModeChangeNotifier modeChangeNotifier){
        this.clefComboBox = new JComboBox<>(ClefMode.values());
        this.modeChangeNotifier = modeChangeNotifier;
        this.clefComboBox.setRenderer(new ClefModeRenderer(clefComboBox.getRenderer()));

        this.clefComboBox.addActionListener(this);
        this.clefComboBox.setSelectedItem(staffState);
    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel();
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setBorder(border);
        Dimension boxSize = new Dimension(100, 40);
        clefComboBox.setPreferredSize(boxSize);
        panel.add(clefComboBox);
        return panel;
    }

    @Override
    public ClefMode getSelection() {
        int selectedIndex = clefComboBox.getSelectedIndex();
        return clefComboBox.getItemAt(selectedIndex);
    }

    @Override
    public boolean trebleEnabled() {
        ClefMode selectedMode = getSelection();
        return (selectedMode == ClefMode.Treble || selectedMode == ClefMode.Grand);
    }

    @Override
    public boolean bassEnabled() {
        ClefMode selectedMode = getSelection();
        return (selectedMode == ClefMode.Bass || selectedMode == ClefMode.Grand);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        modeChangeNotifier.notifyObservers();
    }
}
