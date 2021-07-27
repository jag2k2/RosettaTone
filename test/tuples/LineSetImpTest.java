package tuples;

import music.line.LedgerLine;
import music.line.Line;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.LineSet;

import static org.junit.jupiter.api.Assertions.*;

class LineSetImpTest {
    private LineSet lineSet;

    @BeforeEach
    void setup(){
        lineSet = new LineSetImp();
        lineSet.add(new LedgerLine(new Line(20), 0 ,0));
        lineSet.add(new LedgerLine(new Line(21), 0, 0));
        lineSet.add(new LedgerLine(new Line(25), 0, 0));
    }

    @Test
    void canDisplayAsString() {
        assertEquals("[20, 21, 25]", lineSet.toString());
    }

    @Test
    void canCheckEquality() {
        LineSet expected = new LineSetImp();
        expected.add(new LedgerLine(new Line(20), 0 ,0));
        expected.add(new LedgerLine(new Line(21), 0, 0));
        expected.add(new LedgerLine(new Line(25), 0, 0));

        assertEquals(expected, lineSet);
    }
}