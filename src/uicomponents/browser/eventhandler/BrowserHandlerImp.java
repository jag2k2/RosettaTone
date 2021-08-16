package uicomponents.browser.eventhandler;

import uicomponents.browser.BrowserHandler;
import uicomponents.browser.InstrumentRenderer;
import uicomponents.renderer.records.RenderConstants;
import utility.Maybe;
import javax.imageio.ImageIO;
import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class BrowserHandlerImp implements BrowserHandler, ActionListener, ListSelectionListener {
    private final Receiver midiReceiver;
    private final MidiDeviceInquirer midiDeviceInquirer;
    private final DefaultListModel<MidiDevice> listModel;

    private Maybe<MidiDevice> openDevice = new Maybe<>();

    public BrowserHandlerImp(Receiver midiReceiver, MidiDeviceInquirer midiDeviceInquirer) {
        this.midiReceiver = midiReceiver;
        this.midiDeviceInquirer = midiDeviceInquirer;
        this.listModel = new DefaultListModel<>();
    }

    @Override
    public JList<MidiDevice> createDeviceList() {
        JList<MidiDevice> deviceList = new JList<>(listModel);
        deviceList.setCellRenderer(new InstrumentRenderer(deviceList.getCellRenderer()));
        deviceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        deviceList.addListSelectionListener(this);
        return deviceList;
    }

    @Override
    public AbstractButton createRefreshButton() {
        AbstractButton refreshButton = new JButton();
        try{
            URL fileURL = getClass().getResource(RenderConstants.refreshIconPath);
            Image refreshImage = ImageIO.read(Objects.requireNonNull(fileURL));
            ImageIcon refreshIcon = new ImageIcon(refreshImage);
            refreshButton.setIcon(refreshIcon);

        } catch (IOException e){
            e.printStackTrace();
        }
        refreshButton.addActionListener(this);
        return refreshButton;
    }

    @Override
    public void refreshTransmitterDevices() {
        listModel.clear();
        for (MidiDevice device : midiDeviceInquirer.getMidiDevices()) {
            if (device.getMaxTransmitters() != 0) {
                listModel.addElement(device);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() instanceof JList<?>){
            JList<?> deviceList = (JList<?>) e.getSource();
            if (!e.getValueIsAdjusting()) {
                for(MidiDevice device : openDevice){
                    if(device.isOpen()){
                        device.close();
                    }
                }
                Object selected = deviceList.getSelectedValue();
                if (selected instanceof MidiDevice && deviceList.getSelectedIndex() >= 0){
                    MidiDevice selectedDevice = (MidiDevice) selected;
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        refreshTransmitterDevices();
    }
}
