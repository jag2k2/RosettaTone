package instrument;

import music.Note;
import music.NoteAccidental;
import music.NoteName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KeyTest {
    Key keyDSharp2 = new Key(39);
    Key keyC4 = new Key(60);
    Key keyCSharp4 = new Key(61);
    Key keyD4 = new Key(62);
    Key keyDSharp4 = new Key(63);
    Key keyE4 = new Key(64);
    Key keyF4 = new Key(65);
    Key keyFSharp4 = new Key(66);
    Key keyG4 = new Key(67);
    Key keyGSharp4 = new Key(68);
    Key keyA4 = new Key(69);
    Key keyASharp4 = new Key(70);
    Key keyB4 = new Key(71);
    Key keyGSharp5 = new Key(80);

    @Test
    void canConstructFromNoteInfo(){
        Key compareC4 = new Key(4, NoteName.C, NoteAccidental.NATURAL);
        assertEquals(keyC4, compareC4);

        Key compareCSharp4 = new Key(4, NoteName.C, NoteAccidental.SHARP);
        assertEquals(keyCSharp4, compareCSharp4);

        Key compareD4 = new Key(4, NoteName.D, NoteAccidental.NATURAL);
        assertEquals(keyD4, compareD4);

        Key compareDSharp4 = new Key(4, NoteName.D, NoteAccidental.SHARP);
        assertEquals(keyDSharp4, compareDSharp4);

        Key compareE4 = new Key(4, NoteName.E, NoteAccidental.NATURAL);
        assertEquals(keyE4, compareE4);

        Key compareF4 = new Key(4, NoteName.F, NoteAccidental.NATURAL);
        assertEquals(keyF4, compareF4);

        Key compareFSharp4 = new Key(4, NoteName.F, NoteAccidental.SHARP);
        assertEquals(keyFSharp4, compareFSharp4);

        Key compareG4 = new Key(4, NoteName.G, NoteAccidental.NATURAL);
        assertEquals(keyG4, compareG4);

        Key compareGSharp4 = new Key(4, NoteName.G, NoteAccidental.SHARP);
        assertEquals(keyGSharp4, compareGSharp4);

        Key compareA4 = new Key(4, NoteName.A, NoteAccidental.NATURAL);
        assertEquals(keyA4, compareA4);

        Key compareASharp4 = new Key(4, NoteName.A, NoteAccidental.SHARP);
        assertEquals(keyASharp4, compareASharp4);

        Key compareB4 = new Key(4, NoteName.B, NoteAccidental.NATURAL);
        assertEquals(keyB4, compareB4);

        Key compareDFlat4 = new Key(4, NoteName.D, NoteAccidental.FLAT);
        assertEquals(keyCSharp4, compareDFlat4);

        Key compareGFlat4 = new Key(4, NoteName.G, NoteAccidental.FLAT);
        assertEquals(keyFSharp4, compareGFlat4);
    }

    @Test
    void getOctaves() {
        assertEquals(2, keyDSharp2.getOctave());
        assertEquals(4, keyC4.getOctave());
        assertEquals(4, keyA4.getOctave());
        assertEquals(5, keyGSharp5.getOctave());
    }

    @Test
    void getOctaveKeys() {
        assertEquals(0, keyC4.getOctavePosition());
        assertEquals(3, keyDSharp2.getOctavePosition());
        assertEquals(8, keyGSharp5.getOctavePosition());
        assertEquals(9, keyA4.getOctavePosition());
    }

    @Test
    void getNaturalIndexes() {
        assertEquals(0, keyC4.getNaturalIndex());
        assertEquals(1, keyDSharp2.getNaturalIndex());
        assertEquals(4, keyGSharp5.getNaturalIndex());
        assertEquals(5, keyA4.getNaturalIndex());
    }

    @Test
    void getAccidentals() {
        assertEquals(NoteAccidental.SHARP, keyDSharp2.getAccidental());
        assertEquals(NoteAccidental.NATURAL, keyC4.getAccidental());
        assertEquals(NoteAccidental.SHARP, keyGSharp4.getAccidental());
        assertEquals(NoteAccidental.NATURAL, keyG4.getAccidental());
    }

    @Test
    void isCDorEChecks() {
        assertTrue(keyC4.isCDorE());
        assertTrue(keyDSharp2.isCDorE());
        assertFalse(keyGSharp5.isCDorE());
        assertFalse(keyA4.isCDorE());
    }

    @Test
    void isEvenChecks() {
        assertTrue(keyC4.isEven());
        assertFalse(keyDSharp2.isEven());
        assertTrue(keyGSharp5.isEven());
        assertFalse(keyA4.isEven());
    }

    @Test
    void canEquals() {
        Key compare = new Key(60);
        assertEquals(keyC4, compare);
        assertNotEquals(keyDSharp2, compare);
    }

    @Test
    void canGenerateRandomKey(){
        int iterations = 10000;
        List<Key> octave4 = new ArrayList<>();
        octave4.add(keyC4);
        octave4.add(keyCSharp4);
        octave4.add(keyD4);
        octave4.add(keyDSharp4);
        octave4.add(keyE4);
        octave4.add(keyF4);
        octave4.add(keyFSharp4);
        octave4.add(keyG4);
        octave4.add(keyGSharp4);
        octave4.add(keyA4);
        octave4.add(keyASharp4);
        octave4.add(keyB4);
        int intervalSize = octave4.size();

        HashMap<Key, Integer> histogram = new HashMap<>();

        Key upperKey = keyB4;
        for (int i = 0; i < iterations; i++){
            Key randomKey = keyC4.generateRandomKeyBetweenThisAnd(upperKey);
            if (octave4.contains(randomKey)){
                int keyIndex = octave4.indexOf(randomKey);
                int count = histogram.getOrDefault(octave4.get(keyIndex), 0);
                count++;
                histogram.put(octave4.get(keyIndex), count);
            }
        }

        assertEquals(intervalSize, histogram.size());

        for (Key key : octave4){
            int total = histogram.getOrDefault(key, 0);
            System.out.println(key + " -> " + total);
            int minimumCount = (int) ((float) iterations / intervalSize * 0.85);
            assertTrue(total > minimumCount);
        }
    }
}