package org.xplorer.view;

import org.xplorer.util.Consts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchView extends JPanel {

    private JTextField searchField;
    private JButton searchButton;

    private JList<String> searchResultsList;
    private DefaultListModel<String> searchResultsModel;

    private JLabel timeSearchingSeconds;

    public SearchView() {
        initUI();
        setPreferredSize(new Dimension(Consts.SCREEN_WIDTH / 8, getHeight()));
    }

    private void initUI() {
        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));


        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.LINE_AXIS));

        JLabel regexLabel = new JLabel("Regex:");
        searchPanel.add(regexLabel);
        searchPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        searchField = new JTextField(20);
        searchPanel.add(searchField);
        searchPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        searchButton = new JButton("Rechercher");
        searchPanel.add(searchButton);


        topPanel.add(searchPanel);


        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.LINE_AXIS));

        JLabel statusLabel = new JLabel("Time searching: ");
        statusPanel.add(statusLabel, BorderLayout.SOUTH);

        statusPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        timeSearchingSeconds = new JLabel("0 ms");
        statusPanel.add(timeSearchingSeconds);

        topPanel.add(statusPanel);

        this.add(topPanel, BorderLayout.NORTH);


        searchResultsModel = new DefaultListModel<>();
        this.searchResultsList = new JList<>(searchResultsModel);
        this.searchResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(searchResultsList);


        this.add(listScrollPane, BorderLayout.CENTER);
    }

    public void addSearchListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }


    public void setSearchResults(List<String> results) {
        searchResultsModel.clear();
        for (String result : results) {
            searchResultsModel.addElement(result);
        }
    }

    public String getSearchRegex() {
        return searchField.getText();
    }

    public JList<String> getSearchResultsList() {
        return searchResultsList;
    }

    public void setTimeSearchingSeconds(long time) {
        timeSearchingSeconds.setText(time + " s");
    }
}
