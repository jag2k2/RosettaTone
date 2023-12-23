package instrument.simluated;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class MidiDeviceSimImp implements MidiDevice {
    private Transmitter midiTransmitter;
    private KeyListener keyListener;
    private final List<Transmitter> transmitters;
    private JComponent component;
    private boolean open;

    public MidiDeviceSimImp(JComponent component){
        this.component = component;
        MidiTransmitterSimImp transmitterSim = new MidiTransmitterSimImp();
        this.midiTransmitter = transmitterSim;
        this.keyListener = transmitterSim;
        this.transmitters = new ArrayList<>();
        this.open = false;
        transmitters.add(this.midiTransmitter);
    }

    @Override
    public Info getDeviceInfo() {
        return new SimulatedInfo();
    }

    @Override
    public void open() {
        component.addKeyListener(keyListener);
        component.setFocusable(true);
        open = true;
        System.out.println("Opening Simulated Device");
    }

    @Override
    public void close() {
        component.removeKeyListener(keyListener);
        open = false;
        System.out.println("Closing Simulated Device");
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public long getMicrosecondPosition() {
        return 0;
    }

    @Override
    public int getMaxReceivers() {
        return 0;
    }

    @Override
    public int getMaxTransmitters() {
        return 1;
    }

    @Override
    public Receiver getReceiver() {
        return null;
    }

    @Override
    public List<Receiver> getReceivers() {
        return null;
    }

    @Override
    public Transmitter getTransmitter() {
        return midiTransmitter;
    }

    @Override
    public List<Transmitter> getTransmitters() {
        return transmitters;
    }
}
