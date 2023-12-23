package com.jag2k2.uicomponents.renderer.limit;

import java.awt.*;

public interface LimitDrawable {
    void draw(Graphics2D graphics2D);
    void drawConnection(Graphics2D graphics2D, LimitDrawable otherLimit);
}
