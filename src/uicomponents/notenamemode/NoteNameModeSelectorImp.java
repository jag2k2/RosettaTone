package uicomponents.notenamemode;

import uicomponents.UIComponent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NoteNameModeSelectorImp implements UIComponent, ActionListener {
    private final NoteNameModeModifier noteNameModeModifier;

    public NoteNameModeSelectorImp(NoteNameModeModifier noteNameModeModifier){
        this.noteNameModeModifier = noteNameModeModifier;
    }

    @Override
    public Component makeComponent() {
        JPanel labelPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(labelPanel, BoxLayout.Y_AXIS);
        labelPanel.setLayout(boxLayout);
        labelPanel.add(Box.createRigidArea(new Dimension(10,1)));
        labelPanel.add(new JLabel("NAME ASSIST"));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(labelPanel, BorderLayout.NORTH);
        Dimension boxSize = new Dimension(100, 40);

        JComboBox<NoteNameMode> comboBox = new JComboBox<>(NoteNameMode.values());
        comboBox.setRenderer(new NoteNameModeRenderer(new DefaultListCellRenderer()));
        comboBox.setPreferredSize(boxSize);
        comboBox.addActionListener(this);

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
        if (e.getSource() instanceof JComboBox<?>){
            JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
            int selectedIndex = comboBox.getSelectedIndex();
            Object selected = comboBox.getItemAt(selectedIndex);
            if (selected instanceof NoteNameMode){
                NoteNameMode selectedMode = (NoteNameMode) selected;
                noteNameModeModifier.setMode(selectedMode);
            }
        }
    }
}
