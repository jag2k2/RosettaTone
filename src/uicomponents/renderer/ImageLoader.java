package uicomponents.renderer;

import java.awt.image.BufferedImage;

public interface ImageLoader {
    BufferedImage getTrebleImage();
    BufferedImage getBassImage();
    BufferedImage getNoteImage();
    BufferedImage getSharpImage();
    BufferedImage getNaturalImage();
    BufferedImage getFlatImage();
}
