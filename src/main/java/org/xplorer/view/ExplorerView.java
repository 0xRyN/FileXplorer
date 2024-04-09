package org.xplorer.view;

import javax.swing.*;
import java.awt.*;


public class ExplorerView extends JFrame {
    private JPanel mainPanel;

    public ExplorerView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Explorer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 1000);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));

        getContentPane().add(mainPanel);
    }

    public void addComponent(Component component) {
        mainPanel.add(component);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}