package ru.rsatu.dto;

import ru.rsatu.common.BaseResponse;
import ru.rsatu.models.TestTry;

import java.util.List;

public class GetResultResponse extends BaseResponse {

    private TestTry test;
    private Long ansAmount;
    private Long rightCount;
    private List<TestResult> answers;

    public GetResultResponse() {
    }

    public TestTry getTest() {
        return test;
    }

    public void setTest(TestTry test) {
        this.test = test;
    }

    public Long getAnsAmount() {
        return ansAmount;
    }

    public void setAnsAmount(Long ansAmount) {
        this.ansAmount = ansAmount;
    }

    public Long getRightCount() {
        return rightCount;
    }

    public void setRightCount(Long rightCount) {
        this.rightCount = rightCount;
    }

    public List<TestResult> getAnswers() {
        return answers;
    }

    public void setAnswers(List<TestResult> answers) {
        this.answers = answers;
    }
}
