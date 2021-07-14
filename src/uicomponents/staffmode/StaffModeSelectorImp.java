package uicomponents.staffmode;

import uicomponents.UIComponent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffModeSelectorImp implements UIComponent, ActionListener {
    private final JComboBox<StaffMode> comboBox;
    private final StaffModeModifier staffModeModifier;

    public StaffModeSelectorImp(StaffModeModifier staffModeModifier){
        this.comboBox = new JComboBox<>(StaffMode.values());
        this.staffModeModifier = staffModeModifier;
        this.comboBox.setRenderer(new StaffModeRenderer(comboBox.getRenderer()));
        this.comboBox.addActionListener(this);
    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel();
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setBorder(border);
        Dimension boxSize = new Dimension(100, 40);
        comboBox.setPreferredSize(boxSize);
        panel.add(comboBox);
        staffModeModifier.setUISelected(comboBox);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = comboBox.getSelectedIndex();
        StaffMode selectedMode = comboBox.getItemAt(selectedIndex);
        staffModeModifier.setMode(selectedMode);
    }
}
