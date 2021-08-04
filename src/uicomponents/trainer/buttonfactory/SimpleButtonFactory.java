package uicomponents.trainer.buttonfactory;

import uicomponents.trainer.statechangers.ButtonFactory;

import javax.swing.*;

public class SimpleButtonFactory implements ButtonFactory {
    @Override
    public AbstractButton constructButton(String text) {
        return new JButton(text);
    }
}
