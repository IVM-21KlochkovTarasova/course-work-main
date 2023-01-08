package ru.rsatu.services;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import ru.rsatu.dto.CourseResponse;
import ru.rsatu.dto.LkProgramDTO;
import ru.rsatu.dto.OrderDTO;
import ru.rsatu.dto.SectionResponse;
import ru.rsatu.models.*;
import ru.rsatu.common.BaseResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class StudyService {

    @Inject
    @RestClient
    OrderServiceExtension orderServiceExtension;

    public List<StudyProgram> getProgramsByCourseId(Long courseId) {
        return StudyProgram.find("courseId = ?1 and isDeprecated = false", courseId).list();
    }

    public List<LkProgramDTO> getProgramsByUserId(String userId) {
        List<OrderDTO> orders = orderServiceExtension.getByUserId(userId);
        orders.forEach(System.out::println);
        List<Long> studyProgramsId = orders.stream().map(OrderDTO::getStudyProgramId).collect(Collectors.toList());
        List<StudyProgram> studyPrograms = StudyProgram
                .find("studyProgramId in ?1 and isDeprecated = false", studyProgramsId).list();
        return studyPrograms.stream()
                .map(sp -> LkProgramDTO.builder()
                        .studyProgram(sp)
                        .contract(orders.stream()
                                .filter(o->o.getStudyProgramId().equals(sp.getStudyProgramId())).findFirst().orElseThrow())
                        .build())
                .collect(Collectors.toList());
    }

    public List<StudyProgramLiterature> getLiterature(Long studyProgramId, String filter) {
        return StudyProgramLiterature
                .find("title like ?2 and studyProgramId = ?1 order by studyProgramLiteratureId",
                        studyProgramId, "%"+filter+"%")
                .list();
    }

    public BaseResponse getCourse(Long studySectionId) {
        List<Course> allCourses = Course
                .find("studysectionid = ?1 and isDeprecated != true order by courseId", studySectionId)
                .list();
        return new CourseResponse(allCourses.size(), allCourses);
    }

    public BaseResponse getSections() {
        List<StudySection> allSec = StudySection.find("isDeprecated != true order by studySectionId").list();
        return new SectionResponse(allSec.size(), allSec);
    }

    public List<Question> getQuestion(Long studyProgramId) {
        return Question.find("studyProgramId = ?1", studyProgramId).list();
    }

    public List<Answer> getAnswers(Long questionId) {
        return Answer.find("questionId = ?1", questionId).list();
    }

}
