package ru.rsatu.dto;

import ru.rsatu.common.BaseResponse;
import ru.rsatu.models.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseResponse extends BaseResponse {

    private int contentSize;
    private List<Course> list = new ArrayList<>();

    public CourseResponse() {
    }

    public CourseResponse(int contentSize, List<Course> list) {
        this.contentSize = contentSize;
        this.list = list;
    }

    public int getContentSize() {
        return contentSize;
    }

    public void setContentSize(int contentSize) {
        this.contentSize = contentSize;
    }

    public List<Course> getList() {
        return list;
    }

    public void setList(List<Course> list) {
        this.list = list;
    }

}
