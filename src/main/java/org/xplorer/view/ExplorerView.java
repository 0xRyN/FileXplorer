package org.xplorer.view;

import org.xplorer.controller.ConfigurationController;
import org.xplorer.model.ConfigurationModel;

import javax.swing.*;
import java.awt.*;


public class ExplorerView extends JFrame {
    private JPanel mainPanel;

    public ExplorerView() {
        initializeUI();
        initializeMenuBar();
    }

    private void initializeUI() {
        setTitle("Explorer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 1000);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));

        getContentPane().add(mainPanel);
    }

    private void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu configurationMenu = new JMenu("Configuration");
        JMenuItem editConfigurationMenuItem = new JMenuItem("Éditer la configuration");

        editConfigurationMenuItem.addActionListener(e -> openConfigurationDialog());

        configurationMenu.add(editConfigurationMenuItem);
        menuBar.add(configurationMenu);

        setJMenuBar(menuBar);
    }

    private void openConfigurationDialog() {

        ConfigurationModel configurationModel = new ConfigurationModel();
        ConfigurationView configurationView = new ConfigurationView();

        ConfigurationController configurationController = new ConfigurationController(configurationModel, configurationView);


        JDialog configurationDialog = new JDialog(this, "Éditer la Configuration", true);
        configurationDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        configurationDialog.getContentPane().add(configurationView);
        configurationDialog.pack();
        configurationDialog.setLocationRelativeTo(this);
        configurationDialog.setVisible(true);
    }

    public void addComponent(Component component) {
        mainPanel.add(component);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

}