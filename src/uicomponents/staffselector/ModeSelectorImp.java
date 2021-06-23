package uicomponents.staffselector;

import music.StaffSelection;
import notification.StaffChangeNotifier;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeSelectorImp implements ActionListener {
    private final JComboBox<StaffOptions> clefComboBox;
    private final JPanel panel;
    private final StaffChangeNotifier staffChangeNotifier;
    private final StaffSelection staffSelection;

    public ModeSelectorImp(StaffSelection staffSelection, StaffChangeNotifier staffChangeNotifier){
        this.staffSelection = staffSelection;
        this.staffChangeNotifier = staffChangeNotifier;
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
        staffSelection.setSelection(StaffOptions.values()[selectedIndex]);
        staffChangeNotifier.notifyObservers();
    }
}
