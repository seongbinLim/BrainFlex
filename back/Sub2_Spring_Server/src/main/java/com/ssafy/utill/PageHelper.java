package com.ssafy.utill;

public class PageHelper {
	private int startRow;
    private int linePerPage;

    public PageHelper(int page, int linePerPage) {
        this.linePerPage = linePerPage;
        this.startRow = (page-1)*linePerPage;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getLinePerPage() {
        return linePerPage;
    }

    public void setLinePerPage(int linePerPage) {
        this.linePerPage = linePerPage;
    }

}
