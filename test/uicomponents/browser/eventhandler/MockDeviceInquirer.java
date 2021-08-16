package uicomponents.browser.eventhandler;

import instrument.simluated.MidiDeviceSimImp;

import javax.sound.midi.MidiDevice;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MockDeviceInquirer implements MidiDeviceInquirer{
    private final List<MidiDevice> devices;

    public MockDeviceInquirer() {
        this.devices = new ArrayList<>();

        MidiDevice device1 = new MidiDeviceSimImp(new JPanel());
        MidiDevice device2 = new MidiDeviceSimImp(new JPanel());
        MidiDevice device3 = new MidiDeviceSimImp(new JPanel());

        devices.add(device1);
        devices.add(device2);
        devices.add(device3);
    }

    @Override
    public List<MidiDevice> getMidiDevices() {
        return devices;
    }
}
