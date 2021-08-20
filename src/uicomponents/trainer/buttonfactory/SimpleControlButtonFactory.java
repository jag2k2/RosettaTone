package uicomponents.trainer.buttonfactory;

import uicomponents.trainer.ControlButtonFactory;

import javax.swing.*;

public class SimpleControlButtonFactory implements ControlButtonFactory {
    @Override
    public AbstractButton constructButton(String text) {
        return new JButton(text);
    }
}
