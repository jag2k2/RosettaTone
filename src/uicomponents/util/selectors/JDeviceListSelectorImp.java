package uicomponents.util.selectors;

import uicomponents.browser.eventhandler.MidiDeviceInquirer;
import utility.Maybe;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.StringJoiner;

public class JDeviceListSelectorImp extends JListSelector<MidiDevice> implements ListSelectionListener {
    private final Receiver midiReceiver;
    private final MidiDeviceInquirer midiDeviceInquirer;
    private final JList<MidiDevice> deviceList;
    private final DefaultListModel<MidiDevice> listModel = new DefaultListModel<>();
    private Maybe<MidiDevice> openDevice = new Maybe<>();

    public JDeviceListSelectorImp(Receiver midiReceiver, MidiDeviceInquirer midiDeviceInquirer) {
        this.midiReceiver = midiReceiver;
        this.midiDeviceInquirer = midiDeviceInquirer;
        this.deviceList = new JList<>(listModel);
        this.deviceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.deviceList.addListSelectionListener(this);

        this.setLayout(new BorderLayout());
        this.add(deviceList, BorderLayout.CENTER);
    }

    @Override
    public void setRenderer(ListCellRenderer<MidiDevice> renderer) {
        deviceList.setCellRenderer(renderer);
    }

    @Override
    public void refreshSelections() {
        listModel.clear();
        midiDeviceInquirer.refresh();
        for (MidiDevice device : midiDeviceInquirer.getMidiDevices()) {
            if (device.getMaxTransmitters() != 0) {
                listModel.addElement(device);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            for(MidiDevice device : openDevice){
                if(device.isOpen()){
                    device.close();
                }
            }

            int selectedIndex = deviceList.getSelectedIndex();
            if (selectedIndex >= 0){
                MidiDevice selectedDevice = listModel.getElementAt(selectedIndex);
                try {
                    selectedDevice.open();
                    Transmitter transmitter = selectedDevice.getTransmitter();
                    transmitter.setReceiver(midiReceiver);
                    openDevice = new Maybe<>(selectedDevice);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setSelectedIndex(int index) {
        deviceList.setSelectedIndex(index);
    }


    @Override
    protected void addItem(MidiDevice item) {
        listModel.addElement(item);
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < listModel.getSize(); i++){
            MidiDevice midiDevice = listModel.getElementAt(i);
            stringJoiner.add(midiDevice.getDeviceInfo().getName());
        }

        String optionString = stringJoiner.toString();

        int selectedIndex = deviceList.getSelectedIndex();
        if (selectedIndex >= 0){
            MidiDevice selectedItem = listModel.elementAt(selectedIndex);
            String selectedItemString = selectedItem.getDeviceInfo().getName();
            int index = optionString.indexOf(selectedItemString);

            StringBuilder newString = new StringBuilder();
            for (int i = 0; i < optionString.length(); i++){
                newString.append(optionString.charAt(i));
                if(i == (index - 1)){
                    newString.append("*");
                }
            }
            optionString = newString.toString();
        }

        return optionString;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof JDeviceListSelectorImp){
            JDeviceListSelectorImp compare = (JDeviceListSelectorImp) obj;
            if (listModel.getSize() != compare.listModel.getSize()){
                return false;
            }
            for (int i = 0; i < listModel.getSize(); i++){
                if(!listModel.getElementAt(i).equals(compare.listModel.getElementAt(i)))
                    return false;
            }
            return deviceList.getSelectedIndex() == compare.deviceList.getSelectedIndex();
        }
        return false;
    }
}
