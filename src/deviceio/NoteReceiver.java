package deviceio;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

public interface NoteReceiver extends Receiver, NoteChangeNotifier{

}
