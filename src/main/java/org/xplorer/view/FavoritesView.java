package org.xplorer.view;

import org.xplorer.util.Consts;

import javax.swing.*;
import java.awt.*;

public class FavoritesView extends JPanel {
    private final JList<String> favoritesList;
    private final DefaultListModel<String> listModel;

    public FavoritesView() {
        setLayout(new BorderLayout());
        listModel = new DefaultListModel<>();
        favoritesList = new JList<>(listModel);
        setPreferredSize(new Dimension(Consts.SCREEN_WIDTH / 8, getHeight()));
        add(new JScrollPane(favoritesList), BorderLayout.CENTER);
    }

    public void setFavoritesList(java.util.List<String> favorites) {
        System.out.println(favorites);
        listModel.clear();
        System.out.println("Favorites list cleared");
        for (String favorite : favorites) {
            System.out.println("Adding favorite: " + favorite);
            listModel.addElement(favorite);
        }
    }

    public JList<String> getFavoritesList() {
        return favoritesList;
    }
}