package uicomponents.staffmode;

import uicomponents.UIComponent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffModeSelectorImp implements UIComponent, ActionListener {
    private final StaffModeModifier staffModeModifier;
    private final StaffMode defaultMode;

    public StaffModeSelectorImp(StaffModeModifier staffModeModifier, StaffMode defaultMode){
        this.staffModeModifier = staffModeModifier;
        this.defaultMode = defaultMode;
    }

    @Override
    public Component makeComponent() {
        JPanel labelPanel = new JPanel();
        BoxLayout layout = new BoxLayout(labelPanel, BoxLayout.Y_AXIS);
        labelPanel.setLayout(layout);
        labelPanel.add(Box.createRigidArea(new Dimension(6, 1)));
        labelPanel.add(new JLabel("STAFF"));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(labelPanel, BorderLayout.NORTH);
        Dimension boxSize = new Dimension(100, 40);

        JComboBox<StaffMode> comboBox = new JComboBox<>(StaffMode.values());
        comboBox.setRenderer(new StaffModeRenderer(new DefaultListCellRenderer()));
        comboBox.setPreferredSize(boxSize);

        panel.add(comboBox, BorderLayout.CENTER);
        panel.add(Box.createRigidArea(new Dimension(10,1)), BorderLayout.WEST);
        panel.add(Box.createRigidArea(new Dimension(10,1)), BorderLayout.EAST);
        panel.add(Box.createRigidArea(new Dimension(1,5)), BorderLayout.SOUTH);
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setBorder(border);

        comboBox.addActionListener(this);

        comboBox.setSelectedItem(defaultMode);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JComboBox<?>){
            JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
            int selectedIndex = comboBox.getSelectedIndex();
            Object selected = comboBox.getItemAt(selectedIndex);
            if (selected instanceof StaffMode){
                StaffMode selectedMode = (StaffMode) selected;
                staffModeModifier.setMode(selectedMode);
            }
        }
    }
}
