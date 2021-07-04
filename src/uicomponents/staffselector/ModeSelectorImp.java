package uicomponents.staffselector;

import notification.ModeChangeNotifier;
import notification.ModeChangeObserver;
import uicomponents.UIComponent;
import uicomponents.rangeselector.NoteListRenderer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ModeSelectorImp implements UIComponent, ActionListener, ModeSelector, ModeChangeNotifier {
    private final JComboBox<StaffMode> clefComboBox;
    private final List<ModeChangeObserver> observers;

    public ModeSelectorImp(StaffMode staffState){
        this.clefComboBox = new JComboBox<>(StaffMode.values());
        this.observers = new ArrayList<>();
        this.clefComboBox.setRenderer(new StaffModeRenderer(clefComboBox.getRenderer()));

        this.clefComboBox.addActionListener(this);
        this.clefComboBox.setSelectedItem(staffState);
    }

    @Override
    public Component getComponent() {
        JPanel panel = new JPanel();
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setBorder(border);
        Dimension boxSize = new Dimension(100, 40);
        clefComboBox.setPreferredSize(boxSize);
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
    public void addObserver(ModeChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (ModeChangeObserver observer : observers){
            observer.modeChanged();
        }
    }

}
