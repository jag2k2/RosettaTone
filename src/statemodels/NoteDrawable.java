package statemodels;

import utility.NoteSet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Set;

public interface NoteDrawable {
    void draw(Graphics2D graphics2D, BufferedImage noteImage, BufferedImage sharpImage, BufferedImage naturalImage, BufferedImage flatImage,
              NoteSet notes, int xPos, Set<Integer> ledgerLines, boolean drawName);
}
