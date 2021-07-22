package tuples;

import music.Line;
import utility.LineSet;

import java.util.*;

public class LineSetImp<T extends Line> implements LineSet<T>{
    private final Set<T> lines;

    public LineSetImp() {
        this.lines = new HashSet<>();
    }

    @Override
    public void add(T line) {
        lines.add(line);
    }

    @Override
    public Iterator<T> iterator() {
        return lines.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LineSetImp<?>){
            LineSetImp<?> compareTo = (LineSetImp<?>) obj;
            return lines.equals(compareTo.lines);
        }
        return false;
    }

    @Override
    public String toString() {
        List<T> lineList = new ArrayList<>(lines);
        Collections.sort(lineList);
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (T line : lineList){
            stringJoiner.add(line.toString());
        }
        return stringJoiner.toString();
    }
}
