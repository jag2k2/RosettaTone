package uicomponents.staffselector;

import notification.StaffChangeNotifier;
import notification.StaffChangeObserver;
import uicomponents.UIComponent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ModeSelectorImp implements UIComponent, ActionListener, StaffModeHolder, StaffChangeNotifier {
    private final JComboBox<StaffMode> clefComboBox;
    private final List<StaffChangeObserver> observers;

    public ModeSelectorImp(StaffMode staffState){
        this.clefComboBox = new JComboBox<>(StaffMode.values());
        this.observers = new ArrayList<>();
        this.clefComboBox.addActionListener(this);
        this.clefComboBox.setSelectedItem(staffState);
    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel();
        Border border = BorderFactory.createLineBorder(Color.GRAY);
        panel.setBorder(border);
        panel.add(clefComboBox);
        return panel;
    }

    @Override
    public StaffMode getSelection() {
        int selectedIndex = clefComboBox.getSelectedIndex();
        return clefComboBox.getItemAt(selectedIndex);
    }

    @Override
    public boolean trebleEnabled() {
        StaffMode selectedMode = getSelection();
        return (selectedMode == StaffMode.Treble || selectedMode == StaffMode.Grand);
    }

    @Override
    public boolean bassEnabled() {
        StaffMode selectedMode = getSelection();
        return (selectedMode == StaffMode.Bass || selectedMode == StaffMode.Grand);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        notifyObservers();
    }

    @Override
    public void addObserver(StaffChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (StaffChangeObserver observer : observers){
            observer.updateStaff();
        }
    }

}
