package uicomponents.notenamemode;

import uicomponents.UIComponent;
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
        JPanel panel = new JPanel();
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setBorder(border);
        Dimension boxSize = new Dimension(100, 40);
        comboBox.setPreferredSize(boxSize);
        panel.add(comboBox);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = comboBox.getSelectedIndex();
        NoteNameMode selectedMode = comboBox.getItemAt(selectedIndex);
        noteNameModeModifier.setMode(selectedMode);
    }
}
