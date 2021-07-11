package statemodels;

import music.Note;
import music.NoteCollectionImp;
import music.NoteName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.NoteCollection;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class NoteLimitModelImpTest {
    private NoteLimitModelImp noteLimitModel;
    private final Note lowerLimit = new Note(NoteName.A, 0);
    private final Note activeNote = new Note(NoteName.C, 4);
    private final Note upperLimit = new Note(NoteName.C, 5);

    @BeforeEach
    void setup(){
        noteLimitModel = new NoteLimitModelImp(lowerLimit, activeNote, upperLimit);
    }

    @Test
    void canCheckEquality() {
        NoteLimitModelImp modelToCompare = new NoteLimitModelImp(lowerLimit, activeNote, upperLimit);
        assertEquals(modelToCompare, noteLimitModel);

        modelToCompare = new NoteLimitModelImp(new Note(NoteName.C, 8), activeNote, upperLimit);
        assertNotEquals(modelToCompare, noteLimitModel);
    }

    @Test
    void canDisplayAsString(){
        String compareString = "[absolute: A0, active: C4, available: C5]";
        assertEquals(compareString, noteLimitModel.toString());
    }

    @Test
    void canSetActiveWithinRange(){
        Note newActiveNote = new Note(NoteName.C, 3);
        noteLimitModel.setActiveLimit(newActiveNote);
        NoteLimitModelImp modelToCompare = new NoteLimitModelImp(lowerLimit, newActiveNote, upperLimit);
        assertEquals(modelToCompare, noteLimitModel);
    }

    @Test
    void wontSetActiveOutsideRange(){
        NoteLimitModelImp modelToCompare = new NoteLimitModelImp(lowerLimit, activeNote, upperLimit);

        Note noteTooHigh = new Note(NoteName.C, 6);
        noteLimitModel.setActiveLimit(noteTooHigh);
        assertEquals(modelToCompare, noteLimitModel);

        Note noteTooLow = new Note(NoteName.C, 0);
        noteLimitModel.setActiveLimit(noteTooLow);
        assertEquals(modelToCompare, noteLimitModel);
    }

    @Test
    void canDecrementActive(){
        Note newActiveNote = new Note(NoteName.B, 3);
        NoteLimitModelImp modelToCompare = new NoteLimitModelImp(lowerLimit, newActiveNote, upperLimit);
        noteLimitModel.decrementActive();
        assertEquals(modelToCompare, noteLimitModel);
    }

    @Test
    void wontDecrementActive(){
        noteLimitModel = new NoteLimitModelImp(lowerLimit, lowerLimit, upperLimit);
        noteLimitModel.decrementActive();
        NoteLimitModelImp modelToCompare = new NoteLimitModelImp(lowerLimit, lowerLimit, upperLimit);
        assertEquals(modelToCompare, noteLimitModel);
    }

    @Test
    void canIncrementActive(){
        Note newActiveNote = new Note(NoteName.D, 4);
        NoteLimitModelImp modelToCompare = new NoteLimitModelImp(lowerLimit, newActiveNote, upperLimit);
        noteLimitModel.incrementActive();
        assertEquals(modelToCompare, noteLimitModel);
    }

    @Test
    void wontIncrementActive(){
        noteLimitModel = new NoteLimitModelImp(lowerLimit, upperLimit, upperLimit);
        noteLimitModel.incrementActive();
        NoteLimitModelImp modelToCompare = new NoteLimitModelImp(lowerLimit, upperLimit, upperLimit);
        assertEquals(modelToCompare, noteLimitModel);
    }

    @Test
    void canUpdateComboBoxOptions(){
        NoteCollection expectedOptions = new NoteCollectionImp();
        expectedOptions.add(new Note(NoteName.B, 4));
        expectedOptions.add(new Note(NoteName.A, 4));
        expectedOptions.add(new Note(NoteName.G, 4));
        expectedOptions.add(new Note(NoteName.F, 4));
        expectedOptions.add(new Note(NoteName.E, 4));
        expectedOptions.add(new Note(NoteName.D, 4));
        expectedOptions.add(new Note(NoteName.C, 4));

        JComboBox<Note> comboBox = new JComboBox<>();
        Note activeNote = new Note(NoteName.E, 4);
        noteLimitModel = new NoteLimitModelImp(new Note(NoteName.C, 4), activeNote, new Note(NoteName.B, 4));
        noteLimitModel.refreshJComboBoxOptions(comboBox);
        ComboBoxModel<Note> comboBoxModel = comboBox.getModel();
        NoteCollection comboBoxOptions = new NoteCollectionImp();
        for (int i = 0; i < comboBoxModel.getSize(); i++){
            comboBoxOptions.add(comboBoxModel.getElementAt(i));
        }

        assertEquals(expectedOptions, comboBoxOptions);
        assertEquals(activeNote, comboBox.getSelectedItem());
    }
}