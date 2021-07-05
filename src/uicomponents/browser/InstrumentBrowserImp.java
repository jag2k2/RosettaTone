package uicomponents.browser;

import instrument.simluated.MidiDeviceSimImp;
import uicomponents.UIComponent;
import utility.Maybe;

import javax.imageio.ImageIO;
import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class InstrumentBrowserImp implements UIComponent, InstrumentBrowser, ListSelectionListener, ActionListener {
    private final Receiver midiReceiver;
    private final JList<MidiDevice> deviceList;
    private final DefaultListModel<MidiDevice> listModel;

    private JButton refreshButton;
    private Maybe<MidiDevice> selectedDevice = new Maybe<>();

    public InstrumentBrowserImp(Receiver midiReceiver){
        this.midiReceiver = midiReceiver;
        listModel = new DefaultListModel<>();
        deviceList = new JList<>(listModel);
        deviceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        deviceList.addListSelectionListener(this);
        deviceList.setCellRenderer(new InstrumentRenderer(deviceList.getCellRenderer()));

        try{
            URL fileURL = getClass().getResource("/images/refresh.png");
            Image refreshImage = ImageIO.read(Objects.requireNonNull(fileURL));
            ImageIcon refreshIcon = new ImageIcon(refreshImage);
            refreshButton = new JButton(refreshIcon);

        } catch (IOException e){
            e.printStackTrace();
        }

        refreshButton.addActionListener(this);
        refreshTransmitterDevices();
    }

    @Override
    public Component getComponent() {
        refreshButton.setPreferredSize(new Dimension(20, 20));
        FlowLayout buttonPanelLayout = new FlowLayout(FlowLayout.LEFT);
        buttonPanelLayout.setVgap(1);
        JPanel buttonPanel = new JPanel(buttonPanelLayout);
        buttonPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        buttonPanel.add(refreshButton);
        JPanel browserPanel = new JPanel(new BorderLayout());
        browserPanel.add(BorderLayout.NORTH, buttonPanel);
        browserPanel.add(BorderLayout.CENTER, deviceList);
        JScrollPane listScrollPane = new JScrollPane(browserPanel);
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
            MidiDevice device = deviceList.getSelectedValue();
            if (selectedIndex >= 0){
                try {
                    device.open();
                    Transmitter transmitter = device.getTransmitter();
                    transmitter.setReceiver(midiReceiver);
                    selectedDevice = new Maybe<>(device);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        refreshTransmitterDevices();
    }

    @Override
    public void refreshTransmitterDevices() {
        listModel.clear();
        MidiDevice simDevice = new MidiDeviceSimImp(deviceList);
        listModel.addElement(simDevice);
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
