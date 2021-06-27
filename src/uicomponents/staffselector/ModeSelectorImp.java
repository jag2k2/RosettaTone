package uicomponents.staffselector;

import statemodels.StaffState;
import uicomponents.UIComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeSelectorImp implements UIComponent, ActionListener {
    private final JComboBox<StaffOptions> clefComboBox;
    private final StaffState staffState;

    public ModeSelectorImp(StaffState staffState){
        this.staffState = staffState;
        this.clefComboBox = new JComboBox<>(StaffOptions.values());
        this.clefComboBox.addActionListener(this);
    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel();
        panel.add(clefComboBox);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = clefComboBox.getSelectedIndex();
        staffState.setSelection(StaffOptions.values()[selectedIndex]);
    }
}
