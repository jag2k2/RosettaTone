package deviceio;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;


public class InstrumentImp implements Instrument {
    MidiDevice piano;

    @Override
    public void connect(MidiDevice.Info info, Receiver receiver) {
        try{
            piano = MidiSystem.getMidiDevice(info);
            piano.open();
            Transmitter transmitter = piano.getTransmitter();
            transmitter.setReceiver(receiver);
        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    @Override
    public void close(){
        piano.close();
    }
}
