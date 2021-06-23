package uicomponents.rangeselector;

import music.Note;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class RangeSelectorImp {
    static private final int leftMostKey = 21;
    static private final int defaultLowerKey = 60;
    static private final int rightMostKey = 108;
    static private final int defaultUpperKey = 69;

    private final JComboBox<Note> upperNote;
    private final JComboBox<Note> lowerNote;
    //private final Vector<Note> lowerRangeModel;
    //private final Vector<Note> upperRangeModel;

    private final JPanel panel;

    public RangeSelectorImp(){
        this.upperNote = new JComboBox<>();
        this.lowerNote = new JComboBox<>();
        for (int key = leftMostKey; key <= defaultUpperKey; key++){
            //lowerRangeModel.add(new Note(key));
        }
        for (int key = defaultLowerKey; key <= rightMostKey; key++){
            //upperRangeModel.add(new Note(key));
        }

        this.panel = new JPanel(new FlowLayout());
        panel.add(upperNote);
        panel.add(lowerNote);
    }

    public JPanel getPanel() {
        return panel;
    }
}
