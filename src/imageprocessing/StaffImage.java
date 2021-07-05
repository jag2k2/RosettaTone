package imageprocessing;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class StaffImage {
    private BufferedImage bufferedImage;

    public StaffImage(String path){
        try {
            URL fileURL = getClass().getResource(path);
            this.bufferedImage = ImageIO.read(Objects.requireNonNull(fileURL));
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    public BufferedImage getBufferedImage(){
        return bufferedImage;
    }

    public void resize(double scaleFactor){
        int newWidth = (int) (bufferedImage.getWidth() * scaleFactor);
        int newHeight = (int) (bufferedImage.getHeight() * scaleFactor);
        Image scaledImage = bufferedImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
        bufferedImage = convertImageToBufferedImage(scaledImage, newWidth, newHeight);
    }

    protected BufferedImage convertImageToBufferedImage(Image image, int width, int height)
    {
        BufferedImage destinationImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = destinationImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return destinationImage;
    }
}
