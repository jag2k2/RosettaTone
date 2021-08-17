package uicomponents.staffmode.selectors;

import uicomponents.util.CanSetMode;
import uicomponents.staffmode.StaffMode;
import uicomponents.util.JSelectorRenderer;
import uicomponents.util.JSelector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JComboStaffSelector extends JSelector<StaffMode> implements ActionListener {
    private final CanSetMode<StaffMode> staffMode;
    private final JComboBox<StaffMode> comboBox;

    public JComboStaffSelector(CanSetMode<StaffMode> staffMode) {
        this.staffMode = staffMode;
        comboBox = new JComboBox<>(StaffMode.values());
        comboBox.setRenderer(new JSelectorRenderer<>(new DefaultListCellRenderer()));

        Dimension boxSize = new Dimension(100, 40);
        comboBox.setPreferredSize(boxSize);
        this.setLayout(new BorderLayout());
        this.add(comboBox, BorderLayout.CENTER);

        comboBox.addActionListener(this);
    }

    @Override
    public void setSelectedItem(StaffMode selectedItem) {
        comboBox.setSelectedItem(selectedItem);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = comboBox.getSelectedIndex();
        if (selectedIndex >= 0){
            StaffMode selectedMode = comboBox.getItemAt(selectedIndex);
            staffMode.setMode(selectedMode);
        }
    }
}
