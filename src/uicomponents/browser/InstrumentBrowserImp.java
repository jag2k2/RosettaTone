package uicomponents.browser;

import instrument.simluated.MidiDeviceSimImp;
import uicomponents.UIComponent;
import utility.Maybe;
import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class InstrumentBrowserImp implements UIComponent, InstrumentBrowser, ListSelectionListener {
    private final Receiver midiReceiver;
    private final JList<MidiDevice> deviceList;
    private final DefaultListModel<MidiDevice> listModel;

    private Maybe<MidiDevice> selectedDevice = new Maybe<>();
    private Transmitter transmitter;

    public InstrumentBrowserImp(Receiver midiReceiver){
        this.midiReceiver = midiReceiver;
        listModel = new DefaultListModel<>();
        deviceList = new JList<>(listModel);
        deviceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        deviceList.addListSelectionListener(this);
        deviceList.setCellRenderer(new InstrumentRenderer(deviceList.getCellRenderer()));

        refreshTransmitterDevices();
    }

    @Override
    public Component getComponent() {
        JScrollPane listScrollPane = new JScrollPane(deviceList);
        Border border = BorderFactory.createLineBorder(Color.GRAY);
        listScrollPane.setBorder(border);
        Dimension listSize = new Dimension(200, 100);
        deviceList.setPreferredSize(listSize);
        deviceList.setMaximumSize(listSize);
        deviceList.setMinimumSize(listSize);
        return listScrollPane;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            for(MidiDevice device : selectedDevice){
                if(device.isOpen()){
                    device.close();
                }
            }
            int selectedIndex = deviceList.getSelectedIndex();
            MidiDevice device = listModel.get(selectedIndex);
            try {
                device.open();
                transmitter = device.getTransmitter();
                transmitter.setReceiver(midiReceiver);
                selectedDevice = new Maybe<>(device);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void refreshTransmitterDevices() {
        listModel.clear();
        listModel.addElement(new MidiDeviceSimImp(deviceList));
        MidiDevice.Info[] deviceInfo = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : deviceInfo) {
            try {
                MidiDevice device = MidiSystem.getMidiDevice(info);
                if (device.getMaxTransmitters() != 0) {
                    listModel.addElement(device);
                }
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }
    }
}
