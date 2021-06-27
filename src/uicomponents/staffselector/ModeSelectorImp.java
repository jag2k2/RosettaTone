package uicomponents.staffselector;

import statemodels.StaffState;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeSelectorImp implements ActionListener {
    private final JComboBox<StaffOptions> clefComboBox;
    private final JPanel panel;
    private final StaffState staffState;

    public ModeSelectorImp(StaffState staffState){
        this.staffState = staffState;
        this.clefComboBox = new JComboBox<>(StaffOptions.values());
        this.clefComboBox.addActionListener(this);
        this.panel = new JPanel();

        panel.add(clefComboBox);
    }

    public JPanel getPanel(){
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedIndex = clefComboBox.getSelectedIndex();
        staffState.setSelection(StaffOptions.values()[selectedIndex]);
    }
}
