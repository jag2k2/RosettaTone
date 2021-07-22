package tuples;

import music.line.LedgerLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineSetImpTest {
    private LineSetImp lineSet;

    @BeforeEach
    void setup(){
        lineSet = new LineSetImp();
        lineSet.add(new LedgerLine(20, 0 ,0));
        lineSet.add(new LedgerLine(21, 0, 0));
        lineSet.add(new LedgerLine(25, 0, 0));
    }

    @Test
    void canDisplayAsString() {
        assertEquals("[20, 21, 25]", lineSet.toString());
    }

    @Test
    void canCheckEquality() {
        LineSetImp expected = new LineSetImp();
        expected.add(new LedgerLine(20, 0 ,0));
        expected.add(new LedgerLine(21, 0, 0));
        expected.add(new LedgerLine(25, 0, 0));

        assertEquals(expected, lineSet);
    }
}