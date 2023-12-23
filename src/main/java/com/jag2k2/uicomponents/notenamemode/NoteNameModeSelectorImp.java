package com.jag2k2.uicomponents.notenamemode;

import com.jag2k2.uicomponents.UIComponent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NoteNameModeSelectorImp implements UIComponent, ActionListener {
    private final JComboBox<NoteNameMode> comboBox;
    private final NoteNameModeModifier noteNameModeModifier;

    public NoteNameModeSelectorImp(NoteNameModeModifier noteNameModeModifier){
        this.comboBox = new JComboBox<>(NoteNameMode.values());
        this.noteNameModeModifier = noteNameModeModifier;
        this.comboBox.setRenderer(new NoteNameModeRenderer(comboBox.getRenderer()));
        comboBox.addActionListener(this);
    }

    @Override
    public Component getComponent() {
        JPanel labelPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(labelPanel, BoxLayout.Y_AXIS);
        labelPanel.setLayout(boxLayout);
        labelPanel.add(Box.createRigidArea(new Dimension(10,1)));
        labelPanel.add(new JLabel("NAME ASSIST"));

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
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = comboBox.getSelectedIndex();
        NoteNameMode selectedMode = comboBox.getItemAt(selectedIndex);
        noteNameModeModifier.setMode(selectedMode);
    }
}
