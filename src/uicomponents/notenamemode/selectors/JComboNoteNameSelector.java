package uicomponents.notenamemode.selectors;

import uicomponents.notenamemode.NoteNameMode;
import uicomponents.util.CanSetMode;
import uicomponents.util.JSelector;
import uicomponents.util.JSelectorRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JComboNoteNameSelector extends JSelector<NoteNameMode> implements ActionListener {
    private final CanSetMode<NoteNameMode> noteNameMode;
    private final JComboBox<NoteNameMode> comboBox;

    public JComboNoteNameSelector(CanSetMode<NoteNameMode> noteNameMode) {
        this.noteNameMode = noteNameMode;
        comboBox = new JComboBox<>(NoteNameMode.values());
        comboBox.setRenderer(new JSelectorRenderer<>(new DefaultListCellRenderer()));

        Dimension boxSize = new Dimension(100, 40);
        comboBox.setPreferredSize(boxSize);
        this.setLayout(new BorderLayout());
        this.add(comboBox, BorderLayout.CENTER);

        comboBox.addActionListener(this);
    }

    @Override
    public void setSelectedItem(NoteNameMode selectedItem) {
        comboBox.setSelectedItem(selectedItem);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = comboBox.getSelectedIndex();
        if (selectedIndex >= 0){
            NoteNameMode selectedMode = comboBox.getItemAt(selectedIndex);
            noteNameMode.setMode(selectedMode);
        }
    }


}
