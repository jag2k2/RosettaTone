package utility;

import music.line.Line;

public interface LineSet<T extends Line> extends Iterable<T>{
    void add(T line);
}
