package ru.rsatu.services;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import ru.rsatu.dto.*;
import ru.rsatu.common.BaseResponse;
import ru.rsatu.models.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;

import static java.util.Collections.shuffle;

@ApplicationScoped
public class ExamService {

    @Inject
    EntityManager em;

    @Inject
    @RestClient
    OrderServiceExtension orderServiceExtension;

    @Transactional
    public BaseResponse startExam(ExamDTO request){
        StudyProgram studyProgram = StudyProgram.findById(request.getProgramId());

        OrderDTO userContract = orderServiceExtension
                .getForStartExam(request.getUserId(), request.getProgramId(), false);
        Long contractId = userContract.getContractId();
        finishUnfinishedTests(studyProgram.getStudyProgramId(), contractId);

        if (hasSuccessTestExam(studyProgram.getStudyProgramId(), contractId, request.getIsFinal())){
            return new BaseResponse("error",
                    "Для прохождения итогового теста необходимо успешно пройти пробное тестирование");
        }

        if (hasFinalExamTries(studyProgram.getStudyProgramId(), contractId, studyProgram.getTriesCount())){
            return new BaseResponse("error","Потрачены все попытки прохождения итогового теста");
        }

        int testQuestionCount = studyProgram.questionNums;
        List<Question> questions = Question.find("studyProgramId = ?1", studyProgram.getStudyProgramId()).list();
        int questionCount = questions.size();
        if (questionCount < testQuestionCount) {
            return new BaseResponse("error","Не хватает вопросов.");
        }

        Collections.shuffle(questions);

        TestTry newTestTry = new TestTry();
        newTestTry.setStudyProgram(studyProgram);
        newTestTry.setContractId(contractId);
        newTestTry.setIsTest(!request.getIsFinal());
        newTestTry.setIsFinal(request.getIsFinal());
        newTestTry.setStartDate(new Date());
        newTestTry.persist();

        int npp = 1;
        for (Question testQuestion: questions){
            TryAnswers tryAnswer = new TryAnswers();
            tryAnswer.setQuestion(testQuestion);
            tryAnswer.setTestTry(newTestTry);
            tryAnswer.setNpp(npp++);
            tryAnswer.persist();
        }

        StartResponse startResponse = new StartResponse();
        startResponse.setTestId(newTestTry.getTestTryId());
        startResponse.setStartDate(newTestTry.getStartDate());
        startResponse.setFinal(newTestTry.getIsFinal());
        startResponse.setTest(newTestTry.getIsTest());
        startResponse.setTestTime(studyProgram.getCompleteTime());

        return startResponse;
    }

    public BaseResponse getQuestion(Long testId) {

        Optional<TryAnswers> optionalTryAnswers = TryAnswers.find("testTryId = ?1 and answerId = null ", testId).firstResultOptional();
        if (optionalTryAnswers.isEmpty()){
            return GetQuestionResponse.builder().isComplete(true).build();
        }
        TryAnswers tryAnswers = optionalTryAnswers.get();

        Question question = tryAnswers.getQuestion();
        List<Answer> answers = new ArrayList<>(question.answers);
        shuffle(answers);
        Long questionAmount = TryAnswers.find("testTryId = ?1", testId).count();
        long currQuestion = TryAnswers.find("testTryId = ?1 and answerId != null ", testId).count() + 1;

        return GetQuestionResponse.builder()
                .test(CurrTestRespones.builder()
                        .testId(testId)
                        .tryAnswerId(tryAnswers.getTryAnswersId())
                        .questionId(question.questionId)
                        .questionText(question.questionText)
                        .answer(answers)
                        .amount(questionAmount)
                        .curr(currQuestion)
                        .build())
                .isComplete(false).build();
    }

    @Transactional
    public BaseResponse putAnswer(AnswerDTO request) {
        TryAnswers tryAnswers = TryAnswers.findById(request.getTryAnswerId());
        if (checkDeadline(tryAnswers.getTestTry().getStudyProgram().getCompleteTime(), tryAnswers.getTestTry().getStartDate())){
            return new BaseResponse("error", "Превышено время выполения теста");
        }
        Answer answer = Answer.findById(request.getAnswerId());
        tryAnswers.setAnswer(answer);
        tryAnswers.persist();

        return new BaseResponse("ok", "Ответ получен");
    }

    @Transactional
    public BaseResponse getTestResult(Long testId){
        TestTry testTry = TestTry.findById(testId);
        testTry.setIsComplete(true);

        Long questionAmount = TryAnswers.find("testTryId = ?1", testId).count();
        Long rightAnswersCount = em.createQuery("select count(ta) " +
                        "from TryAnswers ta " +
                        "   left join Answer a on a.answerId = ta.answer.answerId " +
                        "where ta.testTry.testTryId = :ttid and a.isRight = true ", Long.class)
                .setParameter("ttid", testId)
                .getSingleResult();

        if ((questionAmount - rightAnswersCount) <= 2){
            testTry.setIsSuccessful(true);
            testTry.setIsComplete(true);
            if (testTry.getIsFinal())
                orderServiceExtension.setComplete(OrderDTO.builder().contractId(testTry.getContractId()).build());
        } else {
            testTry.setIsSuccessful(false);
            testTry.setIsComplete(true);
        }
        testTry.setEndDate(new Date());
        testTry.persist();

        List<TestResult> testResults = new ArrayList<>();
        testTry.getTryAnswers().forEach(tryAnswer -> {

            Answer rightAnswer = em.createQuery("select a " +
                            "from TryAnswers ta " +
                            "   inner join Answer a on ta.question.questionId = a.question.questionId " +
                            "where ta.tryAnswersId = :taid and a.isRight = true ", Answer.class)
                    .setParameter("taid", tryAnswer.getTryAnswersId())
                    .getSingleResult();

            var tempResult = TestResult.builder()
                    .questionId(tryAnswer.question.questionId)
                    .questionText(tryAnswer.question.questionText)
                    .givenAnswerId(tryAnswer.getAnswer().answerId)
                    .givenAnswerText(tryAnswer.getAnswer().getAnswerText())
                    .rightAnswerId(rightAnswer.getAnswerId())
                    .rightAnswerText(rightAnswer.getAnswerText())
                    .rightAnswerInfo(tryAnswer.getQuestion().answerInformation)
                    .npp(tryAnswer.getNpp())
                    .build();

            testResults.add(tempResult);
        });

        GetResultResponse response = new GetResultResponse();
        response.setAnswers(testResults);
        response.setAnsAmount(questionAmount);
        response.setRightCount(rightAnswersCount);
        response.setTest(testTry);

        return response;
    }

    private void finishUnfinishedTests(Long studyProgramId, Long contractId){
        List<TestTry> tests = TestTry.find("studyProgramId = ?1 and contractId = ?2 and isComplete = false",
                studyProgramId, contractId).list();
        tests.forEach(test -> {
            test.setEndDate(new Date());
            test.setIsComplete(true);
            test.setIsSuccessful(false);
            test.persist();
        });
    }

    private boolean hasSuccessTestExam(Long studyProgramId, Long contractId, Boolean finalFlag){
        long successTestCount =
                TestTry.find("studyProgramId = ?1 and contractId = ?2 and isTest = ?3 and isSuccessful = ?4",
                        studyProgramId, contractId, true, true).count();
        return (successTestCount == 0) && finalFlag;
    }

    private boolean hasFinalExamTries(Long studyProgramId, Long contractId, long triesCount){
        long finalTriesCount = TestTry.find("studyProgramId = ?1 and contractId = ?2 and isFinal = ?3",
                studyProgramId, contractId, true).count();
        return finalTriesCount >= triesCount;
    }

    private boolean checkDeadline(Integer completeTime, Date startExamDate){
        Calendar temp = Calendar.getInstance();
        temp.setTime(startExamDate);
        temp.add(Calendar.MINUTE, completeTime);
        Date deadline = temp.getTime();
        Date currTime = new Date();
        return deadline.before(currTime);
    }
}
