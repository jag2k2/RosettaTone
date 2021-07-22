package tuples;

import music.Line;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineSetImpTest {
    private LineSetImp<Line> lineSet;

    @BeforeEach
    void setup(){
        lineSet = new LineSetImp<>();
        lineSet.add(new Line(20));
        lineSet.add(new Line(21));
        lineSet.add(new Line(25));
    }

    @Test
    void canDisplayAsString() {
        assertEquals("[20, 21, 25]", lineSet.toString());
    }

    @Test
    void canCheckEquality() {
        LineSetImp<Line> expected = new LineSetImp<>();
        expected.add(new Line(20));
        expected.add(new Line(21));
        expected.add(new Line(25));

        assertEquals(expected, lineSet);
    }
}