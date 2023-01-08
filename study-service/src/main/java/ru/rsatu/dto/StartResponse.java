package ru.rsatu.dto;

import ru.rsatu.common.BaseResponse;

import java.util.Date;

public class StartResponse extends BaseResponse {

    private Long testId;
    private Date startDate;
    private Boolean isTest;
    private Boolean isFinal;
    private Integer testTime;

    public StartResponse() {
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Boolean getTest() {
        return isTest;
    }

    public void setTest(Boolean test) {
        isTest = test;
    }

    public Boolean getFinal() {
        return isFinal;
    }

    public void setFinal(Boolean aFinal) {
        isFinal = aFinal;
    }

    public Integer getTestTime() {
        return testTime;
    }

    public void setTestTime(Integer testTime) {
        this.testTime = testTime;
    }

    @Override
    public String toString() {
        return "StartResponse{" +
                "testId=" + testId +
                ", startDate=" + startDate +
                ", isTest=" + isTest +
                ", isFinal=" + isFinal +
                ", testTime=" + testTime +
                '}';
    }
}
