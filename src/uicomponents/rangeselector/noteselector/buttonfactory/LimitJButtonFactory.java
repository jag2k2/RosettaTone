package uicomponents.rangeselector.noteselector.buttonfactory;

import uicomponents.rangeselector.noteselector.LimitButtonFactory;
import uicomponents.renderer.records.RenderConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class LimitJButtonFactory implements LimitButtonFactory {
    @Override
    public AbstractButton constructIncrementButton() {
        try{

            URL upArrowURL = getClass().getResource(RenderConstants.incrementIconPath);
            Image upArrowImage = ImageIO.read(Objects.requireNonNull(upArrowURL));
            ImageIcon upArrowIcon = new ImageIcon(upArrowImage);
            return new JButton(upArrowIcon);
        } catch (Exception e){
            return new JButton("UP");
        }
    }

    @Override
    public AbstractButton constructDecrementButton() {
        try {
            URL downArrowURL = getClass().getResource(RenderConstants.decrementIconPath);
            Image downArrowImage = ImageIO.read(Objects.requireNonNull(downArrowURL));
            ImageIcon downArrowIcon = new ImageIcon(downArrowImage);
            return new JButton(downArrowIcon);
        } catch (Exception e){
            return new JButton("DOWN");
        }
    }
}
