package uicomponents.rangeselector;

import music.Note;
import music.NoteAccidental;
import music.NoteName;
import notification.RangeChangeNotifier;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.util.Vector;

public class RangeSelectorImp implements PopupMenuListener {
    static private final Note lowerBoundNote = new Note(NoteName.A, 0);
    static private final Note upperBoundNote = new Note(NoteName.C, 8);
    static private Note defaultLowerNote = new Note(NoteName.C, 4);
    static private Note defaultUpperNote = new Note(NoteName.A, 4);

    private final JComboBox<Note> upperNote;
    private final JComboBox<Note> lowerNote;
    private final Vector<Note> lowerRangeModel;
    private final Vector<Note> upperRangeModel;

    private final JPanel panel;

    public RangeSelectorImp(RangeChangeNotifier rangeChangeNotifier){
        this.lowerRangeModel = new Vector<>();
        this.lowerNote = new JComboBox<>(lowerRangeModel);
        this.lowerNote.setRenderer(new NoteRangeRenderer(lowerNote.getRenderer(), rangeChangeNotifier));
        this.lowerNote.addPopupMenuListener(this);
        int index = 0;
        int activeIndex = 0;
        for (Note noteIterator = new Note(defaultUpperNote); noteIterator.compareTo(lowerBoundNote) >= 0; noteIterator.decrement()){
            Note noteToAdd = new Note(noteIterator);
            noteToAdd.setAccidental(NoteAccidental.NATURAL, true);
            lowerRangeModel.add(noteToAdd);
            if (noteIterator.equals(defaultLowerNote)){
                activeIndex = index;
            }
            index++;
        }
        this.lowerNote.setSelectedIndex(activeIndex);

        this.upperRangeModel = new Vector<>();
        this.upperNote = new JComboBox<>(upperRangeModel);
        index = 0;
        activeIndex = 0;
        for (Note noteIterator = new Note(upperBoundNote); noteIterator.compareTo(defaultLowerNote) > 0; noteIterator.decrement()){
            Note noteToAdd = new Note(noteIterator);
            noteToAdd.setAccidental(NoteAccidental.NATURAL, true);
            upperRangeModel.add(noteToAdd);
            if (noteIterator.equals(defaultUpperNote)){
                activeIndex = index;
            }
            index++;
        }
        this.upperNote.setSelectedIndex(activeIndex);

        this.panel = new JPanel(new FlowLayout());
        panel.add(lowerNote);
        panel.add(upperNote);
    }

    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {
        System.out.println(e.toString());
    }
}
