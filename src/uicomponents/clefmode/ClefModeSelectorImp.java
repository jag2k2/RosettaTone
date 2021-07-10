package uicomponents.clefmode;

import uicomponents.UIComponent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClefModeSelectorImp implements UIComponent, ActionListener {
    private final JComboBox<ClefMode> clefComboBox;
    private final ClefModeState clefModeState;

    public ClefModeSelectorImp(ClefModeState clefModeState){
        this.clefComboBox = new JComboBox<>(ClefMode.values());
        this.clefModeState = clefModeState;
        this.clefComboBox.setRenderer(new ClefModeRenderer(clefComboBox.getRenderer()));

        this.clefComboBox.addActionListener(this);
        this.clefComboBox.setSelectedItem(clefModeState.getState());
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
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = clefComboBox.getSelectedIndex();
        ClefMode selectedMode = clefComboBox.getItemAt(selectedIndex);
        clefModeState.setState(selectedMode);
    }
}
