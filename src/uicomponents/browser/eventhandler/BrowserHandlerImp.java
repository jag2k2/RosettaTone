package uicomponents.browser.eventhandler;

import uicomponents.browser.BrowserHandler;
import uicomponents.browser.InstrumentRenderer;
import uicomponents.renderer.records.RenderConstants;
import uicomponents.util.selectors.JListSelector;
import uicomponents.util.selectors.JSelector;
import uicomponents.util.selectors.JDeviceListSelectorImp;

import javax.imageio.ImageIO;
import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BrowserHandlerImp implements BrowserHandler, ActionListener {
    private final Receiver midiReceiver;
    private final MidiDeviceInquirer midiDeviceInquirer;
    private final Set<JSelector<MidiDevice>> selectors = new HashSet<>();


    public BrowserHandlerImp(Receiver midiReceiver, MidiDeviceInquirer midiDeviceInquirer) {
        this.midiReceiver = midiReceiver;
        this.midiDeviceInquirer = midiDeviceInquirer;
    }

    @Override
    public JSelector<MidiDevice> createDeviceList() {
        ListCellRenderer<MidiDevice> renderer = new InstrumentRenderer(new DefaultListCellRenderer());
        JListSelector<MidiDevice> selector = new JDeviceListSelectorImp(midiReceiver, midiDeviceInquirer);
        selector.setRenderer(renderer);
        selectors.add(selector);
        return selector;
    }

    @Override
    public AbstractButton createRefreshButton() {
        AbstractButton refreshButton = new JButton();
        try{
            URL fileURL = getClass().getResource(RenderConstants.refreshIconPath);
            Image refreshImage = ImageIO.read(Objects.requireNonNull(fileURL));
            ImageIcon refreshIcon = new ImageIcon(refreshImage);
            refreshButton.setIcon(refreshIcon);

        } catch (IOException e){
            refreshButton = new JButton("Refresh");
        }
        refreshButton.addActionListener(this);
        return refreshButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(JSelector<MidiDevice> selector : selectors){
            selector.refreshSelections();
        }
    }
}
