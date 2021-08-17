package uicomponents.util.selectors;

import uicomponents.util.CanSetMode;
import uicomponents.staffmode.StaffMode;
import uicomponents.util.JSelectorRenderer;
import uicomponents.util.JSelector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JComboSelector<T extends Enum<T>> extends JSelector<T> implements ActionListener {
    private final CanSetMode<T> staffMode;
    private final JComboBox<T> comboBox;

    public JComboSelector(Class<T> enumType, CanSetMode<T> staffMode) {
        this.staffMode = staffMode;
        comboBox = new JComboBox<>();
        for (T constant : enumType.getEnumConstants()){
            comboBox.addItem(constant);
        }

        comboBox.setRenderer(new JSelectorRenderer<>(new DefaultListCellRenderer()));

        Dimension boxSize = new Dimension(100, 40);
        comboBox.setPreferredSize(boxSize);
        this.setLayout(new BorderLayout());
        this.add(comboBox, BorderLayout.CENTER);

        comboBox.addActionListener(this);
    }

    @Override
    public void setSelectedItem(T selectedItem) {
        comboBox.setSelectedItem(selectedItem);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = comboBox.getSelectedIndex();
        if (selectedIndex >= 0){
            T selectedMode = comboBox.getItemAt(selectedIndex);
            staffMode.setMode(selectedMode);
        }
    }
}
