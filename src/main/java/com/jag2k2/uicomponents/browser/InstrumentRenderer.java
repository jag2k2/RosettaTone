package uicomponents.browser;

import javax.sound.midi.MidiDevice;
import javax.swing.*;
import java.awt.*;

public class InstrumentRenderer implements ListCellRenderer<MidiDevice> {
    ListCellRenderer<? super MidiDevice> cellRenderer;

    public InstrumentRenderer(ListCellRenderer<? super MidiDevice> cellRenderer){
        this.cellRenderer = cellRenderer;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends MidiDevice> list, MidiDevice value, int index, boolean isSelected, boolean cellHasFocus) {
        Component rendererComponent = cellRenderer.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
        if (rendererComponent instanceof JLabel){
            JLabel stringRenderer = (JLabel) cellRenderer;
            String deviceName = value.getDeviceInfo().getName();
            stringRenderer.setText(deviceName);
        }
        return rendererComponent;
    }
}
