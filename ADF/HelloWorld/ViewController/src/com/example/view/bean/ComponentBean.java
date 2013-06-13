package com.example.view.bean;

import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;

public class ComponentBean {
    private RichShowDetailItem detailItem;

    public ComponentBean() {
    }

    public void setDetailItem(RichShowDetailItem detailItem) {
        this.detailItem = detailItem;
    }

    public RichShowDetailItem getDetailItem() {
        return detailItem;
    }
}
