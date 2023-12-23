package com.jag2k2.uicomponents.staffmode;

import com.jag2k2.uicomponents.UIComponent;
import javafx.scene.text.TextAlignment;
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
        JPanel labelPanel = new JPanel();
        BoxLayout layout = new BoxLayout(labelPanel, BoxLayout.Y_AXIS);
        labelPanel.setLayout(layout);
        labelPanel.add(Box.createRigidArea(new Dimension(6, 1)));
        labelPanel.add(new JLabel("STAFF"));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(labelPanel, BorderLayout.NORTH);
        Dimension boxSize = new Dimension(100, 40);
        comboBox.setPreferredSize(boxSize);
        panel.add(comboBox, BorderLayout.CENTER);
        panel.add(Box.createRigidArea(new Dimension(10,1)), BorderLayout.WEST);
        panel.add(Box.createRigidArea(new Dimension(10,1)), BorderLayout.EAST);
        panel.add(Box.createRigidArea(new Dimension(1,5)), BorderLayout.SOUTH);
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setBorder(border);

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
