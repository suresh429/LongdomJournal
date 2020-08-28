package com.journals.longdom.model;

import java.util.List;

public class ArchiveHeaderItem {
    // Declaration of the variables
    private String HeaderItemTitle;
    private List<ArchiveChildItem> ChildItemList;

    public ArchiveHeaderItem(String headerItemTitle, List<ArchiveChildItem> childItemList) {
        HeaderItemTitle = headerItemTitle;
        ChildItemList = childItemList;
    }

    public String getHeaderItemTitle() {
        return HeaderItemTitle;
    }

    public void setHeaderItemTitle(String headerItemTitle) {
        HeaderItemTitle = headerItemTitle;
    }

    public List<ArchiveChildItem> getChildItemList() {
        return ChildItemList;
    }

    public void setChildItemList(List<ArchiveChildItem> childItemList) {
        ChildItemList = childItemList;
    }
}
