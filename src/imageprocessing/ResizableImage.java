package imageprocessing;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ResizableImage {
    private BufferedImage bufferedImage;

    public ResizableImage(BufferedImage bufferedImage){
        this.bufferedImage = bufferedImage;

    }

    public void resize(double scaleFactor){
        int beforeWidth = bufferedImage.getWidth();
        int beforeHeight = bufferedImage.getHeight();
        BufferedImage afterImage = new BufferedImage(beforeWidth, beforeHeight, BufferedImage.TYPE_INT_ARGB);
        AffineTransform transform = new AffineTransform();
        transform.scale(scaleFactor, scaleFactor);
        AffineTransformOp scaleOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        bufferedImage = scaleOp.filter(bufferedImage, afterImage);
    }

    public BufferedImage getBufferedImage(){
        return bufferedImage;
    }
}
