package org.xplorer;

import org.xplorer.controller.ExplorerController;

import javax.swing.*;

public class Main {

    public static void initExplorer() {
        ExplorerController explorerController = new ExplorerController();
        explorerController.showExplorer();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::initExplorer);
    }
}