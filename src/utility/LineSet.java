package utility;

import music.LineDrawable;

import java.awt.*;

public interface LineSet {
    void add(LineDrawable line);
    void draw(Graphics2D graphics2D);
}
