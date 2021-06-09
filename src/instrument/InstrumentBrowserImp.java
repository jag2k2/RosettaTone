package instrument;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class InstrumentBrowserImp implements InstrumentBrowser {
    private final List<MidiDevice> transmitterDevices;

    public InstrumentBrowserImp(){
        transmitterDevices = new ArrayList<>();
    }

    @Override
    public List<MidiDevice> getTransmitterDevices() {

        MidiDevice.Info[] deviceInfo = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : deviceInfo) {
            try {
                MidiDevice device = MidiSystem.getMidiDevice(info);
                if (device.getMaxTransmitters() != 0) {
                    System.out.println(device.getDeviceInfo().getName());
                    transmitterDevices.add(device);
                }
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }

        }
        return transmitterDevices;
    }

    @Override
    public Transmitter getSelectedTransmitter() {
        MidiDevice selectedDevice = transmitterDevices.get(0);
        try{
            selectedDevice.open();
            return selectedDevice.getTransmitter();
        } catch (MidiUnavailableException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Transmitter getSimulatedTransmitter(JFrame frame) {
        MidiInstrumentSimImp simulatedInstrument = new MidiInstrumentSimImp();
        frame.addKeyListener(simulatedInstrument);
        frame.setFocusable(true);
        return simulatedInstrument;
    }
}
