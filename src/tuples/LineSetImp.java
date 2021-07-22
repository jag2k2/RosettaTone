package tuples;

import music.LineDrawable;
import utility.LineSet;

import java.awt.*;
import java.util.*;
import java.util.List;

public class LineSetImp implements LineSet{
    private final Set<LineDrawable> lines;

    public LineSetImp() {
        this.lines = new HashSet<>();
    }

    @Override
    public void add(LineDrawable line) {
        lines.add(line);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        for (LineDrawable line : lines){
            line.draw(graphics2D);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LineSetImp){
            LineSetImp compareTo = (LineSetImp) obj;
            return lines.equals(compareTo.lines);
        }
        return false;
    }

    @Override
    public String toString() {
        //List<LineDrawable> lineList = new ArrayList<>(lines);
        //Collections.sort(lineList);
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (LineDrawable line : lines){
            stringJoiner.add(line.toString());
        }
        return stringJoiner.toString();
    }
}
