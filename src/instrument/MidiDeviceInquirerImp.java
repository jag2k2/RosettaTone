package instrument;

import instrument.simluated.MidiDeviceSimImp;
import uicomponents.browser.eventhandler.MidiDeviceInquirer;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MidiDeviceInquirerImp implements MidiDeviceInquirer {
    private final JPanel panel;

    public MidiDeviceInquirerImp(JPanel panel) {
        this.panel = panel;
    }

    @Override
    public List<MidiDevice> getMidiDevices() {
        List<MidiDevice> devices = new ArrayList<>();
        MidiDevice simDevice = new MidiDeviceSimImp(panel);
        devices.add(simDevice);
        MidiDevice.Info[] deviceInfo = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : deviceInfo) {
            try {
                MidiDevice device = MidiSystem.getMidiDevice(info);
                devices.add(device);
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }
        return devices;
    }
}
