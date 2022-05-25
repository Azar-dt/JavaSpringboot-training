package edu.ftv.training.payload;

import edu.ftv.training.Model.SuDung;

public class PagingRequest {
    private SuDung suDung;
    private int limit;
    private int currentPage;

    public SuDung getSuDung() {
        return suDung;
    }

    public void setSuDung(SuDung suDung) {
        this.suDung = suDung;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public PagingRequest(SuDung suDung, int limit, int currentPage) {
        this.suDung = suDung;
        this.limit = limit;
        this.currentPage = currentPage;
    }
}
