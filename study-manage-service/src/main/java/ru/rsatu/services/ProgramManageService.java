package ru.rsatu.services;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import ru.rsatu.common.BaseResponse;
import ru.rsatu.dto.*;
import ru.rsatu.models.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;

@ApplicationScoped
public class ProgramManageService {

    @Inject
    @RestClient
    OrderServiceExtension orderServiceExtension;

    @Transactional
    public BaseResponse addSection(StudySectionDTO request) {
        StudySection.builder()
                .name(request.getName())
                .description(request.getDescription())
                .isDeprecated(false)
                .build().persist();
        return BaseResponse.builder().type("ok").message("Раздел обучения создан").build();
    }

    @Transactional
    public BaseResponse deleteSection(StudySectionDTO request) {
        StudySection section = StudySection.findById(request.getStudySectionId());
        markSectionDeprecation(section);
        return BaseResponse.builder().type("ok").message("Раздел обучения удален").build();
    }

    @Transactional
    public BaseResponse deleteCourse(CourseDTO request) {
        Course course = Course.findById(request.getCourseId());
        markCourseDeprecation(course);
        return BaseResponse.builder().type("ok").message("Направление обучения удалено").build();
    }

    @Transactional
    public BaseResponse editSection(StudySectionDTO request) {
        StudySection section = StudySection.findById(request.getStudySectionId());
        section.setName(request.getName());
        section.setDescription(request.getDescription());
        section.persist();
        return BaseResponse.builder().type("ok").message("Изменения сохранены").build();
    }

    @Transactional
    public BaseResponse addCourse(CourseDTO request) {
        StudySection studySection = StudySection.findById(request.getStudySectionId());
        Course newCourse = Course.builder()
                .name(request.getName())
                .description(request.getDescription())
                .isDeprecated(false)
                .studySection(studySection)
                .build();
        newCourse.persist();
        return BaseResponse.builder().type("ok").message("Направление создано").build();
    }

    @Transactional
    public BaseResponse editCourse(CourseDTO request) {
        Course course = Course.findById(request.getCourseId());
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.persist();
        return BaseResponse.builder().type("ok").message("Изменения сохранены").build();
    }

    public List<Answer> getAnswers(Long questionId) {
        return Answer.find("questionId = ?1 order by answerId", questionId)
                .list();
    }

    @Transactional
    public BaseResponse addProgram(StudyProgramDTO request) {
        Course course = Course.findById(request.getCourseId());
        var newProgram = StudyProgram.builder()
                .name(request.getName())
                .description(request.getDescription())
                .minimalDuration(request.getMinimalDuration())
                .completeTime(request.getCompleteTime())
                .questionNums(request.getQuestionNums())
                .triesCount(request.getTriesCount())
                .price(request.getPrice())
                .isDeprecated(false)
                .course(course)
                .build();
        newProgram.persist();
        return BaseResponse.builder().type("ok").message("Программа обучения создана").build();
    }

    @Transactional
    public BaseResponse addQuestion(QuestionDTO request) {
        try {
            StudyProgram studyProgram = StudyProgram.findById(request.getProgramId());
            Question newQuestion = Question.builder()
                    .questionText(request.getQuestionText())
                    .answerInformation(request.getAnswerInformation())
                    .studyProgram(studyProgram).build();
            newQuestion.persist();
            addAnswersToQuestion(request.getAnswerList(), newQuestion);
            return BaseResponse.builder().type("ok").message("Вопрос добавлен").build();

        } catch (Exception ex) {
            return BaseResponse.builder().type("error").message("Ошибка при добавлении").build();
        }
    }

    @Transactional
    public BaseResponse deleteQuestion(QuestionDTO request) {
        try {
            Question question = Question.findById(request.getQuestionId());
            question.delete();
            return BaseResponse.builder().type("ok").message("Вопрос удален").build();
        } catch (Exception ex) {
            return BaseResponse.builder().type("error").message("Ошибка при удалении").build();
        }
    }

    @Transactional
    public BaseResponse deleteProgram(StudyProgramDTO request) {
        StudyProgram studyProgram = StudyProgram.findById(request.getStudyProgramId());
        markStudyProgramDeprecation(studyProgram);
        return BaseResponse.builder().type("ok").message("Программа удалена").build();
    }

    @Transactional
    public BaseResponse deleteLiterature(StudyProgramLiteratureDTO request) {
        StudyProgramLiterature.delete("studyProgramLiteratureId", request.getStudyProgramLiteratureId());
        return BaseResponse.builder().type("ok").message("Источник удален").build();
    }

    @Transactional
    public BaseResponse editQuestion(QuestionDTO request) {
        Question question = Question.findById(request.getQuestionId());
        question.setQuestionText(request.getQuestionText());
        question.setAnswerInformation(request.getAnswerInformation());

        try {
            question.persist();
            Answer.delete("questionId = ?1", request.getQuestionId());
            addAnswersToQuestion(request.getAnswerList(), question);
            return BaseResponse.builder().type("ok").message("Изменения сохранены").build();
        } catch (Exception ex) {
            return BaseResponse.builder().type("error").message("Ошибка при изменении").build();
        }
    }

    public List<Question> getQuestion(Long strudyProgramId) {
        return Question.find("studyProgramId = ?1 order by questionId", strudyProgramId).list();
    }

    @Transactional
    public BaseResponse editPrograms(StudyProgramDTO request) {
        StudyProgram program = StudyProgram.findById(request.getStudyProgramId());
        program.setName(request.getName());
        program.setDescription(request.getDescription());
        program.setCompleteTime(request.getCompleteTime());
        program.setPrice(request.getPrice());
        program.setMinimalDuration(request.getMinimalDuration());
        program.setQuestionNums(request.getQuestionNums());
        program.setTriesCount(request.getTriesCount());
        program.persist();
        return BaseResponse.builder().type("ok").message("Изменения сохранены").build();
    }

    @Transactional
    public BaseResponse addLiterature(StudyProgramLiteratureDTO request) {
        try {
            StudyProgram studyProgram = StudyProgram.findById(request.getStudyProgramId());
            var literature = StudyProgramLiterature.builder()
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .link(request.getLink())
                    .studyProgram(studyProgram).build();
            literature.persist();
            return BaseResponse.builder().type("ok").message("Литература добавлена").build();
        } catch (Exception ex) {
            return BaseResponse.builder().type("error").message("Ошибка при добавлении").build();
        }
    }

    public StatisticsResponse getStatistic(Integer pageNumber, Integer pageSize){

        List<OrderDTO> contracts = orderServiceExtension.getAllContracts(pageNumber, pageSize);

        List<StatisticDTO> statistics = new ArrayList<>();

        contracts.forEach(contract -> {
            System.out.printf("spID = %d\n", contract.getStudyProgramId());
            System.out.printf("cID = %d\n", contract.getContractId());

            Long testAmount = TestTry
                    .find("studyProgramId = ?1 and contractId = ?2 and istest = True",
                            contract.getStudyProgramId(), contract.getContractId()).count();
            Long testSuccAmount = TestTry
                    .find("studyProgramId = ?1 and contractId = ?2 and istest = True and issuccessful = True",
                            contract.getStudyProgramId(), contract.getContractId()).count();
            Long finalAmount = TestTry
                    .find("studyProgramId = ?1 and contractId = ?2 and isfinal = True",
                            contract.getStudyProgramId(), contract.getContractId()).count();
            Long finalFailAmount = TestTry
                    .find("(studyProgramId = ?1 and contractId = ?2 and isfinal = True " +
                                    "and issuccessful = False) or enddate = null",
                            contract.getStudyProgramId(), contract.getContractId()).count();

            StudyProgram studyProgram = StudyProgram.findById(contract.getStudyProgramId());
            statistics.add(StatisticDTO.builder()
                    .contractId(contract.getContractId())
                    .userId(contract.getUserId())
                    .programId(contract.getStudyProgramId())
                    .programName(studyProgram.getName())
                    .testAmount(testAmount)
                    .testSuccAmount(testSuccAmount)
                    .finalAmount(finalAmount)
                    .finalFailAmount(finalFailAmount)
                    .build());

        });

        return StatisticsResponse.builder()
                .statistics(statistics)
                .countPage((int) Math.ceil((double) contracts.size() / (double) pageSize))
                .build();

    }

    private void markSectionDeprecation(StudySection section){
        section.setIsDeprecated(true);
        section.persist();
        section.getCourses().forEach(this::markCourseDeprecation);
    }

    private void markCourseDeprecation(Course course){
        course.setIsDeprecated(true);
        course.persist();
        course.getStudyPrograms().forEach(this::markStudyProgramDeprecation);
    }

    private void markStudyProgramDeprecation(StudyProgram studyProgram){
        studyProgram.setIsDeprecated(true);
        studyProgram.persist();
    }

    private void addAnswersToQuestion(List<AnswerDTO> answers, Question question){
        answers.forEach(answer -> Answer.builder()
                .answerText(answer.getAnswerText())
                .isRight(answer.getIsRight())
                .question(question)
                .build().persist());
    }
}
