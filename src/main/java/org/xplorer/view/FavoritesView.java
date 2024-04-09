package org.xplorer.view;

import javax.swing.*;
import java.awt.*;

public class FavoritesView extends JPanel {
    private JList<String> favoritesList;
    private DefaultListModel<String> listModel;

    public FavoritesView() {
        setLayout(new BorderLayout());
        listModel = new DefaultListModel<>();
        favoritesList = new JList<>(listModel);
        add(new JScrollPane(favoritesList), BorderLayout.CENTER);
    }

    public void setFavoritesList(java.util.List<String> favorites) {
        listModel.clear();
        for (String favorite : favorites) {
            listModel.addElement(favorite);
        }
    }

    public JList<String> getFavoritesList() {
        return favoritesList;
    }
}