package edu.ftv.training.Model;

import java.util.List;

public class PagingResponse {
    private List<SuDung> suDungs;
    private long total;

    public List<SuDung> getSuDungs() {
        return suDungs;
    }

    public void setSuDungs(List<SuDung> suDungs) {
        this.suDungs = suDungs;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public PagingResponse(List<SuDung> suDungs, long total) {
        this.suDungs = suDungs;
        this.total = total;
    }
}
