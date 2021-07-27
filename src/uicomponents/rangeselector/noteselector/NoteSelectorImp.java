package uicomponents.rangeselector.noteselector;

import music.note.Note;
import notification.LimitChangeObserver;
import uicomponents.UIComponent;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Objects;

public class NoteSelectorImp implements UIComponent, ActionListener, PopupMenuListener, LimitChangeObserver {
    private static final String upButtonName = "upButton";
    private static final String downButtonName = "downButton";
    private final BoundedNoteModifier boundedNoteLimit;
    private final LimitModifier previewLimit;
    private final JComboBox<Note> noteComboBox;
    private JButton upButton;
    private JButton downButton;

    public NoteSelectorImp(BoundedNoteModifier boundedNoteLimit, NoteListRenderer noteListRenderer, LimitModifier previewLimit){
        this.boundedNoteLimit = boundedNoteLimit;
        this.previewLimit = previewLimit;
        this.noteComboBox = new JComboBox<>();
        this.noteComboBox.setRenderer(noteListRenderer);
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
        boundedNoteLimit.refreshJComboBoxOptions(noteComboBox);
        return panel;
    }

    @Override
    public void limitChanged() {
        boundedNoteLimit.refreshJComboBoxOptions(noteComboBox);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("comboBoxChanged")){
            if (noteComboBox.getSelectedIndex() > 0) {
                Note selectedNote = (Note) noteComboBox.getSelectedItem();
                boundedNoteLimit.setLimit(selectedNote);
            }
        }
        else if (e.getSource() instanceof JButton){
            JButton pushedButton = (JButton) e.getSource();
            if (pushedButton.getName().equals(upButtonName)){
                boundedNoteLimit.increment();

            }
            else if (pushedButton.getName().equals(downButtonName)){
                boundedNoteLimit.decrement();
            }
            previewLimit.setLimit(boundedNoteLimit.getLimit());
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
        boundedNoteLimit.setLimit(selectedNote);
        previewLimit.setLimit(boundedNoteLimit.getLimit());
    }
}
