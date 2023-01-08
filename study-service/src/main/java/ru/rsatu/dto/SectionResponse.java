package ru.rsatu.dto;

import ru.rsatu.common.BaseResponse;
import ru.rsatu.models.StudySection;

import java.util.ArrayList;
import java.util.List;

public class SectionResponse extends BaseResponse {
    private int contentSize;
    private List<StudySection> list = new ArrayList<>();

    public SectionResponse() {
    }

    public SectionResponse(int contentSize, List<StudySection> list) {
        this.contentSize = contentSize;
        this.list = list;
    }

    public int getContentSize() {
        return contentSize;
    }

    public void setContentSize(int contentSize) {
        this.contentSize = contentSize;
    }

    public List<StudySection> getList() {
        return list;
    }

    public void setList(List<StudySection> list) {
        this.list = list;
    }
}
