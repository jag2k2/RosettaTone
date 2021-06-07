package deviceio;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;
import java.util.List;

public class InstrumentBrowserImp implements InstrumentBrowser {

    @Override
    public List<MidiDevice> getTransmitterDevices() {
        ArrayList<MidiDevice> transmitterDevices = new ArrayList<>();

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
}
