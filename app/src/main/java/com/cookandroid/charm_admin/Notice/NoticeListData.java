package com.cookandroid.charm_admin.Notice;

/**
 * Created by HP on 2016-09-11.
 */
public class NoticeListData {
    private String tvNum;
    private String tvNoticeTitle;
    private String tvNoticeContent;
    private String tvNoticeDate;

    public void setTvNoticeTitle(String tvNoticeTitle) {
        this.tvNoticeTitle = tvNoticeTitle;
    }

    public String getTvNoticeContent() {
        return tvNoticeContent;
    }

    public void setTvNoticeContent(String tvNoticeContent) {
        this.tvNoticeContent = tvNoticeContent;
    }

    public String getTvNum() {
        return tvNum;
    }

    public void setTvNum(String tvNum) {
        this.tvNum = tvNum;
    }

    public String getTvNoticeTitle() {
        return tvNoticeTitle;
    }

    public String getTvNoticeDate() {
        return tvNoticeDate;
    }

    public void setTvNoticeDate(String tvNoticeDate) {
        this.tvNoticeDate = tvNoticeDate;
    }
}
