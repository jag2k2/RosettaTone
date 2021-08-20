package uicomponents.browser;

import javax.swing.border.EtchedBorder;
import javax.swing.*;
import java.awt.*;

public class InstrumentBrowserImp extends JComponent {
    public InstrumentBrowserImp(BrowserHandler browserHandler){
        Component deviceList = browserHandler.createDeviceList();
        AbstractButton refreshButton = browserHandler.createRefreshButton();

        deviceList.setPreferredSize(new Dimension(200, 100));
        refreshButton.setPreferredSize(new Dimension(20, 20));

        FlowLayout buttonPanelLayout = new FlowLayout(FlowLayout.LEFT);
        buttonPanelLayout.setVgap(1);
        JPanel buttonPanel = new JPanel(buttonPanelLayout);
        buttonPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        buttonPanel.add(refreshButton);

        JScrollPane listScrollPane = new JScrollPane(deviceList);
        listScrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        this.setLayout(new BorderLayout());
        this.add(BorderLayout.NORTH, buttonPanel);
        this.add(BorderLayout.CENTER, listScrollPane);

        refreshButton.doClick();
    }
}
