package com.finz.rest;

import java.util.List;

/**
 * @author SudTechnologies
 */
public class Pageable<T> {

    private List<T> items;
    private boolean last;
    private int page;

    Pageable(List<T> items, boolean last, int page) {
        this.items = items;
        this.last = last;
        this.page = page;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}
