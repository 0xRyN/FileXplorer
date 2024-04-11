package org.xplorer.model;

import java.util.List;

public class SearchModel {
    private String searchRegex;

    private String rootDir;
    private List<String> searchResults;

    public List<String> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<String> searchResults) {
        this.searchResults = searchResults;
    }

    public String getSearchRegex() {
        return searchRegex;
    }

    public void setSearchRegex(String searchRegex) {
        this.searchRegex = searchRegex;
    }

    public String getRootDir() {
        return rootDir;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }
}
