package uicomponents.rangeselector.noteselector;

import music.Note;
import music.NoteAccidental;
import uicomponents.rangeselector.NoteSelector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Objects;

public class NoteSelectorImp implements NoteSelector, ActionListener, PopupMenuListener {
    private static final String upButtonName = "upButton";
    private static final String downButtonName = "downButton";
    private final NoteLimitModel noteLimitModel;
    private final JComboBox<Note> noteComboBox;
    private JButton upButton;
    private JButton downButton;

    public NoteSelectorImp(NoteLimitModel noteLimitModel){
        this.noteLimitModel = noteLimitModel;
        this.noteComboBox = new JComboBox<>();
        this.noteComboBox.setRenderer(new NoteListRenderer(noteComboBox.getRenderer(), noteLimitModel));
        try{
            URL upArrowURL = getClass().getResource("/images/UpArrow.png");
            URL downArrowURL = getClass().getResource("/images/DownArrow.png");
            Image upArrowImage = ImageIO.read(Objects.requireNonNull(upArrowURL));
            Image downArrowImage = ImageIO.read(Objects.requireNonNull(downArrowURL));
            ImageIcon upArrowIcon = new ImageIcon(upArrowImage);
            ImageIcon downArrowIcon = new ImageIcon(downArrowImage);
            this.upButton = new JButton(upArrowIcon);
            this.downButton = new JButton(downArrowIcon);
        } catch (Exception e) {
            this.upButton = new JButton();
            this.downButton = new JButton();
        }

        this.upButton.setName("upButton");
        this.downButton.setName("downButton");

        this.noteComboBox.addPopupMenuListener(this);
        this.noteComboBox.addActionListener(this);
        this.upButton.addActionListener(this);
        this.downButton.addActionListener(this);
    }

    @Override
    public Component getComponent(){
        JPanel panel = new JPanel(new BorderLayout());
        Dimension boxSize = new Dimension(80, 40);
        noteComboBox.setPreferredSize(boxSize);
        panel.add(BorderLayout.NORTH, upButton);
        panel.add(BorderLayout.CENTER, noteComboBox);
        panel.add(BorderLayout.SOUTH, downButton);
        return panel;
    }

    @Override
    public void refreshSelection(Note upperNote, Note lowerNote, Note selectedNote){
        noteComboBox.removeAllItems();
        for (Note noteIterator = upperNote; noteIterator.compareTo(lowerNote) >= 0; noteIterator = noteIterator.getPrevious(NoteAccidental.NATURAL)){
            noteComboBox.addItem(noteIterator);
            if (noteIterator.equals(selectedNote)){
                noteComboBox.setSelectedItem(noteIterator);
            }
        }
    }

    @Override
    public Note getSelectedNote() {
        return noteLimitModel.getLimit();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("comboBoxChanged")){
            Note selectedNote = (Note) noteComboBox.getSelectedItem();
            noteLimitModel.setLimit(selectedNote);
        }
        else if (e.getSource() instanceof JButton){
            JButton pushedButton = (JButton) e.getSource();
            if (pushedButton.getName().equals(upButtonName)){
                System.out.println("Up Button Pushed!");
            }
            else if (pushedButton.getName().equals(downButtonName)){
                System.out.println("Down Button Pushed!");
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
        noteLimitModel.setLimit(selectedNote);
    }
}
