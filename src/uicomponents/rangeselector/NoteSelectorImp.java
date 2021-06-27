package uicomponents.rangeselector;

import music.Note;
import statemodels.NoteLimitModel;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NoteSelectorImp implements PopupMenuListener, ActionListener {
    private final NoteLimitModel noteLimitModel;
    private final JPanel panel;
    private final JComboBox<Note> noteComboBox;

    public NoteSelectorImp(NoteLimitModel noteLimitModel){
        this.noteLimitModel = noteLimitModel;
        this.panel = new JPanel();
        this.noteComboBox = new JComboBox<>();
        this.noteComboBox.setRenderer(new NoteListRenderer(noteComboBox.getRenderer(), noteLimitModel));
        this.noteComboBox.addPopupMenuListener(this);

        this.noteComboBox.addActionListener(this);
    }

    public JPanel getPanel(){
        panel.add(noteComboBox);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("comboBoxChanged")){
            Note selectedNote = (Note) noteComboBox.getSelectedItem();
            noteLimitModel.changeLimit(selectedNote);
        }
    }

    public void addActionListener(ActionListener actionListener){
        this.noteComboBox.addActionListener(actionListener);
    }

    public void refreshSelection(Note upperNote, Note lowerNote, Note selectedNote){
        noteComboBox.removeAllItems();
        for (Note noteIterator = new Note(upperNote); noteIterator.compareTo(lowerNote) >= 0; noteIterator.decrement()){
            Note noteToAdd = new Note(noteIterator);
            noteComboBox.addItem(noteToAdd);
            if (noteIterator.equals(selectedNote)){
                noteComboBox.setSelectedItem(noteToAdd);
            }
        }
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {
        ComboBoxModel<Note> listModel = noteComboBox.getModel();
        Note selectedNote = listModel.getElementAt(noteComboBox.getSelectedIndex());
        noteLimitModel.changeLimit(selectedNote);
    }
}
