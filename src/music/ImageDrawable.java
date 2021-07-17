package music;

import java.awt.*;

public interface ImageDrawable {
    void draw(Graphics2D graphics2D, int xPos, int yPos);
    int getWidth();
    int getHeight();
}
