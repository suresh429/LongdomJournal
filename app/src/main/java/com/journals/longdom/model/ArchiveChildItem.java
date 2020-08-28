package com.journals.longdom.model;

public class ArchiveChildItem {
    private String childItemTitle;
    private String year;
    private String journal;
    private String vol;
    private String issue;




    public ArchiveChildItem(String childItemTitle, String year, String journal, String vol, String issue) {
        this.childItemTitle = childItemTitle;
        this.year = year;
        this.journal = journal;
        this.vol = vol;
        this.issue = issue;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getChildItemTitle() {
        return childItemTitle;
    }

    public void setChildItemTitle(String childItemTitle) {
        this.childItemTitle = childItemTitle;
    }
}
