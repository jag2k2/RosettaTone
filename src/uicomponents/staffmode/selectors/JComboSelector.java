package uicomponents.staffmode.selectors;

import uicomponents.staffmode.eventhandler.StaffModeChangeObserver;
import uicomponents.staffmode.StaffMode;
import uicomponents.staffmode.StaffModeRenderer;
import uicomponents.staffmode.eventhandler.JSelector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JComboSelector extends JSelector implements ActionListener {
    private final StaffModeChangeObserver staffMode;
    private final JComboBox<StaffMode> comboBox;

    public JComboSelector(StaffModeChangeObserver staffMode) {
        this.staffMode = staffMode;
        comboBox = new JComboBox<>(StaffMode.values());
        comboBox.setRenderer(new StaffModeRenderer(new DefaultListCellRenderer()));

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
        StaffMode selectedMode = (StaffMode) comboBox.getSelectedItem();
        staffMode.setMode(selectedMode);
    }
}
