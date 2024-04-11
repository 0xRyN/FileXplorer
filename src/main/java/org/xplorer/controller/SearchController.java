package org.xplorer.controller;

import org.xplorer.interfaces.SearchFileSelectionListener;
import org.xplorer.model.SearchModel;
import org.xplorer.view.SearchView;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SearchController {
    private final SearchModel model;
    private final SearchView view;

    private final SearchFileSelectionListener searchFileSelectionListener;

    public SearchController(SearchModel model, SearchView view, SearchFileSelectionListener searchFileSelectionListener) {
        this.model = model;
        this.view = view;
        this.searchFileSelectionListener = searchFileSelectionListener;
        initController();
    }

    private void initController() {
        initEventListeners();
    }

    private void updateTimeSearching(long elapsedTime) {
        System.out.println("Elapsed time: " + elapsedTime);
        view.setTimeSearchingSeconds(elapsedTime);
    }


    public void searchFiles() {

        view.setTimeSearchingSeconds(0);
        long startTime = System.currentTimeMillis();
        Timer timer = new Timer(1000, e -> {
            long elapsedTime = System.currentTimeMillis() - startTime;
            updateTimeSearching(elapsedTime / 1000);
        });
        timer.start();
        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() {
                return findFiles(model.getRootDir(), model.getSearchRegex());
            }

            @Override
            protected void done() {
                try {
                    model.setSearchResults(get());
                    view.setSearchResults(model.getSearchResults());
                    timer.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                    timer.stop();

                }
            }
        }.execute();
    }


    private List<String> findFiles(String dir, String regex) {
        String linuxCommand = "find " + dir + " -type f -regex " + regex;
        String windowsCommand = "powershell.exe -Command \"Get-ChildItem -Path " + dir + " -Recurse | Where-Object { $_.Name -match '" + regex + "' } | ForEach-Object { $_.FullName }\"";
        String usedCommand = System.getProperty("os.name").contains("Windows") ? windowsCommand : linuxCommand;

        System.out.println("Command: " + usedCommand);

        List<String> fileList = new ArrayList<>();
        try {

            Process process = Runtime.getRuntime().exec(usedCommand);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                fileList.add(line);
            }


            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    private void initEventListeners() {
        view.addSearchListener(e -> {
            model.setSearchRegex(view.getSearchRegex());
            searchFiles();
        });

        view.getSearchResultsList().addListSelectionListener(e -> {
            String selectedFile = view.getSearchResultsList().getSelectedValue();
            if (selectedFile != null) {
                searchFileSelectionListener.onSearchFileSelected(selectedFile);
            }
        });
    }

    public void onFolderSelected(String path) {
        model.setRootDir(path);
    }

}
